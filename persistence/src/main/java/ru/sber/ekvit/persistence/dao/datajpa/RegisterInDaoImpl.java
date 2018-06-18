package ru.sber.ekvit.persistence.dao.datajpa;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.sber.ekvit.persistence.Authorization;
import ru.sber.ekvit.persistence.model.RegisterIn;
import ru.sber.ekvit.persistence.dao.RegisterInDao;
import ru.sber.ekvit.persistence.dao.datajpa.crud.ArticleCrudDao;
import ru.sber.ekvit.persistence.dao.datajpa.crud.ContractCrudDao;
import ru.sber.ekvit.persistence.dao.datajpa.crud.RegisterInCrudDao;
import ru.sber.ekvit.persistence.to.RegisterInTo;

import java.util.List;

@Repository
@Slf4j
public class RegisterInDaoImpl implements RegisterInDao {

    private final RegisterInCrudDao registerInCrudDao;

    private final ContractCrudDao contractCrudDao;

    private final ArticleCrudDao articleCrudDao;

    @Autowired
    public RegisterInDaoImpl(RegisterInCrudDao registerInCrudDao, ContractCrudDao contractCrudDao, ArticleCrudDao articleCrudDao) {
        this.registerInCrudDao = registerInCrudDao;
        this.contractCrudDao = contractCrudDao;
        this.articleCrudDao = articleCrudDao;
    }

    @Override
    public RegisterIn save(RegisterIn entity) {
        log.info("save {}", entity);

        return registerInCrudDao.save(entity);
    }

    @Override
    public void saveAll(List<RegisterIn> list) {
        log.info("saveAll");

        registerInCrudDao.saveAll(list);
    }

    @Override
    public boolean delete(int id) {
        log.info("delete id {}", id);

        return registerInCrudDao.delete(id) != 0;
    }

    @Override
    public void deleteAllByContractId(int contractId) {
        log.info("deleteAllByContractId {}",contractId);

        List<RegisterIn> registerInList = getAllByContractId(contractId);
        registerInCrudDao.deleteAll(registerInList);
    }

    @Override
    public RegisterIn get(int id) {
        log.info("get id {}", id);

        return registerInCrudDao.findById(id).orElse(null);
    }

    @Override
    public List<RegisterIn> getAll() {
        log.info("getAll");

        return registerInCrudDao.findAll();
    }

    @Override
    public RegisterIn save(RegisterInTo registerInTo) {
        log.info("save TO {}",registerInTo);

        RegisterIn registerIn = new RegisterIn(
                registerInTo.getId(),
                contractCrudDao.getOne(registerInTo.getContract()),
                articleCrudDao.getOne(registerInTo.getArticle()),
                registerInTo.getFieldName(),
                registerInTo.getFieldType(),
                registerInTo.getLength(),
                registerInTo.getPrecision(),
                registerInTo.getDescription(),
                registerInTo.getCode(),
                registerInTo.getLinkedFieldName());

        return registerInCrudDao.save(registerIn);
    }

    @Override
    public List<RegisterIn> getAllByContractId(int contractId) {
        log.info("getAllByContractId {}",contractId);

        return registerInCrudDao.getAllByContractId(contractId);
    }
}