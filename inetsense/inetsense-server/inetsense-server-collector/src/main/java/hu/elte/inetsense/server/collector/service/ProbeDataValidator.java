package hu.elte.inetsense.server.collector.service;

import hu.elte.inetsense.common.dtos.ProbeDataDTO;
import hu.elte.inetsense.server.collector.util.JsonValidationException;

public interface ProbeDataValidator {

    /**
     * Validate incoming JSON message object.
     *
     * @param message
     * @return
     */
    void validate(ProbeDataDTO probeDataDTO) throws JsonValidationException;

}