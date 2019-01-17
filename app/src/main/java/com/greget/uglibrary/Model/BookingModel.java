package com.greget.uglibrary.Model;

public class BookingModel {
    private String loker;
    private String nama;
    private String npm;
    private String waktu;


    public BookingModel() {
    }

    public BookingModel(String loker, String nama, String npm, String waktu) {
        this.loker = loker;
        this.nama = nama;
        this.npm = npm;
        this.waktu = waktu;
    }

    public String getLoker() {
        return loker;
    }

    public void setLoker(String loker) {
        this.loker = loker;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNpm() {
        return npm;
    }

    public void setNpm(String npm) {
        this.npm = npm;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }
}
