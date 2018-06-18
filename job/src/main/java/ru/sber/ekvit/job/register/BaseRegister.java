//
// base class for handling files (registers)
//

package ru.sber.ekvit.job.register;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import ru.sber.ekvit.job.JobType;
import ru.sber.ekvit.job.util.CommonUtil;
import ru.sber.ekvit.persistence.dao.ApplicationEventDao;
import ru.sber.ekvit.persistence.dao.BaseDao;
import ru.sber.ekvit.persistence.dao.ContractDao;
import ru.sber.ekvit.persistence.model.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

import static ru.sber.ekvit.job.util.CommonUtil.getFileExtension;
import static ru.sber.ekvit.job.util.CommonUtil.tryParseInt;

@Slf4j
public abstract class BaseRegister <T extends BaseEntity> {
    private static final String FILE_NAME_REGEX = "__\\d+__(.+)"; //special inline part that added to file name. contains contract id
    public static final String SPECIAL_SYMBOL = "__"; //part of FILE_NAME_REGEX var

    static final Integer CHARGE_LIST_LIMIT = 1000; //list size that accumulates charges objs to save

    private final ContractDao contractDao;
    private final BaseDao<T> baseDao;
    private final ApplicationEventDao applicationEventDao;

    private final Path searchDirectory;
    private final JobType jobType;

    BaseRegister(BaseDao<T> baseDao, ContractDao contractDao, ApplicationEventDao applicationEventDao, Path searchDirectory, JobType jobType) {
        this.baseDao = baseDao;
        this.contractDao = contractDao;
        this.applicationEventDao = applicationEventDao;
        this.searchDirectory = searchDirectory;
        this.jobType = jobType;
    }

    // save execution status
    void handleFile() throws IOException, FileTypeException {
        try {
            go();
            applicationEventDao.save(new ApplicationEvent(LocalDateTime.now(), jobType.getStr(),"", ApplicationStatus.INFO));
        } catch (Exception e) {
            try {
                applicationEventDao.save(new ApplicationEvent(LocalDateTime.now(), jobType.getStr(), ExceptionUtils.getRootCauseMessage(e), ApplicationStatus.ERROR));
            } catch (Exception ignored){}
            throw e;
        }
    }

    private void go() throws IOException, FileTypeException {
        log.info("handleFile");

        //get file or directory with files to handle
        File searchFile = searchDirectory.toFile();
        log.info("handleFile ( with searchDirectory: {} ) ", searchDirectory);

        if (!searchFile.exists()) return;

        //add all found files into list
        List<File> files = new ArrayList<>();
        if (searchFile.isDirectory()){
            File[] listFiles = searchFile.listFiles();
            if (listFiles != null)
                files.addAll(Arrays.asList(listFiles));
        } else {
            files.add(searchFile);
        }
        log.info("handleFile ( with files: {} )", searchFile);

        //store exception cases
        StringJoiner exceptionInfo = new StringJoiner(";");

        //need to unzip zip files
        //cannot add objects into array (files var) while iteration, so create another list of zip files
        List<File> filesFromZip = new ArrayList<>();
        for (File file : files){
            if (CommonUtil.getFileExtension(file).equals("ZIP")){
                for (File f : CommonUtil.unZip(file)){
                    String contractId = CommonUtil.getContractIdFromFileName(file.getName());
                    Path movePath = Files.move(f.toPath(), CommonUtil.renameUploadedFile(f, contractId).toPath());
                    filesFromZip.add(movePath.toFile());
                }
            }
        }
        log.info("handleFile ( with filesFromZip: {} )", filesFromZip,  this.getClass().getSimpleName());

        //collect all files
        files.addAll(filesFromZip);

        //handle or ignore found files
        for (File file : files) {
            log.info("handleFile ( with file: "+file+" )  " + this.getClass().getSimpleName());

            if (!file.exists()) continue;
            String fileName = file.getName();
            if (fileName.matches(FILE_NAME_REGEX)) {
                String contractId = CommonUtil.getContractIdFromFileName(fileName);
                Contract contract = contractDao.get(tryParseInt(contractId));
                if (contract != null) {
                    String ext = getFileExtension(file);
                    if (ext.equals(FileType.DBF.toString())) {
                        if (contract.getFileType() == FileType.DBF)
                            handleDBF(file, contract);
                        else
                            exceptionInfo.add("Тип файла не соответствует заявленному! Тип должет быть " + contract.getFileType() + ". Договор: " + contractId + ". Файл: " + fileName);
                    } else if (ext.equals(FileType.TXT.toString())) {
                        if (contract.getFileType() == FileType.TXT)
                            handleTXT(file, contract);
                        else
                            exceptionInfo.add("Тип файла не соответствует заявленному! Тип должет быть " + contract.getFileType()  + ". Договор: " + contractId + ". Файл: " + fileName);
                    } else {
                        exceptionInfo.add("Неверный тип файла (" + ext + ")! Тип должет быть " + FileType.DBF.toString() + " или "+  FileType.TXT.toString() + ". Если используется архив, то архив должен быть в формате ZIP."  + ". Договор: " + contractId + ". Файл: " + fileName);
                    }
                }
            }

            log.info("handleFile ( with finished file: {} ) ", file);
            log.info("handleFile ( with delete file: {} )", file);
            CommonUtil.deleteFile(file);

            if (exceptionInfo.length() > 0)
                throw new FileTypeException(exceptionInfo.toString());
        }
    }

    //need to by synchronized to write only one list at a time
    synchronized void saveList(List<T> entities){
        if (entities.size() > 0) {
            baseDao.saveAll(entities);
        }
    }

    protected abstract void handleDBF(File file, Contract contract) throws IOException;

    protected abstract void handleTXT(File file, Contract contract) throws IOException;
}
