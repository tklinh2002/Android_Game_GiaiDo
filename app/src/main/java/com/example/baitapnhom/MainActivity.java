package com.example.baitapnhom;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ImageButton imagemenu;
    Button btnTroChoiMoi, btnThongKe, btnBangXepHang, btnTiepTuc;
    MediaPlayer player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhXa();
        dangKyLangNghe();
        // nhạc nhẽo đau đầu
        player = MediaPlayer.create(MainActivity.this, R.raw.amthanh);
        player.setLooping(true);
        player.start();
        SharedPreferences presound = getSharedPreferences("sound", MODE_PRIVATE);
        float VolumnNhacNen = presound.getFloat("nhacNen",1.f);
        player.setVolume(VolumnNhacNen,VolumnNhacNen);
        // kiểm tra còn chơi tiếp ?
        SharedPreferences pre = getSharedPreferences("player", MODE_PRIVATE);
        boolean newGame = pre.getBoolean("newGame",true);
        if(newGame){
            btnTiepTuc.setVisibility(View.GONE);
        }else{
            btnTiepTuc.setVisibility(View.VISIBLE);
        }

    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.d("tag", "onPause: ");
        player.stop();
    }

    private void anhXa() {
        imagemenu = (ImageButton) findViewById(R.id.imagemenu);
        btnBangXepHang = findViewById(R.id.btnBangXepHang);
        btnTroChoiMoi = findViewById(R.id.btnTroChoiMoi);
        btnThongKe = findViewById(R.id.btnThongKe);
        btnTiepTuc = findViewById(R.id.btnTiepTuc);
    }

    private void dangKyLangNghe() {
        imagemenu.setOnClickListener(this);
        btnBangXepHang.setOnClickListener(this);
        btnTroChoiMoi.setOnClickListener(this);
        btnThongKe.setOnClickListener(this);
        btnTiepTuc.setOnClickListener(this);
    }

    private void showmenu() {
        PopupMenu popupMenu = new PopupMenu(this, imagemenu);
        popupMenu.getMenuInflater().inflate(R.menu.menu, popupMenu.getMenu());
        popupMenu.getMenu().getItem(0).setIcon(R.drawable.iconcaidat);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menucaidat:
                        Intent intent = new Intent(MainActivity.this, CaiDatActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.menubaoloi:
                        Intent intent2 = new Intent(MainActivity.this, BaoLoiActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.menugioithieu:
                        Intent intent3 = new Intent(MainActivity.this, GioiThieuActivity.class);
                        startActivity(intent3);
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnTroChoiMoi) {
            LayoutInflater layoutinflater = LayoutInflater.from(this);
            View view = layoutinflater.inflate(R.layout.alert_dialog_getname_player, null);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setView(view);

            final EditText editText = (EditText) view.findViewById(R.id.edGetName);

            builder.setPositiveButton("OK!!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SharedPreferences pre = getSharedPreferences("player", MODE_PRIVATE);
                    SharedPreferences.Editor edit = pre.edit();
                    edit.putBoolean("newGame",true);
                    edit.commit();
                    Intent intent = new Intent(MainActivity.this, GameStart.class);
                    final String name = editText.getText().toString();
                    intent.putExtra("name",name);
                    if(!name.equals("")){
                        startActivity(intent);
                    }
                    dialog.cancel();
                }
            });
            builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        if(v.getId()==R.id.btnTiepTuc){
            SharedPreferences pre = getSharedPreferences("player", MODE_PRIVATE);
            SharedPreferences.Editor edit = pre.edit();
            edit.putBoolean("newGame",false);
            edit.commit();
            Intent intent = new Intent(MainActivity.this,GameStart.class);
            startActivity(intent);
        }
        if (v.getId() == R.id.imagemenu) {
            showmenu();
        }
        if(v.getId()==R.id.btnThongKe){
            Intent intent = new Intent(MainActivity.this, ThongKeActivity.class);
            startActivity(intent);
        }
        if(v.getId()==R.id.btnBangXepHang){
            Intent intent = new Intent(MainActivity.this, BangXepHang.class);
            startActivity(intent);
        }
    }
}