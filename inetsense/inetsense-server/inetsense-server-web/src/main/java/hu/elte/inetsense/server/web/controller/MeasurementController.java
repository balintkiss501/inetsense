package hu.elte.inetsense.server.web.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
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

    /**
     *
     * @author zsolt.turo@gmail.com
     *
     * @param probeId
     *            8-length alphanumeric String
     * @param dateFrom
     *            timestamp, eg. 1456790400000
     * @param dateTo
     *            timestamp, eg. 1461494069283
     *
     * @return
     */
    @CrossOrigin(origins = "http://localhost:1841")
    @RequestMapping("/measurements/{probeId}/from/{dateFrom}/to/{dateTo}/{resolution}")
    public List<List<List<BigDecimal>>> getAllMeasurementsOfProbeIdByTimeWindow(
            @PathVariable("probeId") final String probeId,
            @PathVariable("dateFrom") final String dateFrom,
            @PathVariable("dateTo") final String dateTo,
            @PathVariable("resolution") Integer resolution
            ) {

        List<List<List<BigDecimal>>> result = new ArrayList<List<List<BigDecimal>>>();

        // REVIEW check parameter validity

        // dateFrom = "1456790400000";
        // dateTo = "1461494069283";

        long start = Long.parseLong(dateFrom);
        long end = Long.parseLong(dateTo);

        Date fromDate = new Date(start);
        Date toDate = new Date(end);

        List<MeasurementDTO> measurements = measurementService.getMeasurementsByProbeAuthIdBetweenDates(probeId,
                fromDate, toDate);

        if (measurements.isEmpty()) {
            return result;
        }

        Iterator<MeasurementDTO> iterator = measurements.iterator();
        MeasurementDTO nextMeasurement = iterator.next();

        long diff = end - start;

        // this should be sent by the client @zsoltistvanfi
        // final int resolution = 600;

        long step = diff / resolution;
        if (step == 0) {
            step = 1;
        }

        // REVIEW minimum resolution
        // REVIEW measurement ranges, measurements base as parameter

        List<List<BigDecimal>> uploads = new ArrayList<List<BigDecimal>>();
        List<List<BigDecimal>> downloads = new ArrayList<List<BigDecimal>>();
        for (int i = 0; i < resolution; i++) {
            final long stepEndTime = start + step * (i + 1);
            Date stepEndDate = new Date(stepEndTime);

//            if (!nextMeasurement.getCompletedOn().before(stepEndDate)) {
//                continue;
//            }

            List<MeasurementDTO> stepMeasurements = new ArrayList<>();
            stepMeasurements.add(nextMeasurement);

            while (iterator.hasNext()) {
                MeasurementDTO dto = iterator.next();
                if (dto.getCompletedOn().before(stepEndDate)) {
                    stepMeasurements.add(dto);
                } else {
                    nextMeasurement = dto;
                    break;
                }
            }

            long averageUpload = (long) stepMeasurements.stream().mapToLong(x -> x.getUploadSpeed()).average()
                    .orElse(0);
            long averageDownload = (long) stepMeasurements.stream().mapToLong(x -> x.getDownloadSpeed()).average()
                    .orElse(0);

            BigDecimal stepStartTime = BigDecimal.valueOf(start + step * i);
            
            uploads.add(Arrays.asList(stepStartTime.setScale(0, RoundingMode.DOWN), BigDecimal.valueOf(averageUpload)));
            downloads.add(Arrays.asList(stepStartTime.setScale(0, RoundingMode.DOWN), BigDecimal.valueOf(averageDownload)));

            if (!iterator.hasNext()) {
                break;
            }
        }
        
        result.add(downloads);
        result.add(uploads);

        return result;
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
	@RequestMapping("/measurements/demo/{probeId}/from/{dateFrom}/to/{dateTo}/{resolution}")
	public List<List<List<BigDecimal>>> getDemoData(
			@PathVariable("probeId") String probeId,
			@PathVariable("dateFrom") String dateFrom,
			@PathVariable("dateTo") String dateTo,
			@PathVariable("resolution") Integer resolution
			) {
		
		List<List<List<BigDecimal>>> result = new ArrayList<List<List<BigDecimal>>>();
		
		// REVIEW check parameter validity
		
//		dateFrom = "1456790400000";
//		dateTo = "1461494069283";
		
		BigDecimal dFrom = new BigDecimal(dateFrom);
		BigDecimal dTo = new BigDecimal(dateTo);
		
		BigDecimal diff = dTo.subtract(dFrom);

		Random random = new Random(probeId.hashCode());
		
//		resolution
//		Integer resolution = 600;
		BigDecimal step = diff.divide(new BigDecimal(resolution));
		
		// REVIEW mini7mum resolution
		// REVIEW measurement ranges, measurements base as parameter
		
		List<List<BigDecimal>> uploads = new ArrayList<List<BigDecimal>>();
		for (int i = 0; i < resolution; i++) {

			BigDecimal measured = new BigDecimal(random.nextInt(30) + random.nextDouble() + 100);
			measured = measured.setScale(2, RoundingMode.CEILING);

			uploads.add(Arrays.asList(dFrom.add(step.multiply(new BigDecimal(i)).setScale(0, RoundingMode.DOWN)), measured));
		}
		
		
		List<List<BigDecimal>> downloads = new ArrayList<List<BigDecimal>>();
		for (int i = 0; i < resolution; i++) {

			BigDecimal measured = new BigDecimal(random.nextInt(30) + random.nextDouble() + 0);
			measured = measured.setScale(2, RoundingMode.CEILING);

			downloads.add(Arrays.asList(dFrom.add(step.multiply(new BigDecimal(i)).setScale(0, RoundingMode.DOWN)), measured));
		}
		
		result.add(downloads);
		result.add(uploads);

		return result;
	}

}
