package ru.sber.ekvit.persistence.datajpa;

import org.junit.Test;
import ru.sber.ekvit.persistence.TestData;
import ru.sber.ekvit.persistence.model.AddressElement;
import ru.sber.ekvit.persistence.to.AddressElementTo;

import java.util.Arrays;


public class AddressElementDaoImpTest extends BaseToDaoTest<AddressElement, AddressElementTo> {
    @Test
    public void createTest() throws Exception {
        create(TestData.ADDRESS_ELEMENT_NEW, TestData.ADDRESS_ELEMENT1, TestData.ADDRESS_ELEMENT2, TestData.ADDRESS_ELEMENT3, TestData.ADDRESS_ELEMENT_NEW);
    }

    @Test
    public void createToTest() throws Exception {
        create(new AddressElementTo(TestData.ADDRESS_ELEMENT_NEW), Arrays.asList(TestData.ADDRESS_ELEMENT1, TestData.ADDRESS_ELEMENT2, TestData.ADDRESS_ELEMENT3, TestData.ADDRESS_ELEMENT_NEW), "contract");
    }

    @Test
    public void updateTest() throws Exception {
        AddressElement updated = new AddressElement(TestData.ADDRESS_ELEMENT1);
        updated.setDelimiter("new del");
        update(updated);
    }

    @Test
    public void updateToTest() throws Exception {
        AddressElementTo updated = new AddressElementTo(TestData.ADDRESS_ELEMENT1);
        updated.setDelimiter("new del");
        update(updated, "contract");
    }

    @Test
    public void removeTest() throws Exception {
        remove(TestData.ADDRESS_ELEMENT1.getId(), TestData.ADDRESS_ELEMENT2, TestData.ADDRESS_ELEMENT3);
    }

    @Test
    public void findTest() throws Exception {
        find(TestData.ADDRESS_ELEMENT1.getId(), TestData.ADDRESS_ELEMENT1);
    }

    @Test
    public void findAllTest() throws Exception {
        findAll(TestData.ADDRESS_ELEMENTS);
    }
}