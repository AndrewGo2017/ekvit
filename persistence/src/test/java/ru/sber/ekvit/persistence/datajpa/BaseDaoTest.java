package ru.sber.ekvit.persistence.datajpa;

import org.assertj.core.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.sber.ekvit.persistence.dao.BaseDao;
import ru.sber.ekvit.persistence.model.BaseEntity;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
@Sql(executionPhase= Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"/init_db.sql", "/populate_db.sql"}, config = @SqlConfig(encoding = "UTF-8"))
public abstract class BaseDaoTest <T extends BaseEntity> extends Assertions {
    @Autowired
    private BaseDao<T> dao;

    BaseDaoTest() {
    }

    @SafeVarargs
    public final void create(T entity, T... expectedEntities) throws Exception {
        dao.save(entity);
        List actual = dao.getAll();
        List<T> expected = Arrays.asList(expectedEntities);
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }

    public void update(T entity) throws Exception {
        dao.save(entity);
        assertThat(dao.get(entity.getId())).isEqualToComparingFieldByField(entity);
    }

    @SafeVarargs
    public final void remove(int id, T... expectedEntities) throws Exception {
        dao.delete(id);
        List actual = dao.getAll();
        List<T> expected = Arrays.asList(expectedEntities);
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }

    public void find(int id, T expectedEntity) throws Exception {
        T actual = dao.get(id);
        assertThat(actual).isEqualToComparingFieldByField(expectedEntity);
    }

    public void findAll(List<T> expectedEntities) throws Exception {
        List actual = dao.getAll();
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expectedEntities);
    }
}