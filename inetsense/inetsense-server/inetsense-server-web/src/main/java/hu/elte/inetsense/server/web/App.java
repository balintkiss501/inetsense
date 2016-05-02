package hu.elte.inetsense.server.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

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
    	
    	// Display all the digits of BigDecimals in JSON, without any shortening
    	ObjectMapper om = new ObjectMapper();
    	om.setNodeFactory(JsonNodeFactory.withExactBigDecimals(true));
    	
        SpringApplication.run(App.class, args);
    }

}
