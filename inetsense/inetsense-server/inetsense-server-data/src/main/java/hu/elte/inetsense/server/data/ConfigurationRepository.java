package hu.elte.inetsense.server.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hu.elte.inetsense.server.data.entities.Configuration;

@Repository
public interface ConfigurationRepository extends JpaRepository<Configuration, String> {

}
