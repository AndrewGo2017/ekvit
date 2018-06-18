package ru.sber.ekvit.persistence.dao.datajpa;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.sber.ekvit.persistence.Authorization;
import ru.sber.ekvit.persistence.dao.ApplicationEventDao;
import ru.sber.ekvit.persistence.dao.datajpa.crud.ApplicationEventCrudDao;
import ru.sber.ekvit.persistence.model.ApplicationEvent;

import java.util.List;

@Repository
@Slf4j
public class ApplicationEventDaoImpl implements ApplicationEventDao {

    private final ApplicationEventCrudDao applicationEventCrudDao;

    @Autowired
    public ApplicationEventDaoImpl(ApplicationEventCrudDao contractorCrudDao) {
        this.applicationEventCrudDao = contractorCrudDao;
    }

    @Override
    public ApplicationEvent save(ApplicationEvent entity) {
        log.info("save {} ", entity);

        return applicationEventCrudDao.save(entity);
    }

    @Override
    public void saveAll(List<ApplicationEvent> entities) {
        log.info("saveAll ");

        applicationEventCrudDao.saveAll(entities);
    }

    @Override
    public boolean delete(int id) {
        log.info("delete id {} ", id);

        return applicationEventCrudDao.delete(id) != 0;
    }

    @Override
    public ApplicationEvent get(int id) {
        log.info("get id {} ", id);

        return applicationEventCrudDao.findById(id).orElse(null);
    }

    @Override
    public List<ApplicationEvent> getAll() {
        log.info("getAll ");

        return applicationEventCrudDao.findFirst100ByOrderByIdDesc();
    }
}
