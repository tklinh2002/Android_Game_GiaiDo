package com.example.baitapnhom;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class GioiThieuActivity extends AppCompatActivity {

    ImageView troVe;
    private MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gioi_thieu);
        troVe = findViewById(R.id.imgGioiThieuTroVe);
        player = MediaPlayer.create(GioiThieuActivity.this, R.raw.amthanh);
        player.setLooping(true);
        player.start();
        SharedPreferences pre = getSharedPreferences("sound", MODE_PRIVATE);
        float VolumnNhacNen = pre.getFloat("nhacNen",1.f);
        player.setVolume(VolumnNhacNen,VolumnNhacNen);
        troVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(GioiThieuActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

    }
    @Override
    protected void onPause() {
        super.onPause();
        player.stop();
    }
}