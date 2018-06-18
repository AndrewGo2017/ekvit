package ru.sber.ekvit.persistence.dao;

import ru.sber.ekvit.persistence.model.Contract;
import ru.sber.ekvit.persistence.model.FileEncoding;
import ru.sber.ekvit.persistence.model.FileType;
import ru.sber.ekvit.persistence.to.ContractTo;

import java.util.Arrays;
import java.util.List;

public interface ContractDao extends BaseToDao<Contract, ContractTo> {
    Contract save(ContractTo contractTo);

    Contract getOne(int id);

    default List<FileType> getFileTypes(){
        return Arrays.asList(FileType.values());
    }

    default List<FileEncoding> getFileEncodings(){
        return Arrays.asList(FileEncoding.values());
    }
}
