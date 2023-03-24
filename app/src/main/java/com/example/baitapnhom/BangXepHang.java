package com.example.baitapnhom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class BangXepHang extends AppCompatActivity {
    ImageView imgBack,imgHinh1, imgHinh2;
    ListView lv;
    DataBaseGame db;
    ArrayList<Player> listPlayer;
    BangXepHangAdapter adapter;
    private MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bang_xep_hang);
        anhXa();
        player = MediaPlayer.create(BangXepHang.this, R.raw.amthanh);
        player.setLooping(true);
        player.start();
        SharedPreferences pre = getSharedPreferences("sound", MODE_PRIVATE);
        float VolumnNhacNen = pre.getFloat("nhacNen",1.f);
        player.setVolume(VolumnNhacNen,VolumnNhacNen);
        db = new DataBaseGame();
        loadDuLieu();
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BangXepHang.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }
    @Override
    protected void onPause() {
        super.onPause();
        player.stop();
    }
    public void anhXa(){
        lv = findViewById(R.id.lvRank);
        imgBack = findViewById(R.id.imgBack);
        imgHinh1 = findViewById(R.id.imgHinh1);
        imgHinh2 = findViewById(R.id.imgHinh2);
        imgHinh2.setImageResource(R.drawable.crong);
        imgHinh2.setScaleType(ImageView.ScaleType.FIT_XY);
        imgHinh1.setImageResource(R.drawable.correct);
        imgBack.setImageResource(R.drawable.arrows);
    }
    public void loadDuLieu(){
        listPlayer = new ArrayList<>();
        listPlayer = db.getAllPlayer();
        adapter = new BangXepHangAdapter(BangXepHang.this,R.layout.layout_bangxephang_listview,listPlayer);
        lv.setAdapter(adapter);
    }
}