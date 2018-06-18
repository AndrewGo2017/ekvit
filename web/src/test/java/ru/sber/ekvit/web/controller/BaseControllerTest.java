package ru.sber.ekvit.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username="user",roles={"ADMIN"},password = "")
public class BaseControllerTest extends Assertions {

    @Autowired
    private MockMvc mockMvc;

    private static List<String> baseUrls;

    static {
        baseUrls = new ArrayList<>(Arrays.asList(
                "/contract",
                "/contractor",
                "/addressElement",
                "/article",
                "/charge",
                "/correctAddress",
                "/login",
                "/registerin",
                "/user"
                ));
    }

    @Test
    public void simpleCheck() throws Exception {
        for (String url : baseUrls){
            this.mockMvc.perform(get(url)).andExpect(status().isOk());
        }
    }
}