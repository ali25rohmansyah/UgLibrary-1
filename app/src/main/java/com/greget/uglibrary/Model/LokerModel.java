package com.greget.uglibrary.Model;

public class LokerModel {
    private String LokerID;
    private String status;

    public LokerModel() {
    }

    public LokerModel(String lokerID, String status) {
        LokerID = lokerID;
        this.status = status;
    }

    public String getLokerID() {
        return LokerID;
    }

    public void setLokerID(String lokerID) {
        LokerID = lokerID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
