package com.example.baitapnhom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ThongKeActivity extends AppCompatActivity {
    TextView tv;
    ImageView btnBack;
    ArrayList<ThongKe> listThongKe;
    ThongKeAdapter thongKeAdapter;
    ListView lv;
    DataBaseGame db;
    ArrayList<Player> listPlayer;
    private MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);
        btnBack = findViewById(R.id.btnBack);
        tv = findViewById(R.id.tvDatLaiThongKe);
        lv = findViewById(R.id.lvThongKe);
        player = MediaPlayer.create(ThongKeActivity.this, R.raw.amthanh);
        player.setLooping(true);
        player.start();
        SharedPreferences presound = getSharedPreferences("sound", MODE_PRIVATE);
        float VolumnNhacNen = presound.getFloat("nhacNen",1.f);
        player.setVolume(VolumnNhacNen,VolumnNhacNen);
        db = new DataBaseGame();
        /////// thống kê
        ThongKe();
        // sự kiện
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThongKeActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteAllPlayer();
                ThongKe();
            }
        });
    }
    @Override
    protected void onStop() {
        super.onStop();
        player.stop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        player.stop();
    }

    public void ThongKe(){
        listPlayer = new ArrayList<>();
        listPlayer = db.getAllPlayer();
        int count  = 0;
        double tongDiem = 0;
        double diemTB = 0;
        double tiLeThang = 0;
        int diemMax = 0;
        if(listPlayer.size()!=0){
            for(int i = 0;i<listPlayer.size();i++){
                if(listPlayer.get(i).getDiem()==100)
                    count++;
                tongDiem+=listPlayer.get(i).getDiem();
            }
            diemMax = listPlayer.get(0).getDiem();
            diemTB = tongDiem/(double) listPlayer.size();
            tiLeThang = (double) count/(double) listPlayer.size()*100;

        }
        ThongKe tBatDau = new ThongKe("Trò chơi đã bắt đầu",listPlayer.size()+"");
        ThongKe tWin = new ThongKe("Trò chơi đã thắng",count+"");
        ThongKe tTiLe = new ThongKe("Tỉ lệ thắng",String.format("%.1f",tiLeThang)+"%");
        ThongKe tDiemMax = new ThongKe("Điểm cao nhất",diemMax+"");
        ThongKe tDiemAvg = new ThongKe("Điểm trung bình",String.format("%.2f",diemTB));
        listThongKe = new ArrayList<>();
        listThongKe.add(tBatDau);
        listThongKe.add(tWin);
        listThongKe.add(tTiLe);
        listThongKe.add(tDiemMax);
        listThongKe.add(tDiemAvg);
        thongKeAdapter = new ThongKeAdapter(ThongKeActivity.this,R.layout.layout_thongke_listview,listThongKe);
        lv.setAdapter(thongKeAdapter);
    }
}