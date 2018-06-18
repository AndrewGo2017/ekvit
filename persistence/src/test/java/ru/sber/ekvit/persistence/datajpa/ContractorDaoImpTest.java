package ru.sber.ekvit.persistence.datajpa;

import org.junit.Test;
import org.springframework.dao.DataIntegrityViolationException;
import ru.sber.ekvit.persistence.TestData;
import ru.sber.ekvit.persistence.dao.BaseDao;
import ru.sber.ekvit.persistence.model.*;


public class ContractorDaoImpTest extends BaseDaoTest<Contractor> {
    @Test
    public void createTest() throws Exception {
        create(TestData.CONTRACTOR_NEW, TestData.CONTRACTOR1, TestData.CONTRACTOR2,  TestData.CONTRACTOR3,  TestData.CONTRACTOR_NEW );
    }

    @Test
    public void updateTest() throws Exception {
        Contractor updated = new Contractor(TestData.CONTRACTOR1);
        updated.setName("new name");
        update(updated);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void removeTest() throws Exception {
        remove(TestData.CONTRACTOR1.getId(), TestData.CONTRACTOR2, TestData.CONTRACTOR3);
    }

    @Test
    public void findTest() throws Exception {
       find(TestData.CONTRACTOR1.getId(), TestData.CONTRACTOR1);
    }

    @Test
    public void findAllTest() throws Exception {
        findAll(TestData.CONTRACTORS);
    }
}