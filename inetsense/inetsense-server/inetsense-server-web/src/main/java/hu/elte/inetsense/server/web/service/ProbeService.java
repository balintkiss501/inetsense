/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.inetsense.server.web.service;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.elte.inetsense.common.service.configuration.ConfigurationNames;
import hu.elte.inetsense.server.common.exception.InetsenseServiceException;
import hu.elte.inetsense.server.data.ProbeRepository;
import hu.elte.inetsense.server.data.entities.Probe;
import hu.elte.inetsense.server.data.entities.User;
import hu.elte.inetsense.server.service.configuration.ServerConfigurationProvider;
import hu.elte.inetsense.server.web.util.UserUtils;

/**
 *
 * @author Bekfi Richárd
 */
@Service
public class ProbeService {

    @Autowired
    ProbeRepository                     repo;

    @Autowired
    private ServerConfigurationProvider configProvider;

    private final SecureRandom          random = new SecureRandom();

    @PostConstruct
    private void init() {
        random.setSeed(System.currentTimeMillis());
    }

    public List<Probe> getAllProbes() {
        return repo.findAll();
    }

    public List<Probe> getAllProbesOfCurrentUser() {
        User user = UserUtils.getLoggedInUser();
        return repo.findAllByUserId(user.getId());
    }

    public int getProbeCountLimit() {
        return configProvider.getInt(ConfigurationNames.PROBE_LIMIT_PER_USER);
    }

    public Probe addProbe() throws InetsenseServiceException {
        return addProbe(nextAuthId());
    }

    public Probe addProbe(final String authId) throws InetsenseServiceException {
        User user = UserUtils.getLoggedInUser();

        int probeCountLimit = getProbeCountLimit();
        int currentProbeCountOfUser = repo.countByUserId(user.getId());

        if (currentProbeCountOfUser >= probeCountLimit) {
            throw new InetsenseServiceException("Tried to create a new probe for user with id [" + user.getId()
                    + "] (already existing probes: " + currentProbeCountOfUser + ")");
        }

        Probe probe = new Probe();
        probe.setCreatedOn(Date.from(Instant.now()));
        probe.setAuthId(authId);
        probe.setUser(user);
        probe = repo.save(probe);

        return probe;
    }

    public Probe changeProbe(final String authId, final String newId) {
        /*
         * Probe probe = repo.getProbe(authId);
         *
         * probe.setAuthId(newId);
         * probe = repo.save(probe);
         *
         * return probe;
         */

        Probe probe = new Probe();
        probe.setCreatedOn(Date.from(Instant.now()));
        probe.setAuthId(newId);
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
        String id = "";
        boolean unique = true;

        // skip short numbers, and collisions (there is approx. 1 out of 1000000)
        while (next.bitLength() < 36 || !unique) {
            next = new BigInteger(40, random);
            id = next.toString(32);

            unique = !repo.findOneByAuthId(id).isPresent();
        }

        return id;
    }

}
