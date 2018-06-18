package ru.sber.ekvit.persistence.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import ru.sber.ekvit.persistence.dao.BaseToDao;
import ru.sber.ekvit.persistence.model.BaseEntity;
import ru.sber.ekvit.persistence.to.BaseTo;

import java.util.List;

public abstract class BaseToDaoTest<T1 extends BaseEntity, T2 extends BaseTo> extends BaseDaoTest<T1> {
    @Autowired
    private BaseToDao<T1, T2> dao;

    public void create(T2 entity, List<T1> expectedEntities, String... ignoringFields) {
        dao.save(entity);
        List actual = dao.getAll();
        assertThat(actual).usingElementComparatorIgnoringFields(ignoringFields).isEqualTo(expectedEntities);
    }

    public void update(T2 entity, String... ignoringFields) throws Exception {
        dao.save(entity);
        assertThat(dao.get(entity.getId())).isEqualToIgnoringGivenFields(entity, ignoringFields);
    }
}
