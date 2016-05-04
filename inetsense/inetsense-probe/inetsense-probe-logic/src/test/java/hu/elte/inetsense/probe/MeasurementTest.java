
package hu.elte.inetsense.probe;

import hu.elte.inetsense.probe.uploader.Measurement;
import org.junit.Test;
import static org.junit.Assert.*;

public class MeasurementTest {
    
    @Test
    public void testJSON() {
        
        Measurement me = new Measurement(
            0,
            200,
            300
        );
        System.out.println(me.toJSON());
        assertEquals(me.toJSON(), "{\"completedOn\":\"1970-01-01T01:00:00Z\",\"downloadSpeed\":200,\"uploadSpeed\":300}");
        
    }
    
}
