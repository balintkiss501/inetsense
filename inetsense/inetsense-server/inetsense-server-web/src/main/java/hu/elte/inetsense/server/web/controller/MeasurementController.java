package hu.elte.inetsense.server.web.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
    @RequestMapping("/measurements/{probeId}/from/{dateFrom}/to/{dateTo}")
    public List<List<List<BigDecimal>>> getAllMeasurementsOfProbeIdByTimeWindow(
            @PathVariable("probeId") final String probeId,
            @PathVariable("dateFrom") final String dateFrom,
            @PathVariable("dateTo") final String dateTo) {

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
        final int resolution = 600;

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

            if (!nextMeasurement.getCompletedOn().before(stepEndDate)) {
                continue;
            }

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

            uploads.add(Arrays.asList(stepStartTime, BigDecimal.valueOf(averageUpload)));
            downloads.add(Arrays.asList(stepStartTime, BigDecimal.valueOf(averageDownload)));

            if (!iterator.hasNext()) {
                break;
            }
        }

        result.add(uploads);
        result.add(downloads);

        return result;
    }

}
