package hu.elte.inetsense.probe.uploader;

import hu.elte.inetsense.probe.InetsenseProbe;
import java.util.ArrayList;
import java.util.List;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

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
        
        this.flush();
        
    }
    
    /* Todo: Check server availability */
    private void flush() {
        
        /* todo: replace with a JSON lib */
        String data = "{\"probeAuthId\":\""+probe_id+"\",\"measurements\":[";
        
        data += measurements.get(0).toJSON();
        
        for(int i=1; i<measurements.size(); i++) {
            data += ","+measurements.get(i).toJSON();
        }
        
        data += "]}";
        
        try {
            URL url = new URL(server_location);
            
            byte[] postData       = data.getBytes( StandardCharsets.UTF_8 );
            int    postDataLength = postData.length;
            
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();        
            conn.setDoOutput( true );
            conn.setInstanceFollowRedirects( false );
            conn.setRequestMethod( "POST" );
            conn.setRequestProperty( "Content-Type", "text/json"); 
            conn.setRequestProperty( "Charset", "utf-8");
            conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
            
            DataOutputStream wr= new DataOutputStream(conn.getOutputStream());
            wr.write(postData);
            wr.write('\n');
            
            wr.flush();
            wr.close();
            
            InetsenseProbe.log.info("DATA sent! Response code: "+conn.getResponseCode());
            
            this.measurements.clear();
            
            conn.disconnect();
        
        } catch(Exception ex) {
            // print error, try again later
            InetsenseProbe.log.error(ex.getMessage());
        }
        
    }
    
}
