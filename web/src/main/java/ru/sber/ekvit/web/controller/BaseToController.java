//
//generic base controller class for crud operations on TO objects
//


package ru.sber.ekvit.web.controller;

import lombok.extern.slf4j.Slf4j;
import ru.sber.ekvit.persistence.Authorization;
import ru.sber.ekvit.persistence.dao.BaseToDao;
import ru.sber.ekvit.persistence.model.BaseEntity;
import ru.sber.ekvit.persistence.to.BaseTo;

@Slf4j
class BaseToController<T1 extends BaseEntity, T2 extends BaseTo> extends BaseController<T1> {
    private final BaseToDao<T1, T2> dao;

    BaseToController(BaseToDao<T1, T2> dao) {
        super(dao);
        this.dao = dao;
    }

    T1 _save(T2 entity) {
        log.info("save TO {}",entity);
        return dao.save(entity);
    }
}