package com.example.baitapnhom;

public class ThongKe {
    private String title;
    private String thongKe;

    public ThongKe(String title, String thongKe) {
        this.title = title;
        this.thongKe = thongKe;
    }

    public ThongKe() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThongKe() {
        return thongKe;
    }

    public void setThongKe(String thongKe) {
        this.thongKe = thongKe;
    }
}
