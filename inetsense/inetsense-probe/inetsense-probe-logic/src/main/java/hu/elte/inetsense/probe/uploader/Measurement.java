/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.inetsense.probe.uploader;

import java.text.SimpleDateFormat;

/**
 *
 * @author Adam
 */
public class Measurement {
    
    private long timestamp;
    private int download_bps;
    private int upload_bps;

    public Measurement(long timestamp, int download_bps, int upload_bps) {
        this.timestamp = timestamp;
        this.download_bps = download_bps;
        this.upload_bps = upload_bps;
    }

    public int getDownload() {
        return download_bps;
    }

    public void setDownload(int download_bps) {
        this.download_bps = download_bps;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getUpload() {
        return upload_bps;
    }

    public void setUpload(int upload_bps) {
        this.upload_bps = upload_bps;
    }
    
    public String toJSON() {
        return "{\"completedOn\":\""+getTimeString()+"\",\"downloadSpeed\":"+download_bps+",\"uploadSpeed\":"+upload_bps+"}";
    }
    
    private String getTimeString() {
        return (
            new SimpleDateFormat("yyyy-MM-dd").format(timestamp)
            + "T" +
            new SimpleDateFormat("HH:mm:ss").format(timestamp)
            + "Z"
        );
    }
    
}
