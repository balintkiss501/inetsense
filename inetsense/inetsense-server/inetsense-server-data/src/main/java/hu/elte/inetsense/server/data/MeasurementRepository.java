package hu.elte.inetsense.server.data;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hu.elte.inetsense.server.data.entities.Measurement;
import hu.elte.inetsense.server.data.entities.Probe;

/**
 * @author Zsolt Istvanfi
 */
@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Long> {

    List<Measurement> findAllByProbeId(long probeId);

    List<Measurement> findAllByProbeOrderByIdAsc(Probe probe);

    List<Measurement> findAllByProbeAuthIdAndCompletedOnBetweenOrderByCompletedOnAsc(String probeAuthId, Date from, Date to);

    List<Measurement> findAllByProbeAuthIdOrderByCompletedOnAsc(String probeAuthId);

}
