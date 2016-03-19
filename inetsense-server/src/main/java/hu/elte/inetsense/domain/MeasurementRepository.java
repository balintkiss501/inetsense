package hu.elte.inetsense.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hu.elte.inetsense.domain.entities.Measurement;

/**
 * @author Zsolt Istvanfi
 */
@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Long> {

}
