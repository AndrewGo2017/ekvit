package ru.sber.ekvit.persistence.dao;

import ru.sber.ekvit.persistence.model.Constant;
public interface ConstantDao {
    Constant save(Constant entity);

    Constant get();
}
