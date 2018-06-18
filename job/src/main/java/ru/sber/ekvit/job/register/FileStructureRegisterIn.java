//
// callable only
// filling register_in table with data from files
//

package ru.sber.ekvit.job.register;

import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sber.ekvit.job.JobType;
import ru.sber.ekvit.job.register.BaseRegister;
import ru.sber.ekvit.persistence.dao.*;
import ru.sber.ekvit.persistence.model.*;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


@Component
@Slf4j
public class FileStructureRegisterIn extends BaseRegister<RegisterIn> {
    static final String SEARCH_DIRECTORY = "FileStructureRegisterIn";
    private static final int COMMON_ARTICLE_ID = 4;

    private final ArticleDao articleDao;
    private final RegisterInDao registerInDao;
    
    @Autowired
    public FileStructureRegisterIn(ContractDao contractDao, RegisterInDao registerInDao, ArticleDao articleDao, ApplicationEventDao applicationEventDao, ConstantDao constantDao) {
        super(registerInDao, contractDao, applicationEventDao, Paths.get(constantDao.get().getMainPath(), SEARCH_DIRECTORY), JobType.FileStructureRegisterInJob);
        this.articleDao = articleDao;
        this.registerInDao = registerInDao;
    }

    void go() throws IOException, FileTypeException {
        log.info("go ");

        handleFile();
    }

    @Override
    protected void handleDBF(File file, Contract contract) throws IOException {
        log.info("handleDBF (with file: {}, contract:{}) ", file, contract);

        //collect data into list and then add all data to db. so if there is any error changes wouldn't be committed
        List<RegisterIn> registerInList = new ArrayList<>();
        try (InputStream inputStream = new FileInputStream(file)) {
            DBFReader reader = new DBFReader(inputStream);
            reader.setCharactersetName(contract.getFileEncoding().getStr());

            for (int i = 0; i < reader.getFieldCount(); i++) {
                DBFField field = reader.getField(i);

                RegisterIn registerIn = new RegisterIn();
                registerIn.setContract(contract);
                registerIn.setArticle(articleDao.getOne(COMMON_ARTICLE_ID));
                registerIn.setFieldName(field.getName());

                FieldType fieldType;
                switch (String.valueOf((char) field.getDataType())) {
                    case "C":
                        fieldType = FieldType.C;
                        break;
                    case "N":
                        fieldType = FieldType.N;
                        break;
                    case "D":
                        fieldType = FieldType.D;
                        break;
                    case "F":
                        fieldType = FieldType.N;
                        break;
                    case "L":
                        fieldType = FieldType.L;
                        break;
                    default:
                        fieldType = FieldType.C;
                }
                registerIn.setFieldType(fieldType);
                registerIn.setLength(field.getFieldLength());
                registerIn.setPrecision(field.getDecimalCount());

                registerInList.add(registerIn);
            }
        }

        //delete old data
        registerInDao.deleteAllByContractId(contract.getId());
        //save new data
        registerInDao.saveAll(registerInList);

    }

    @Override
    protected void handleTXT(File file, Contract contract) throws IOException {
        log.info("handleTXT (with file: {}, contract:{}) ", file, contract);

        List<RegisterIn> registerInList = new ArrayList<>();

        try(Stream<String> stream = Files.lines(Paths.get(file.toURI()), Charset.forName(contract.getFileEncoding().getStr()))){
            String line = stream.findFirst().orElse("");

            String[] subLines = line.split(contract.getDelimiter());
            int counter = 0;
            for (String str : subLines) {
                counter++;
                RegisterIn registerIn = new RegisterIn();
                registerIn.setContract(contract);
                registerIn.setArticle(articleDao.getOne(COMMON_ARTICLE_ID));
                registerIn.setFieldName(String.valueOf(counter));

                //no chance to know details for sure, so set with default values
                registerIn.setFieldType(FieldType.C);
                registerIn.setLength(50);
                registerIn.setPrecision(0);

                registerInList.add(registerIn);
            }
        }

        //delete old data
        registerInDao.deleteAllByContractId(contract.getId());
        //save new data
        registerInDao.saveAll(registerInList);
    }
}