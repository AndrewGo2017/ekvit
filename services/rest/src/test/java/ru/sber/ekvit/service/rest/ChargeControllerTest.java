package ru.sber.ekvit.service.rest;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ChargeControllerTest extends Assertions {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void simpleCheck() throws Exception {
        mockMvc.perform(get("/charge/ленина/33/2")
                .contentType(MediaType.APPLICATION_XML_VALUE))
                .andExpect(status().isOk());
    }
}