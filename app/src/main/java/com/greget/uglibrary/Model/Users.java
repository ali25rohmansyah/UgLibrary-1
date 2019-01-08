package com.greget.uglibrary.Model;

public class Users {
    private String jkel;
    private String nama;
    private String pass;
    private String npm;

    public Users() {
    }

    public Users(String jkel, String nama, String pass) {
        this.jkel = jkel;
        this.nama = nama;
        this.pass = pass;
    }

    public String getNpm() {
        return npm;
    }

    public void setNpm(String npm) {
        this.npm = npm;
    }

    public String getJkel() {
        return jkel;
    }

    public void setJkel(String jkel) {
        this.jkel = jkel;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}

