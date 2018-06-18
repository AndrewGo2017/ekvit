package ru.sber.ekvit.job.register.schedule;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.sber.ekvit.job.BaseJobTest;
import ru.sber.ekvit.job.register.ChargesRegisterIn;

public class ChargesRegisterInTest extends BaseJobTest {
    @Autowired
    ChargesRegisterIn chargesRegisterIn;

    @Test
    public void checkSpeed() throws Exception{
        chargesRegisterIn.go();
    }
}