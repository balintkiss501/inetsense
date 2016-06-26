package hu.elte.inetsense.server.collector.util;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by balintkiss on 5/1/16.
 */
public class JsonValidationException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private List<String> errors;
	
    public JsonValidationException(List<String> errors) {
    	this.errors = errors;
    }
    
    @Override
    public String getMessage() {
    	return errors.stream().collect(Collectors.joining("\n"));
    }
    
    public List<String> getErrors() {
		return Collections.unmodifiableList(errors);
	}
}
