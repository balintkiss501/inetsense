package hu.elte.inetsense.server.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.elte.inetsense.common.dtos.MeasurementDTO;
import hu.elte.inetsense.server.web.service.MeasurementService;

/**
 * @author Zsolt Istvanfi
 */
@RestController
@Transactional(readOnly = true)
public class MeasurementController {

    @Autowired
    private MeasurementService measurementService;

    @RequestMapping("/measurements")
    public List<MeasurementDTO> measurements() {
        return measurementService.getAllMeasurements();
    }

}
