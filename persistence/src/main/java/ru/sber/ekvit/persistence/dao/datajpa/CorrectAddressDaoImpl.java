package ru.sber.ekvit.persistence.dao.datajpa;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.sber.ekvit.persistence.Authorization;
import ru.sber.ekvit.persistence.dao.CorrectAddressDao;
import ru.sber.ekvit.persistence.dao.datajpa.crud.AddressCrudDao;
import ru.sber.ekvit.persistence.dao.datajpa.crud.ContractCrudDao;
import ru.sber.ekvit.persistence.dao.datajpa.crud.CorrectAddressCrudDao;
import ru.sber.ekvit.persistence.model.CorrectAddress;
import ru.sber.ekvit.persistence.to.CorrectAddressTo;

import java.util.List;

@Repository
@Slf4j
public class CorrectAddressDaoImpl implements CorrectAddressDao {

    private final ContractCrudDao contractCrudDao;

    private final AddressCrudDao addressCrudDao;

    private final CorrectAddressCrudDao correctAddressCrudDao;

    @Autowired
    public CorrectAddressDaoImpl(ContractCrudDao contractCrudDao, AddressCrudDao addressCrudDao, CorrectAddressCrudDao correctAddressCrudDao) {
        this.contractCrudDao = contractCrudDao;
        this.addressCrudDao = addressCrudDao;
        this.correctAddressCrudDao = correctAddressCrudDao;
    }

    @Override
    public CorrectAddress save(CorrectAddress entity) {
        log.info("save {}", entity);

        return correctAddressCrudDao.save(entity);
    }

    @Override
    public void saveAll(List<CorrectAddress> entities) {
        log.info("saveAll");

        correctAddressCrudDao.saveAll(entities);
    }

    @Override
    public boolean delete(int id) {
        log.info("delete id {}", id);

        return correctAddressCrudDao.delete(id) != 0;
    }

    @Override
    public CorrectAddress get(int id) {
        log.info("get id {}", id);

        return correctAddressCrudDao.findById(id).orElse(null);
    }

    @Override
    public List<CorrectAddress> getAll() {
        log.info("getAll");

        return correctAddressCrudDao.findFirstByOrderByIdAsc();
    }

    @Transactional
    @Override
    public CorrectAddress save(CorrectAddressTo correctAddressTo) {
        log.info("save TO {}", correctAddressTo);

        CorrectAddress correctAddress = new CorrectAddress();
        correctAddress.setId(correctAddressTo.getId());
        correctAddress.setAddress(addressCrudDao.getOne(correctAddressTo.getAddress()));
        correctAddress.setContract(contractCrudDao.getOne(correctAddressTo.getContract()));
        correctAddress.setLs(correctAddressTo.getLs());

        return correctAddressCrudDao.save(correctAddress);
    }
}
