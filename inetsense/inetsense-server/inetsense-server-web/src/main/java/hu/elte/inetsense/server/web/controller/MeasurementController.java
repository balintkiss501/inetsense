package hu.elte.inetsense.server.web.controller;

import java.util.List;

import hu.elte.inetsense.common.dtos.MeasurementDTO;
import hu.elte.inetsense.common.dtos.ProbeDTO;
import hu.elte.inetsense.server.web.service.MeasurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by riboczki on 2016. 11. 05.
 */
@RestController
@RequestMapping("/measurements")
public class MeasurementController {

    @Autowired
    private MeasurementService measurementService;

    @RequestMapping(method = RequestMethod.GET)
    public List<MeasurementDTO> getAllMeasurementsForProbe(
            @RequestParam(value = "authId") final String probeAuthId) {
        return measurementService.getMeasurementsDataByProbeAuthId(probeAuthId);
    }
}