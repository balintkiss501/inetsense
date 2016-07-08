package hu.elte.inetsense.common.util;

import java.io.IOException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class HTTPUtil {

    public static CloseableHttpResponse get(String url) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(url);
        try {
            CloseableHttpResponse response = httpclient.execute(httpget);
            return response;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static CloseableHttpResponse post(String url, String json) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(url);
        try {
            StringEntity entity = new StringEntity(json);
            entity.setContentType(ContentType.APPLICATION_JSON.toString());
            httppost.setEntity(entity);
            CloseableHttpResponse response = httpclient.execute(httppost);
            return response;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
