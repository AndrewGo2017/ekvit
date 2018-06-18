package ru.sber.ekvit.persistence.dao;

import ru.sber.ekvit.persistence.model.BaseEntity;

import java.util.List;

public interface BaseDao<T extends BaseEntity> {
    T save(T entity);

    void saveAll(List<T> entities);

    boolean delete(int id);

    T get(int id);

    List<T> getAll();
}
