//
// scheduled only
// filling charges table with data from files
//

package ru.sber.ekvit.job.register;

import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.sber.ekvit.job.JobType;
import ru.sber.ekvit.persistence.dao.*;
import ru.sber.ekvit.persistence.model.*;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

import static ru.sber.ekvit.job.util.CommonUtil.tryParseInt;
import static ru.sber.ekvit.persistence.model.Charge.*;

@Component
@Slf4j
public class ChargesRegisterIn extends BaseAdrRegister<Charge> {
    static final String SEARCH_DIRECTORY = "ChargesRegisterInJob";

    private final AddressElementDao addressElementDao;
    private final RegisterInDao registerInDao;
    private final ApplicationEventDao applicationEventDao;

    @Autowired
    public ChargesRegisterIn(AddressElementDao addressElementDao, ContractDao contractDao, ChargeDao chargeDao, RegisterInDao registerInDao, ConstantDao constantDao, ApplicationEventDao applicationEventDao) {
        super(chargeDao, contractDao, applicationEventDao, Paths.get(constantDao.get().getMainPath(), SEARCH_DIRECTORY), JobType.ChargesRegisterInJob);
        this.addressElementDao = addressElementDao;
        this.registerInDao = registerInDao;
        this.applicationEventDao = applicationEventDao;
    }

    @Scheduled(fixedRate = 7200000L) // 2h
    public synchronized void go() throws IOException, FileTypeException {
        log.info("go ");
    }

    @Override
    protected void handleDBF(File file, Contract contract) throws IOException {
        log.info("handleDBF (with file: {}, contract:{}) ", file, contract);

        try (InputStream inputStream = new FileInputStream(file)) {
            DBFReader reader = new DBFReader(inputStream);
            reader.setCharactersetName(contract.getFileEncoding().getStr());

            //get address string(s) specification
            AddressElement addressElement = addressElementDao.get(contract.getId());

            //get structure of all data types
            Structure structure = getStructure(contract);

            Map<String, Integer> fieldNameIndexMap = new HashMap<>(); //index-name fields pairs
            for (int i = 0; i < reader.getFieldCount(); i++) {
                DBFField field = reader.getField(i);
                fieldNameIndexMap.put(field.getName(), i);

                if (structure.lsFieldInfoMap.containsKey(field.getName()))
                    structure.lsFieldInfoMap.get(field.getName()).setIndex(i);
                else if (structure.addressFieldInfoMap.containsKey(field.getName()))
                    structure.addressFieldInfoMap.get(field.getName()).setIndex(i);
                else {
                    if (structure.otherFieldInfoMap.containsKey(field.getName())) {
                        structure.otherFieldInfoMap.get(field.getName()).setIndex(i);
                    }
                }
            }

            List<Charge> chargeListToSave = new ArrayList<>(); // list to accumulate charge obj
            //handle each file row
            Object[] rowObjects;
            while ((rowObjects = reader.nextRecord()) != null) {
                Charge charge = new Charge();
                charge.setContract(contract);

                StringBuilder lsValue = new StringBuilder();
                StringJoiner addressValue = new StringJoiner(addressElement.getDelimiter());
                JSONObject infoValue = new JSONObject(); //save 'other data' as json, so there's need for only one row

                for (Map.Entry<String, FieldInfo> entry : structure.lsFieldInfoMap.entrySet()) {
                    lsValue.append(rowObjects[entry.getValue().getIndex()].toString().trim());
                }
                Map<String, String> addressNameValueElementMap = new HashMap<>();
                for (Map.Entry<String, FieldInfo> entry : structure.addressFieldInfoMap.entrySet()) {
                    addressNameValueElementMap.put(entry.getKey(), rowObjects[entry.getValue().getIndex()].toString());
                    addressValue.add(rowObjects[entry.getValue().getIndex()].toString().trim());
                }
                JSONArray jsonArray = new JSONArray();
                for (Map.Entry<String, FieldInfo> entry : structure.otherFieldInfoMap.entrySet()) {
                    String additionalValue = "";
                    try{
                        additionalValue = rowObjects[fieldNameIndexMap.get(entry.getValue().getAdditionalField())].toString().trim();
                    } catch (NullPointerException ignored){ }

                    jsonArray.put(new JSONObject()
                            .put(ARTICLE_TAG_NAME, entry.getValue().getArticle())
                            .put(FIELDNAME_TAG_NAME, entry.getKey())
                            .put(FIELDVALUE_TAG_NAME, rowObjects[entry.getValue().getIndex()].toString().trim())
                            .put(ADDITIONALVALUE_TAG_NAME, additionalValue)
                    );
                }
                infoValue.put("items", jsonArray);

                Adr adr = normalizeAdr(getAdr(addressNameValueElementMap, addressElement));
                charge.setStreet(adr.street);
                charge.setHouse(adr.house);
                charge.setApartment(adr.apartment);

                charge.setLs(lsValue.toString());
                charge.setAddress(addressValue.toString().substring(0, addressValue.length()));
                charge.setInfo(infoValue.toString());
                charge.setUploadDate(LocalDateTime.now());

                chargeListToSave.add(charge);
                // speed up with using another thread to save list of charges
                if (chargeListToSave.size() > CHARGE_LIST_LIMIT){
                    new Thread( ()-> saveList(chargeListToSave)).run();
                    chargeListToSave.clear();
                }
            }
            saveList(chargeListToSave);
        }
    }

