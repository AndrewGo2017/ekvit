package ru.sber.ekvit.persistence.dao;

import ru.sber.ekvit.persistence.model.BaseEntity;
import ru.sber.ekvit.persistence.to.BaseTo;

public interface BaseToDao<T1 extends BaseEntity, T2 extends BaseTo> extends BaseDao<T1> {
    T1 save(T2 entity);
}
