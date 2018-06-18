package ru.sber.ekvit.persistence.datajpa;

import org.junit.Test;
import org.springframework.dao.DataIntegrityViolationException;
import ru.sber.ekvit.persistence.TestData;
import ru.sber.ekvit.persistence.model.Contract;
import ru.sber.ekvit.persistence.to.ContractTo;

import java.util.Arrays;


public class ContractDaoImpTest extends BaseToDaoTest<Contract, ContractTo> {
    @Test
    public void createTest() throws Exception {
        create(TestData.CONTRACT_NEW, TestData.CONTRACT1, TestData.CONTRACT2, TestData.CONTRACT3, TestData.CONTRACT_NEW);
    }

    @Test
    public void createToTest() throws Exception {
        create(new ContractTo(TestData.CONTRACT_NEW), Arrays.asList(TestData.CONTRACT1, TestData.CONTRACT2, TestData.CONTRACT3, TestData.CONTRACT_NEW), "contractor");
    }

    @Test
    public void updateTest() throws Exception {
        Contract updated = new Contract(TestData.CONTRACT1);
        updated.setName("new name");
        update(updated);
    }

    @Test
    public void updateToTest() throws Exception {
        ContractTo updated = new ContractTo(TestData.CONTRACT1);
        updated.setName("new name");
        update(updated, "contractor");
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void removeTest() throws Exception {
        remove(TestData.CONTRACT1.getId(), TestData.CONTRACT2, TestData.CONTRACT3);
    }

    @Test
    public void findTest() throws Exception {
        find(TestData.CONTRACT1.getId(), TestData.CONTRACT1);
    }

    @Test
    public void findAllTest() throws Exception {
        findAll(TestData.CONTRACTS);
    }
}