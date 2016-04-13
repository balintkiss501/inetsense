package hu.elte.inetsense.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hu.elte.inetsense.domain.entities.Measurement;
import hu.elte.inetsense.domain.entities.Probe;

/**
 * @author Zsolt Istvanfi
 */
@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Long> {

    List<Measurement> findAllByProbe(Probe probe);

    List<Measurement> findAllByProbeOrderByIdAsc(Probe probe);

}
