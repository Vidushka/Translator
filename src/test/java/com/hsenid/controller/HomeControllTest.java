package com.hsenid.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by Vidushka on 03/05/17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("/../../main/webapp/WEB-INF/web.xml")
public class HomeControllTest {

    @Autowired
    private WebApplicationContext webContext;

    private MockMvc mockMvc;

    @Test
    public void testWelcomePage(){
        try {
            mockMvc.perform(get("/home").accept(MediaType.ALL))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.ALL))
                    .andExpect(view().name("/welcome"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
