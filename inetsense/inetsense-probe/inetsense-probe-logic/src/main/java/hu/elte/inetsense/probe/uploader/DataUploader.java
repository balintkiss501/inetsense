package hu.elte.inetsense.probe.uploader;

import java.util.ArrayList;
import java.util.List;

import java.io.*;
import java.net.*;

/**
 *
 * @author Adam Kecskes
 */
public class DataUploader{
    
    private String probe_id;
    private String server_location;
    private List<Measurement> measurements;

    public DataUploader(String server_location, String probe_id) {
        this.probe_id = probe_id;
        this.server_location = "http://"+server_location;
        this.measurements = new ArrayList();
    }
    
    public void addMeasurement(Measurement me) {
        
        this.measurements.add(me);
        System.out.println(me.getDownload());
        
        this.flush();
        
    }
    
    /* Todo: Check server availability */
    private void flush() {
        
        /* todo: replace with a JSON lib */
        String data = "{\"probeAuthId\":\""+probe_id+"\", \"measurements\":[";
        
        data += measurements.get(0).toJSON();
        
        for(int i=1; i<measurements.size(); i++) {
            data += ", "+measurements.get(i).toJSON();
        }
        
        data += "]}";
        
        System.out.println(data);
        
        try {
            URL url = new URL(server_location);
            
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setRequestMethod("POST");
            
            OutputStreamWriter wr= new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.write('\n');
            
            wr.flush();
            wr.close();
            
            System.out.println("DATA sent");
            
            this.measurements.clear();
            
            conn.disconnect();
        
        } catch(Exception ex) {
            // print error, try again later
            System.out.println(ex.getMessage());
        }
        
    }
    
}
