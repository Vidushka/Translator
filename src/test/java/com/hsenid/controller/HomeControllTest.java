package com.hsenid.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Created by Vidushka on 03/05/17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("classpath:mvc-dispatcher-servlet.xml")
public class HomeControllTest {

    @Autowired
    private WebApplicationContext webContext;

    private MockMvc mockMvc;

    @Test
    public void testWelcomePage() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webContext).build();
        this.mockMvc.perform(get("/home")
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(view().name("welcome"));
    }

    @Test
    public void loginTest() throws Exception {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.webContext).build();
        this.mockMvc.perform(get("/login").
                accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(view().name("admin"));
    }
    }


