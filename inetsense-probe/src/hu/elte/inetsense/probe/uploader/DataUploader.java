package hu.elte.inetsense.probe.uploader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;

import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

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
    
    //
    
    /* Check server availability: */
    private void flush() {
        
        /* todo: replace with a JSON lib */
        String data = "{\"probe_id\":\""+probe_id+"\", \"measurements\":[";
        
        data += measurements.get(0).toJSON();
        
        for(int i=1; i<measurements.size(); i++) {
            data += ", "+measurements.get(i).toJSON();
        }
        
        data += "]}";
        
        System.out.println(data);
        
        try {
            HttpClient httpclient = HttpClients.createDefault();
            HttpPost httppost = new HttpPost(this.server_location);
            
            
            List<NameValuePair> params = new ArrayList(2);
            params.add(new BasicNameValuePair("data", data));
            httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

            
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                InputStream instream = entity.getContent();
                try {
                    //
                } finally {
                    instream.close();
                }
            }
            
            this.measurements.clear();
        
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
        }
        
    }
    
}
