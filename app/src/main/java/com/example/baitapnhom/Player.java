package com.example.baitapnhom;

public class Player {
    private String name;
    private int diem;

    public Player( String name, int diem) {
        this.name = name;
        this.diem = diem;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDiem() {
        return diem;
    }

    public void setDiem(int diem) {
        this.diem = diem;
    }
}
