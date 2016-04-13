/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.inetsense;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hu.elte.inetsense.domain.entities.Probe;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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

/**
 *
 * @author Bekfi Richárd
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = App.class)
@WebAppConfiguration
public class ProbeAdministrationTest {

    private List<Probe> probes;

    private MockMvc mockMvc;
    private ObjectMapper mapper;

    
    final int probecount = 2;
    
    @Resource
    private WebApplicationContext context;

    @Before
    public void init() {
       

        probes = new LinkedList<>();
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        mapper = new ObjectMapper();

    }

    @Test
    public void testController() throws Exception {
        

        for (int i = 0; i < probecount; ++i) {
            String probejson = mockMvc.perform(MockMvcRequestBuilders.post("/probe")).
                    andExpect(MockMvcResultMatchers.status().isOk()).
                    andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).
                    andDo(MockMvcResultHandlers.print()).
                    andExpect(MockMvcResultMatchers.jsonPath("$.id").exists()).
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
        
        
        //test delete
        Probe p = probes.get(0);
        
        mockMvc.perform(MockMvcRequestBuilders.delete("/probe/"+p.getId())).
                    andExpect(MockMvcResultMatchers.status().isOk());
        
        
        List<Probe> plist2 = listProbes(probecount - 1);
        
        
        assert !plist2.contains(p);
        
        plist2.forEach(probe -> {
            assert probes.contains(probe);
        });
        
        
    }
    
    private List<Probe> listProbes(int expectedsize) throws Exception{
        String s= mockMvc.
                perform(
                        MockMvcRequestBuilders.get("/probe")).
                andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(expectedsize))).
                andReturn().getResponse().getContentAsString();
        
        return mapper.readValue(s, new TypeReference<List<Probe>>() {
        });
    }

}
