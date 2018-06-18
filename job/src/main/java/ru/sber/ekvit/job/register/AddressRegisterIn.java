//
// scheduled only
// filling addresses table with data from file
//

package ru.sber.ekvit.job.register;

import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.sber.ekvit.job.JobType;
import ru.sber.ekvit.persistence.dao.*;
import ru.sber.ekvit.persistence.model.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

import static ru.sber.ekvit.job.util.CommonUtil.tryParseInt;

@Component
@Slf4j
class AddressRegisterIn extends BaseAdrRegister<Address> {
    static final String SEARCH_DIRECTORY = "RegisterInMainAddress";

    private final AddressElementDao addressElementDao;
    private final RegisterInDao registerInDao;
    private final AddressDao addressDao;

    @Autowired
    public AddressRegisterIn(ContractDao contractDao, AddressElementDao addressElementDao, RegisterInDao registerInDao, AddressDao addressDao, ConstantDao constantDao, ApplicationEventDao applicationEventDao) {
        super(addressDao ,contractDao, applicationEventDao, Paths.get(constantDao.get().getMainPath(),SEARCH_DIRECTORY), JobType.AddressRegisterInJob);
        this.addressElementDao = addressElementDao;
        this.registerInDao = registerInDao;
        this.addressDao = addressDao;
    }

    @Scheduled(fixedRate = 7200000L) // 2h
    public synchronized void go() throws IOException, FileTypeException {
        log.info("go");

        handleFile();
    }

    //all below - similar (but not alike) to ChargesRegisterIn
    @Override
    protected void handleDBF(File file, Contract contract) throws IOException {
        log.info("handleDBF (with file: {}, contract:{})", file, contract);

        try (InputStream inputStream = new FileInputStream(file)) {
            DBFReader reader = new DBFReader(inputStream);
            reader.setCharactersetName(contract.getFileEncoding().getStr());

            AddressElement addressElement = addressElementDao.get(contract.getId());

            List<String> addressFiledNameList = getAddressFieldNameList(contract);

            Map<Integer, String> addressFieldIndexMap = new LinkedHashMap<>();
            for (int i = 0; i < reader.getFieldCount(); i++) {
                DBFField field = reader.getField(i);

                if (addressFiledNameList.contains(field.getName()))
                    addressFieldIndexMap.put(i, field.getName());
            }

            List<Address> addressListToSave = new ArrayList<>();
            Object[] rowObjects;
            while ((rowObjects = reader.nextRecord()) != null) {
                Address address = new Address();

                StringBuilder addressValue = new StringBuilder();

                Map<String, String> addressNameValueElementMap = new HashMap<>();

                for (Map.Entry<Integer, String> entry : addressFieldIndexMap.entrySet()) {
                    addressNameValueElementMap.put(entry.getValue(), rowObjects[entry.getKey()].toString());
                    addressValue.append(rowObjects[entry.getKey()].toString()).append(addressElement.getDelimiter());
                }

                Adr adr = getAdr(addressNameValueElementMap, addressElement);
                address.setStreet(normalizeAdrString(adr.street));
                address.setHouse(normalizeAdrString(adr.house));
                address.setApartment(normalizeAdrString(adr.apartment));

                address.setAddress(addressValue.toString().substring(0, addressValue.length() - 1));
                address.setUploadDate(LocalDateTime.now());

                addressListToSave.add(address);
                if (addressListToSave.size() > CHARGE_LIST_LIMIT){
                    new Thread( ()-> saveList(addressListToSave)).run();
                    addressListToSave.clear();
                }
            }
            saveList(addressListToSave);
        }
    }

    @Override
    protected void handleTXT(File file, Contract contract) throws IOException {
        log.info("handleTXT (with file: {}, contract:{})", file, contract);

        try (Stream<String> stream = Files.lines(Paths.get(file.toURI()), Charset.forName(contract.getFileEncoding().getStr()))) {
            List<Address> addressListToSave = new ArrayList<>();
            stream.forEach(line -> {
                Address address = new Address();

                AddressElement addressElement = addressElementDao.get(contract.getId());

                List<String> addressFiledNameList = getAddressFieldNameList(contract);


                String[] subLines = line.split(contract.getDelimiter());
                int maxIndex = subLines.length - 1;

                StringBuilder addressValue = new StringBuilder();

                Map<String, String> addressNameValueElementMap = new HashMap<>();
                for (String fieldIndex : addressFiledNameList) {
                    int index = tryParseInt(fieldIndex) - 1;
                    if (index == -2 || index >= maxIndex)
                        continue;

                    addressNameValueElementMap.put(String.valueOf(index), subLines[index]);
                    addressValue.append(subLines[index]);
                }


                Adr adr = getAdr(addressNameValueElementMap, addressElement);
                address.setStreet(normalizeAdrString(adr.street));
                address.setHouse(normalizeAdrString(adr.house));
                address.setApartment(normalizeAdrString(adr.apartment));

                address.setAddress(addressValue.toString().substring(0, addressValue.length() - 1));
                address.setUploadDate(LocalDateTime.now());

                addressListToSave.add(address);
                if (addressListToSave.size() > CHARGE_LIST_LIMIT){
                    new Thread( ()-> saveList(addressListToSave)).run();
                    addressListToSave.clear();
                }
            });
            saveList(addressListToSave);
        }
    }

    private List<String> getAddressFieldNameList(Contract contract) {
        List<RegisterIn> registerInList = registerInDao.getAllByContractId(contract.getId());

        List<String> addressFieldNameList = new ArrayList<>();

        for (RegisterIn registerIn : registerInList) {
            if (registerIn.isAddress())
                addressFieldNameList.add(registerIn.getFieldName());
        }

        return addressFieldNameList;
    }

    private synchronized void saveAddressList(List<Address> addresses){
        if (addresses.size() > 0) {
            addressDao.saveAll(addresses);
        }
    }
}