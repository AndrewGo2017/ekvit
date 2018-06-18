package ru.sber.ekvit.persistence.dao.datajpa;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.sber.ekvit.persistence.Authorization;
import ru.sber.ekvit.persistence.dao.AddressElementDao;
import ru.sber.ekvit.persistence.dao.datajpa.crud.AddressElementCrudDao;
import ru.sber.ekvit.persistence.dao.datajpa.crud.ContractCrudDao;
import ru.sber.ekvit.persistence.model.AddressElement;
import ru.sber.ekvit.persistence.to.AddressElementTo;

import java.util.List;

@Repository
@Slf4j
public class AddressElementDaoImpl implements AddressElementDao {

    private final AddressElementCrudDao addressElementCrudDao;

    private final ContractCrudDao contractCrudDao;

    @Autowired
    public AddressElementDaoImpl(AddressElementCrudDao contractorCrudDao, ContractCrudDao contractCrudDao) {
        this.addressElementCrudDao = contractorCrudDao;
        this.contractCrudDao = contractCrudDao;
    }

    @Override
    public AddressElement save(AddressElement entity) {
        log.info("save {}", entity);

        return addressElementCrudDao.save(entity);
    }

    @Override
    public void saveAll(List<AddressElement> entities) {
        log.info("saveAll");

        addressElementCrudDao.saveAll(entities);
    }

    @Override
    public boolean delete(int id) {
        log.info("delete id {}", id);

        return addressElementCrudDao.delete(id) != 0;
    }

    @Override
    public AddressElement get(int id) {
        log.info("get id {}", id);

        return addressElementCrudDao.findById(id).orElse(null);
    }

    @Override
    public List<AddressElement> getAll() {
        log.info("getAll");

        return addressElementCrudDao.findAll();
    }

    @Override
    public AddressElement save(AddressElementTo addressElementTo) {
        log.info("save {} ", addressElementTo);

        AddressElement addressElement = new AddressElement(
                addressElementTo.getId(),
                contractCrudDao.getOne(addressElementTo.getContract()),
                addressElementTo.getDelimiter(),
                addressElementTo.getFieldStreet(),
                addressElementTo.getPosStreet(),
                addressElementTo.getFieldHouse(),
                addressElementTo.getPosHouse(),
                addressElementTo.getFieldApartment(),
                addressElementTo.getPosApartment()
        );
        
        return addressElementCrudDao.save(addressElement);
    }
}
