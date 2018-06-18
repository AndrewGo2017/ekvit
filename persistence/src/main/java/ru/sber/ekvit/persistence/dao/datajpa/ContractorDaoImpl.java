package ru.sber.ekvit.persistence.dao.datajpa;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.sber.ekvit.persistence.Authorization;
import ru.sber.ekvit.persistence.model.Contractor;
import ru.sber.ekvit.persistence.dao.ContractorDao;
import ru.sber.ekvit.persistence.dao.datajpa.crud.ContractorCrudDao;

import java.util.List;

@Repository
@Slf4j
public class ContractorDaoImpl implements ContractorDao {

    private final ContractorCrudDao contractorCrudDao;

    @Autowired
    public ContractorDaoImpl(ContractorCrudDao contractorCrudDao) {
        this.contractorCrudDao = contractorCrudDao;
    }

    @Override
    public Contractor save(Contractor entity) {
        log.info("save {}", entity);

        return contractorCrudDao.save(entity);
    }

    @Override
    public void saveAll(List<Contractor> entities) {
        log.info("saveAll");

        contractorCrudDao.saveAll(entities);
    }

    @Override
    public boolean delete(int id) {
        log.info("delete id {}", id);

        return contractorCrudDao.delete(id) != 0;
    }

    @Override
    public Contractor get(int id) {
        log.info("get id {}", id);

        return contractorCrudDao.findById(id).orElse(null);
    }

    @Override
    public List<Contractor> getAll() {
        log.info("getAll");

        return contractorCrudDao.findAll();
    }
}
