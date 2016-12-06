package hu.elte.inetsense.server.data.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hu.elte.inetsense.server.data.entities.probe.Probe;

/**
 * @author Zsolt Istvanfi
 */
@Repository
public interface ProbeRepository extends JpaRepository<Probe, Long> {

    Optional<Probe> findOneByAuthId(String id);

    List<Probe> findAllByUserId(Long userId);

    int countByUserId(Long userId);

}
