package hu.elte.inetsense.server.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hu.elte.inetsense.server.data.entities.probe.ProbeConfiguration;

@Repository
public interface ProbeConfigurationRepository extends JpaRepository<ProbeConfiguration, Long> {

	@Query("SELECT c FROM ProbeConfiguration c "
		 + "WHERE c.configurationProfile = (SELECT p.configurationProfile FROM Probe p WHERE p.authId = ?#{[0]})")
	List<ProbeConfiguration> findConfigurationsByProbeAuthId(String probeAuthId);
}
