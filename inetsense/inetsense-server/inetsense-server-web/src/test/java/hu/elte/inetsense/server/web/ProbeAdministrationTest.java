/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.inetsense.server.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hu.elte.inetsense.server.data.entities.Probe;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Bekfi Richárd
 */
// FIXME: Application context errors, needs more modular tests, needs to be moved into another file
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ProbeAdministrationTest.TestConfig.class)
@WebAppConfiguration
public class ProbeAdministrationTest {

    @Configuration
    @EnableAutoConfiguration
    @EnableJpaRepositories("hu.elte.inetsense.server.data")
    @EntityScan("hu.elte.inetsense.server.data.entities")
    @ComponentScan({"hu.elte.inetsense.server.web.service", "hu.elte.inetsense.server.web.controller"})
    public static class TestConfig {
    }
    
    private List<Probe> probes;

    private MockMvc mockMvc;
    private ObjectMapper mapper;

    
    final int probecount = 10;
    
    @Resource
    private WebApplicationContext context;

    @Before
    public void init() {
       

        probes = new LinkedList<>();
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        mapper = new ObjectMapper();

    }

    @Ignore
    @Test
    public void testController() throws Exception {
        

        for (int i = 0; i < probecount; ++i) {
            String probejson = mockMvc.perform(MockMvcRequestBuilders.post("/probes")).
                    andExpect(MockMvcResultMatchers.status().isOk()).
                    andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).
                    andDo(MockMvcResultHandlers.print()).
                    andExpect(MockMvcResultMatchers.jsonPath("$.authId").exists()).
                    andExpect(MockMvcResultMatchers.jsonPath("$.createdOn").exists()).
                    andReturn().getResponse().getContentAsString();

            probes.add(mapper.readValue(probejson, Probe.class));
        }

        

        List<Probe> plist = listProbes(probecount);

        assert plist.size() == probes.size();

        plist.forEach((probe) -> {
            assert probes.contains(probe);
            assert probe.getAuthId().length() == 8;
        });

        
        //check auth code uniqueness
        List<String> authcodes = plist.stream().map(Probe::getAuthId).collect(Collectors.toList());

        while (!authcodes.isEmpty()) {
            String code = authcodes.remove(0);
            assert !authcodes.contains(code);
        }
        
        
        
        
    }
    
    private List<Probe> listProbes(int expectedsize) throws Exception{
        String s= mockMvc.
                perform(
                        MockMvcRequestBuilders.get("/probes")).
                andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(expectedsize))).
                andReturn().getResponse().getContentAsString();
        
        return mapper.readValue(s, new TypeReference<List<Probe>>() {
        });
    }

}
