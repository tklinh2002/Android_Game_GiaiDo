package com.example.baitapnhom;

public class Question {
    private int id;
    private String cauHoi;
    private String cauA;
    private String cauB;
    private String cauC;
    private String cauD;
    private String dapAn;

    public Question() {
    }

    public Question(int id, String cauHoi, String cauA, String cauB, String cauC, String cauD, String dapAn) {
        this.id = id;
        this.cauHoi = cauHoi;
        this.cauA = cauA;
        this.cauB = cauB;
        this.cauC = cauC;
        this.cauD = cauD;
        this.dapAn = dapAn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCauHoi() {
        return cauHoi;
    }

    public void setCauHoi(String cauHoi) {
        this.cauHoi = cauHoi;
    }

    public String getCauA() {
        return cauA;
    }

    public void setCauA(String cauA) {
        this.cauA = cauA;
    }

    public String getCauB() {
        return cauB;
    }

    public void setCauB(String cauB) {
        this.cauB = cauB;
    }

    public String getCauC() {
        return cauC;
    }

    public void setCauC(String cauC) {
        this.cauC = cauC;
    }

    public String getCauD() {
        return cauD;
    }

    public void setCauD(String cauD) {
        this.cauD = cauD;
    }

    public String getDapAn() {
        return dapAn;
    }

    public void setDapAn(String dapAn) {
        this.dapAn = dapAn;
    }
}
