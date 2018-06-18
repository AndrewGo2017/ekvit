//
// generic base controller class for crud operations
//

package ru.sber.ekvit.web.controller;

import lombok.extern.slf4j.Slf4j;
import ru.sber.ekvit.persistence.Authorization;
import ru.sber.ekvit.persistence.dao.BaseDao;
import ru.sber.ekvit.persistence.model.BaseEntity;

import java.util.List;

@Slf4j
class BaseController<T extends BaseEntity> {
    private final BaseDao<T> dao;

    BaseController(BaseDao<T> dao) {
        this.dao = dao;
    }

    List<T> _getAll(){
        log.info("getAll");
        return dao.getAll();
    }

    T _get(Integer id){
        log.info("get id {}",id);
        return dao.get(id);
    }

    T _save(T entity) {
        log.info("save {}",entity);
        return dao.save(entity);
    }

    boolean _delete(Integer id) {
        log.info("delete id {}",id);
        return dao.delete(id);
    }
}
