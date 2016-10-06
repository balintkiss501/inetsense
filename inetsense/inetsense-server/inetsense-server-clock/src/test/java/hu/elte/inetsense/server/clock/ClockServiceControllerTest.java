package hu.elte.inetsense.server.clock;

import hu.elte.inetsense.server.clock.controller.ClockServiceController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * 
 * @author balintkiss
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(App.class)
@WebAppConfiguration
public class ClockServiceControllerTest {

    private static final long DELTA = 1000;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ClockServiceController clockServiceController;

    @Before
    public void init() {
        mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void queryTime() throws Exception {
        long expected = new Date().getTime();
        MvcResult result = mockMvc.perform(get("/time"))
                .andExpect(status().isOk())
                .andReturn();
        long clockResponse = Long.parseLong(result.getResponse().getContentAsString());
        assertEquals(expected, clockResponse, DELTA);
    }

}
