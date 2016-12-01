package hu.elte.inetsense.server.collector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Entry point.
 *
 * @author Zsolt Istvanfi
 */
@SpringBootApplication
@EnableJpaRepositories("hu.elte.inetsense.server.data")
@EntityScan("hu.elte.inetsense.server.data.entities")
public class App {

    public static void main(final String[] args) {
        SpringApplication.run(App.class, args);
    }

}
