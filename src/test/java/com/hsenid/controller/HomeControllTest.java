package com.hsenid.controller;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;


/**
 * Created by Vidushka on 03/05/17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration()
public class HomeControllTest {

   /* @Autowired
    private WebApplicationContext webContext;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webContext).build();
    }

    @Test
    public void testWelcomePage(){
        try {
            mockMvc.perform(get("/home").accept(MediaType.TEXT_PLAIN))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.TEXT_PLAIN))
                    .andExpect(content().string("Hello World!"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Configuration
    public static class TestConfiguration {

    }*/
}
