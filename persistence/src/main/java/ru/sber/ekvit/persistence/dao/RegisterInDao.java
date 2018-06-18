package ru.sber.ekvit.persistence.dao;

import ru.sber.ekvit.persistence.model.FieldType;
import ru.sber.ekvit.persistence.model.RegisterIn;
import ru.sber.ekvit.persistence.to.RegisterInTo;

import java.util.Arrays;
import java.util.List;

public interface RegisterInDao extends BaseToDao<RegisterIn, RegisterInTo> {
    RegisterIn save(RegisterInTo registerInTo);

    void deleteAllByContractId(int contractId);

    List<RegisterIn> getAllByContractId(int contractId);

    default List<FieldType> getFieldTypes(){
        return Arrays.asList(FieldType.values());
    }

}
