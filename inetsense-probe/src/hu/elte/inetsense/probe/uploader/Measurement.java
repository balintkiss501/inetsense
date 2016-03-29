/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.inetsense.probe.uploader;

/**
 *
 * @author Adam
 */
public class Measurement {
    
    private long timestamp;
    private int download_bytes;
    private int upload_bytes;

    public Measurement(long timestamp, int download_bytes, int upload_bytes) {
        this.timestamp = timestamp;
        this.download_bytes = download_bytes;
        this.upload_bytes = upload_bytes;
    }

    public int getDownload() {
        return download_bytes;
    }

    public void setDownload(int download_bytes) {
        this.download_bytes = download_bytes;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getUpload() {
        return upload_bytes;
    }

    public void setUpload(int upload_bytes) {
        this.upload_bytes = upload_bytes;
    }
    
    public String toJSON() {
        return "{\"created_on\":"+timestamp+", \"download_speed\":"+download_bytes+", \"upload_speed\":"+upload_bytes+"}";
    }
    
}