    @Override
    protected void handleTXT(File file, Contract contract) throws IOException {
        log.info("handleTXT (with file: {}, contract:{}) ", file, contract);

        //read each line in file
        //need to deliberately close stream, so use try-with-resources. https://stackoverflow.com/questions/34072035/why-is-files-lines-and-similar-streams-not-automatically-closed
        try (Stream<String> stream = Files.lines(Paths.get(file.toURI()), Charset.forName(contract.getFileEncoding().getStr()))) {
            List<Charge> chargeListToSave = new ArrayList<>(); // list to accumulate charge obj
            stream.forEach(line -> {
                Charge charge = new Charge();
                charge.setContract(contract);

                AddressElement addressElement = addressElementDao.get(contract.getId());

                Structure structure = getStructure(contract);

                String[] subLines = line.split(contract.getDelimiter());
                int maxIndex = subLines.length - 1;

                StringBuilder lsValue = new StringBuilder();
                StringJoiner addressValue = new StringJoiner(addressElement.getDelimiter());
                JSONObject infoValue = new JSONObject();

                Map<String, String> addressNameValueElementMap = new HashMap<>();

                for (Map.Entry<String, FieldInfo> entry : structure.lsFieldInfoMap.entrySet()) {
                    int index = tryParseInt(entry.getKey()) - 1; // index manipulation... 0 is real, 1 is saved (more client-readable)
                    if (index == -2 || index >= maxIndex)
                        continue;

                    lsValue.append(subLines[index]);
                }
                for (Map.Entry<String, FieldInfo> entry : structure.addressFieldInfoMap.entrySet()) {
                    int index = tryParseInt(entry.getKey()) - 1;
                    if (index == -2 || index >= maxIndex)
                        continue;

                    addressNameValueElementMap.put(entry.getKey(), subLines[index]);
                    addressValue.add(subLines[index]);
                }
                JSONArray jsonArray = new JSONArray();
                for (Map.Entry<String, FieldInfo> entry : structure.otherFieldInfoMap.entrySet()) {
                    int index = tryParseInt(entry.getKey()) - 1;
                    if (index == -2 || index >= maxIndex)
                        continue;

                    String additionalValue = "";
                    try{
                        additionalValue = subLines[tryParseInt(entry.getValue().getAdditionalField()) - 1].trim();
                    } catch (Exception ignored){   }

                    jsonArray.put(new JSONObject()
                            .put(ARTICLE_TAG_NAME, entry.getValue().getArticle())
                            .put(FIELDNAME_TAG_NAME, entry.getKey())
                            .put(FIELDVALUE_TAG_NAME, subLines[index].trim())
                            .put(ADDITIONALVALUE_TAG_NAME, additionalValue)
                    );
                }
                infoValue.put("items", jsonArray);

                Adr adr = normalizeAdr(getAdr(addressNameValueElementMap, addressElement));
                charge.setStreet(adr.street);
                charge.setHouse(adr.house);
                charge.setApartment(adr.apartment);

                charge.setLs(lsValue.toString());
                charge.setAddress(addressValue.toString().substring(0, addressValue.length()));
                charge.setInfo(infoValue.toString());

                chargeListToSave.add(charge);
                // speed up with using another thread to save list of charges
                if (chargeListToSave.size() > CHARGE_LIST_LIMIT){
                    new Thread( ()-> saveList(chargeListToSave)).run();
                    chargeListToSave.clear();
                }
            });
            saveList(chargeListToSave);
        }
    }

    @AllArgsConstructor
    private class Structure {
        Map<String,FieldInfo> lsFieldInfoMap;
        Map<String,FieldInfo> addressFieldInfoMap;
        Map<String,FieldInfo> otherFieldInfoMap;
    }

    @Data
    private class FieldInfo{
        Integer index;
        String article;
        String additionalField;

        FieldInfo(String article, String additionalField) {
            this.article = article;
            this.additionalField = additionalField;
        }
    }

    //get all specified types of info in file as inner class obj (Structure)
    private Structure getStructure(Contract contract) {
        List<RegisterIn> registerInList = registerInDao.getAllByContractId(contract.getId());

        Map<String,FieldInfo> lsFieldInfoMap = new LinkedHashMap<>();
        Map<String,FieldInfo> addressFieldInfoMap = new LinkedHashMap<>();
        Map<String,FieldInfo> otherFieldInfoMap = new LinkedHashMap<>();

        for (RegisterIn registerIn : registerInList) {
            if (registerIn.isLs()){
                lsFieldInfoMap.put(registerIn.getFieldName(), new FieldInfo(registerIn.getArticle().getId().toString(), registerIn.getLinkedFieldName()));
            }
            else if (registerIn.isAddress())
                addressFieldInfoMap.put(registerIn.getFieldName(), new FieldInfo(registerIn.getArticle().getId().toString(), registerIn.getLinkedFieldName()));
            else
                otherFieldInfoMap.put(registerIn.getFieldName(), new FieldInfo(registerIn.getArticle().getId().toString(), registerIn.getLinkedFieldName()));
        }

        return new Structure(lsFieldInfoMap, addressFieldInfoMap, otherFieldInfoMap);
    }
}