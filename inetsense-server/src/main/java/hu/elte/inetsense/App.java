package hu.elte.inetsense;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

/**
 * Entry point.
 *
 * @author Zsolt Istvanfi
 */
@SpringBootApplication
public class App {

    public static void main(final String[] args) {
    	
    	ObjectMapper om = new ObjectMapper();
    	om.setNodeFactory(JsonNodeFactory.withExactBigDecimals(true));
    	
        SpringApplication.run(App.class, args);
    }

}
