package hu.elte.inetsense.server.web.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
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
    
    /**
     * 
     * @author zsolt.turo@gmail.com
     * 
     * @param probeId 0
     * @param dateFrom timestamp 1456790400000
     * @param dateTo timestamp 1461494069283
     * 
     * @return
     */
    @CrossOrigin(origins = "http://localhost:1841")
	@RequestMapping("/measurements/{probeId}/from/{dateFrom}/to/{dateTo}")
	public List<List<List<BigDecimal>>> getAllMeasurementsOfProbeIdByTimeWindow(
			@PathVariable("probeId") Integer probeId,
			@PathVariable("dateFrom") String dateFrom,
			@PathVariable("dateTo") String dateTo) {
		
		List<List<List<BigDecimal>>> result = new ArrayList<List<List<BigDecimal>>>();
		
		// REVIEW check parameter validity
		
//		dateFrom = "1456790400000";
//		dateTo = "1461494069283";
		
		BigDecimal dFrom = new BigDecimal(dateFrom);
		BigDecimal dTo = new BigDecimal(dateTo);
		
		BigDecimal diff = dTo.subtract(dFrom);
		
		Random random = new Random(probeId);
		
		Integer resolution = 600;
		BigDecimal step = diff.divide(new BigDecimal(resolution));
		
		// REVIEW minimum resolution
		// REVIEW measurement ranges, measurements base as parameter
		
		List<List<BigDecimal>> uploads = new ArrayList<List<BigDecimal>>();
		for (int i = 0; i < resolution; i++) {

			BigDecimal measured = new BigDecimal(random.nextInt(30) + random.nextDouble() + 0);
			measured = measured.setScale(2, RoundingMode.CEILING);

			uploads.add(Arrays.asList(dFrom.add(step.multiply(new BigDecimal(i)).setScale(0, RoundingMode.DOWN)), measured));
		}
		
		
		List<List<BigDecimal>> downloads = new ArrayList<List<BigDecimal>>();
		for (int i = 0; i < resolution; i++) {

			BigDecimal measured = new BigDecimal(random.nextInt(30) + random.nextDouble() + 100);
			measured = measured.setScale(2, RoundingMode.CEILING);

			downloads.add(Arrays.asList(dFrom.add(step.multiply(new BigDecimal(i)).setScale(0, RoundingMode.DOWN)), measured));
		}
		
		result.add(uploads);
		result.add(downloads);

		return result;
	}

}
