package ru.sber.ekvit.persistence.dao.datajpa;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.sber.ekvit.persistence.Authorization;
import ru.sber.ekvit.persistence.dao.ConstantDao;
import ru.sber.ekvit.persistence.dao.datajpa.crud.ConstantCrudDao;
import ru.sber.ekvit.persistence.model.Constant;

import java.util.List;

@Repository
@Slf4j
    public class ConstantDaoImpl implements ConstantDao {
    private final ConstantCrudDao constantCrudDao;

    @Autowired
    public ConstantDaoImpl(ConstantCrudDao constantCrudDao) {
        this.constantCrudDao = constantCrudDao;
    }

    @Override
    public Constant save(Constant entity) {
        log.info("save {}", entity);

        return constantCrudDao.save(entity);
    }

    @Override
    public Constant get() {
        log.info("get _ ");

        return constantCrudDao.findById(1).orElse(null);
    }
}
