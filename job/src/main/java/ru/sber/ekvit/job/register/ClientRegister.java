//
// class for handling calls from another modules
//

package ru.sber.ekvit.job.register;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.sber.ekvit.job.JobType;
import ru.sber.ekvit.persistence.Authorization;
import ru.sber.ekvit.persistence.dao.ConstantDao;
import ru.sber.ekvit.persistence.model.Constant;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import static ru.sber.ekvit.job.util.CommonUtil.renameUploadedFile;

@Component
@Slf4j
public class ClientRegister {
    private final FileStructureRegisterIn fileStructureRegisterIn;
    private final ConstantDao constantDao;

    public ClientRegister(FileStructureRegisterIn fileStructureRegisterIn, ConstantDao constantDao) {
        this.fileStructureRegisterIn = fileStructureRegisterIn;
        this.constantDao = constantDao;
    }

    public void handle(MultipartFile[] files, String contract, JobType jobType) throws IOException, FileTypeException {
        log.info("handle (with files: {}, contract: {}, jobType: {}). user: {}", files, contract, jobType, Authorization.id());

        for (MultipartFile file : files) {
            Constant constant = constantDao.get();
            String mainPath = constant.getMainPath();
            String catalog;
            switch (jobType) {
                case ChargesRegisterInJob:
                    catalog = ChargesRegisterIn.SEARCH_DIRECTORY;
                    break;
                case FileStructureRegisterInJob:
                    catalog = FileStructureRegisterIn.SEARCH_DIRECTORY;
                    break;
                default:
                    catalog = AddressRegisterIn.SEARCH_DIRECTORY;
            }

            //rename file, so there would be info about contract id
            File f = renameUploadedFile(Paths.get(mainPath, catalog, file.getOriginalFilename()).toFile(), contract);
            //save file on disc
            //***need to be saved before handling as it might be bad for cashing all file data into ram (file may be very big). i actually don't like it, but i have no idea how it can be done another (better) way
            file.transferTo(f);

            //if it is a 'callable' job then do it
            if (jobType == JobType.FileStructureRegisterInJob)
                fileStructureRegisterIn.go();
        }
    }
}
