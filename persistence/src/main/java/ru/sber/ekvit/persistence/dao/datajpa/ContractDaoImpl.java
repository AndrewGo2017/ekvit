package ru.sber.ekvit.persistence.dao.datajpa;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.sber.ekvit.persistence.Authorization;
import ru.sber.ekvit.persistence.model.Contract;
import ru.sber.ekvit.persistence.dao.ContractDao;
import ru.sber.ekvit.persistence.dao.datajpa.crud.ContractCrudDao;
import ru.sber.ekvit.persistence.dao.datajpa.crud.ContractorCrudDao;
import ru.sber.ekvit.persistence.to.ContractTo;

import java.util.List;

@Repository
@Slf4j
public class ContractDaoImpl implements ContractDao {

    private final ContractCrudDao contractCrudDao;

    private final ContractorCrudDao contractorCrudDao;

    @Autowired
    public ContractDaoImpl(ContractCrudDao contractCrudDao, ContractorCrudDao contractorCrudDao) {
        this.contractCrudDao = contractCrudDao;
        this.contractorCrudDao = contractorCrudDao;
    }

    @Override
    public Contract save(Contract entity) {
        log.info("save {}", entity);

        return contractCrudDao.save(entity);
    }

    @Override
    public void saveAll(List<Contract> entities) {
        log.info("saveAll");

        contractCrudDao.saveAll(entities);
    }

    @Override
    public boolean delete(int id) {
        log.info("delete id {}", id);

        return contractCrudDao.delete(id) != 0;
    }

    @Override
    public Contract get(int id) {
        log.info("get id {}", id);

        return contractCrudDao.findById(id).orElse(null);
    }

    @Override
    public List<Contract> getAll() {
        log.info("getAll");

        return contractCrudDao.findAll();
    }

    @Transactional
    @Override
    public Contract save(ContractTo contractTo) {
        log.info("save TO {} from class {} by user id {}", contractTo);

        Contract contract = new Contract(
                contractTo.getId(),
                contractTo.getName(),
                contractorCrudDao.getOne(contractTo.getContractor()),
                contractTo.getNumb(),
                contractTo.getStartDate(),
                contractTo.getEndDate(),
                contractTo.getFileType(),
                contractTo.getFileEncoding(),
                contractTo.getDelimiter());

        return contractCrudDao.save(contract);
    }

    @Override
    public Contract getOne(int id) {
        log.info("getOne id {}", id);

        return contractCrudDao.getOne(id);
    }
}
