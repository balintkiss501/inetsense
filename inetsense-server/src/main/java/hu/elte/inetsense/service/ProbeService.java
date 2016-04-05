/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.inetsense.service;

import hu.elte.inetsense.domain.ProbeRepository;
import hu.elte.inetsense.domain.entities.Probe;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Bekfi Richárd
 */
@Service
public class ProbeService {
    
    @Autowired
    ProbeRepository repo;
    
    private final SecureRandom random = new SecureRandom();
  
    public Probe addProbe(){
        Probe probe = new Probe();
        probe.setCreatedOn(Date.from(Instant.now()));
        probe.setAuthId(nextAuthId());
        probe = repo.save(probe);
        
        return probe;
    }
    
    /**
     * Generate random authentication id for new probes
     * 
     * @return random 8 character long alphanumeric String
     */
    private String nextAuthId() {
            BigInteger next = BigInteger.ZERO;

            
            //skip short numbers
            while(next.bitLength() < 36){
                next = new BigInteger(40, random);
            }
           
            return next.toString(32);
        }
    
}
