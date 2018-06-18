package ru.sber.ekvit.persistence.datajpa;

import org.junit.Test;
import ru.sber.ekvit.persistence.TestData;
import ru.sber.ekvit.persistence.model.RegisterIn;
import ru.sber.ekvit.persistence.to.RegisterInTo;

import java.util.Arrays;


public class RegisterInDaoImpTest extends BaseToDaoTest<RegisterIn, RegisterInTo> {
    @Test
    public void createTest() throws Exception {
        create(TestData.REGISTER_IN_NEW, TestData.REGISTER_IN1, TestData.REGISTER_IN2, TestData.REGISTER_IN3, TestData.REGISTER_IN_NEW);
    }

    @Test
    public void createToTest() throws Exception {
        create(new RegisterInTo(TestData.REGISTER_IN_NEW), Arrays.asList(TestData.REGISTER_IN1, TestData.REGISTER_IN2, TestData.REGISTER_IN3, TestData.REGISTER_IN_NEW), "contractor", "article");
    }

    @Test
    public void updateTest() throws Exception {
        RegisterIn updated = new RegisterIn(TestData.REGISTER_IN1);
        updated.setCode("new code");
        update(updated);
    }

    @Test
    public void updateToTest() throws Exception {
        RegisterInTo updated = new RegisterInTo(TestData.REGISTER_IN1);
        updated.setCode("new code");
        update(updated, "contract", "article");
    }

    @Test
    public void removeTest() throws Exception {
        remove(TestData.REGISTER_IN1.getId(), TestData.REGISTER_IN2, TestData.REGISTER_IN3);
    }

    @Test
    public void findTest() throws Exception {
        find(TestData.REGISTER_IN1.getId(), TestData.REGISTER_IN1);
    }

    @Test
    public void findAllTest() throws Exception {
        findAll(TestData.REGISTER_INS);
    }
}