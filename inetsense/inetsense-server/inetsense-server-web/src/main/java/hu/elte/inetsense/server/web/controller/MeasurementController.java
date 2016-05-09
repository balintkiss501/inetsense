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
import hu.elte.inetsense.server.web.config.ExtJSCORS;
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
     * @param resolution
     *            maximum size of the return list
     *
     * @return
     */
    @CrossOrigin(origins = ExtJSCORS.EXTJS_LOCAL)
    @RequestMapping("/measurements/{probeId}/from/{dateFrom}/to/{dateTo}/{resolution}")
    public List<List<List<BigDecimal>>> getAllMeasurementsOfProbeIdByTimeWindow(
            @PathVariable("probeId") final String probeId,
            @PathVariable("dateFrom") final String dateFrom,
            @PathVariable("dateTo") final String dateTo,
            @PathVariable("resolution") final Integer resolution) {

        List<List<List<BigDecimal>>> result = new ArrayList<List<List<BigDecimal>>>();

        final long startTime = Long.parseLong(dateFrom);
        final long endTime = Long.parseLong(dateTo);

        final Date fromDate = new Date(startTime);
        final Date toDate = new Date(endTime);
        List<MeasurementDTO> measurements = measurementService.getMeasurementsByProbeAuthIdBetweenDates(probeId, fromDate,
                toDate);

        // returning empty list if there are no measurements in the interval
        if (measurements.isEmpty()) {
            return result;
        }

        Iterator<MeasurementDTO> iterator = measurements.iterator();
        MeasurementDTO nextMeasurement = iterator.next();

        long diff = endTime - startTime;

        long step = diff / resolution;
        if (step == 0) {
            // step must be at least 1
            step = 1;
        }

        List<List<BigDecimal>> uploads = new ArrayList<List<BigDecimal>>();
        List<List<BigDecimal>> downloads = new ArrayList<List<BigDecimal>>();
        for (int i = 0; i < resolution; i++) {
            final long stepEndTime = startTime + step * (i + 1);
            Date stepEndDate = new Date(stepEndTime);

            // if the next Measurement is after the end time of this step, then there are no more measurements in this step
            // and we continue to next step
            if (!nextMeasurement.getCompletedOn().before(stepEndDate)) {
                continue;
            }

            // measurements completed in this step interval
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

            double averageUpload = stepMeasurements.stream().mapToLong(x -> x.getUploadSpeed() < 0 ? 0 : x.getUploadSpeed())
                    .average().orElse(0);
            double averageDownload = stepMeasurements.stream().mapToLong(x -> x.getDownloadSpeed() < 0 ? 0 : x.getDownloadSpeed())
                    .average().orElse(0);

            // FIXME: why is it needed to skip the values bigger than 100M? @zsoltistvanfi
            if (averageUpload > 100 * 1000 * 1000 || averageDownload > 100 * 1000 * 1000) {
                continue;
            }

            // b/s to Mb/s conversion
            BigDecimal avgUpload = BigDecimal.valueOf(averageUpload);
            avgUpload = avgUpload.divide(BigDecimal.valueOf(1000 * 1000)).setScale(2, RoundingMode.HALF_UP);
            BigDecimal avgDownload = new BigDecimal(averageDownload);
            avgDownload = avgDownload.divide(BigDecimal.valueOf(1000 * 1000)).setScale(2, RoundingMode.HALF_UP);

            BigDecimal stepStartTime = BigDecimal.valueOf(startTime + step * i);

            uploads.add(Arrays.asList(stepStartTime, avgUpload));
            downloads.add(Arrays.asList(stepStartTime, avgDownload));

            // maybe not needed but for safety...
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
     * @param probeId
     *            0
     * @param dateFrom
     *            timestamp 1456790400000
     * @param dateTo
     *            timestamp 1461494069283
     *
     * @return
     */
    @CrossOrigin(origins = ExtJSCORS.EXTJS_LOCAL)
    @RequestMapping("/measurements/demo/{probeId}/from/{dateFrom}/to/{dateTo}/{resolution}")
    public List<List<List<BigDecimal>>> getDemoData(
            @PathVariable("probeId") final String probeId,
            @PathVariable("dateFrom") final String dateFrom,
            @PathVariable("dateTo") final String dateTo,
            @PathVariable("resolution") final Integer resolution) {

        List<List<List<BigDecimal>>> result = new ArrayList<List<List<BigDecimal>>>();

        // REVIEW check parameter validity

        // dateFrom = "1456790400000";
        // dateTo = "1461494069283";

        BigDecimal dFrom = new BigDecimal(dateFrom);
        BigDecimal dTo = new BigDecimal(dateTo);

        BigDecimal diff = dTo.subtract(dFrom);

        Random random = new Random(probeId.hashCode());

        // resolution
        // Integer resolution = 600;
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
