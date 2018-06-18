package ru.sber.ekvit.persistence.datajpa;

import org.junit.Test;
import ru.sber.ekvit.persistence.TestData;
import ru.sber.ekvit.persistence.model.CorrectAddress;
import ru.sber.ekvit.persistence.to.CorrectAddressTo;

import java.util.Arrays;


public class CorrectAddressDaoImpTest extends BaseToDaoTest<CorrectAddress, CorrectAddressTo> {
    @Test
    public void updateTest() throws Exception {
        CorrectAddress updated = new CorrectAddress(TestData.CORRECT_ADDRESS2);
        updated.setLs("new ls");
    }

    @Test
    public void updateToTest() throws Exception {
        CorrectAddressTo updated = new CorrectAddressTo(TestData.CORRECT_ADDRESS1);
        updated.setLs("new ls");
        update(updated, "address", "contract");
    }

    @Test
    public void findTest() throws Exception {
        find(TestData.CORRECT_ADDRESS1.getId(), TestData.CORRECT_ADDRESS1);
    }
}