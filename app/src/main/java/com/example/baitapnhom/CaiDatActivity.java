package com.example.baitapnhom;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class CaiDatActivity extends AppCompatActivity {

    ImageView troVe;
    Button btnDongY;
    MediaPlayer nhacNen;
    private MediaPlayer player;
    int soundAmThanh;
    int soundNhacNen;
    SeekBar sbAmThanh;
    SeekBar sbNhacNen;
    float volumnAmThanh;
    float volumnNhacNen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cai_dat);
        sbAmThanh = findViewById(R.id.sbAmThanh);
        sbNhacNen = findViewById(R.id.sbNhacNen);
        troVe = findViewById(R.id.imgCaiDatTroVe);
        btnDongY = findViewById(R.id.btnDongY);
        //
        player = MediaPlayer.create(CaiDatActivity.this, R.raw.amthanh);
        player.setLooping(true);
        player.start();

        reShare();

        sbAmThanh.setProgress((int)(volumnAmThanh*100));
        sbNhacNen.setProgress((int) (volumnNhacNen*100));
        //
        btnDongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                volumnNhacNen = (float)sbNhacNen.getProgress()/100.f;
                player.setVolume(volumnNhacNen,volumnNhacNen);
                saveShare();
            }
        });

        troVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i3 = new Intent(CaiDatActivity.this, MainActivity.class);
                startActivity(i3);
            }
        });
    }
    @Override
    protected void onPause() {
        super.onPause();
        player.stop();
    }
    public void saveShare() {
        SharedPreferences pre = getSharedPreferences("sound", MODE_PRIVATE);
        SharedPreferences.Editor edit = pre.edit();
        soundAmThanh = sbAmThanh.getProgress();
        soundNhacNen = sbNhacNen.getProgress();
        volumnAmThanh = (float)soundAmThanh/100.f;
        volumnNhacNen = (float)soundNhacNen/100.f;
        edit.putFloat("amThanh", volumnAmThanh);
        edit.putFloat("nhacNen", volumnNhacNen);
        edit.commit();
    }

    public void reShare() {
        SharedPreferences pre = getSharedPreferences("sound", MODE_PRIVATE);
        volumnAmThanh = pre.getFloat("amThanh",1.f);
        volumnNhacNen = pre.getFloat("nhacNen",1.f);
        player.setVolume(volumnNhacNen,volumnNhacNen);
    }
}