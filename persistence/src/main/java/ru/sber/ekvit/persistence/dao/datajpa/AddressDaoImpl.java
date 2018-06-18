package ru.sber.ekvit.persistence.dao.datajpa;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.sber.ekvit.persistence.Authorization;
import ru.sber.ekvit.persistence.dao.AddressDao;
import ru.sber.ekvit.persistence.dao.datajpa.crud.AddressCrudDao;
import ru.sber.ekvit.persistence.model.Address;

import java.util.List;

@Repository
@Slf4j
public class AddressDaoImpl implements AddressDao {

    private final AddressCrudDao addressCrudDao;

    @Autowired
    public AddressDaoImpl(AddressCrudDao addressCrudDao) {
        this.addressCrudDao = addressCrudDao;
    }

    @Override
    public Address save(Address entity) {
        log.info("save {} ", entity);

        return addressCrudDao.save(entity);
    }

    @Override
    public void saveAll(List<Address> entities) {
        log.info("saveAll ");

        addressCrudDao.saveAll(entities);
    }

    @Override
    public boolean delete(int id) {
        log.info("delete id {} ", id);

        return addressCrudDao.delete(id) != 0;
    }

    @Override
    public Address get(int id) {
        log.info("get id {} ", id);

        return addressCrudDao.findById(id).orElse(null);
    }

    @Override
    public List<Address> getAll() {
        log.info("getAll ");

        return addressCrudDao.findAll();
    }
}
