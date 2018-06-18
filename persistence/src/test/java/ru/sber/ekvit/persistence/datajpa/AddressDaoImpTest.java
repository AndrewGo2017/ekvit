package ru.sber.ekvit.persistence.datajpa;

import org.junit.Test;
import ru.sber.ekvit.persistence.TestData;
import ru.sber.ekvit.persistence.model.Address;


public class AddressDaoImpTest extends BaseDaoTest<Address> {
    @Test
    public void createTest() throws Exception {
        create(TestData.ADDRESS_NEW, TestData.ADDRESS1, TestData.ADDRESS2,  TestData.ADDRESS3,  TestData.ADDRESS_NEW );
    }

    @Test
    public void updateTest() throws Exception {
        Address updated = new Address(TestData.ADDRESS1);
        updated.setCode("new code");
        update(updated);
    }

    @Test
    public void removeTest() throws Exception {
        remove(TestData.ADDRESS1.getId(), TestData.ADDRESS2, TestData.ADDRESS3);
    }

    @Test
    public void findTest() throws Exception {
       find(TestData.ADDRESS1.getId(), TestData.ADDRESS1);
    }

    @Test
    public void findAllTest() throws Exception {
        findAll(TestData.ADDRESSES);
    }
}