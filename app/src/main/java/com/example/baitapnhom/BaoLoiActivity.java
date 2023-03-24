package com.example.baitapnhom;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class BaoLoiActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView troVe;
    EditText edtBaoCao;
    Button btnBaoCao;
    private MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bao_loi);

        troVe = findViewById(R.id.imgBaoCaoTroVe);
        edtBaoCao = findViewById(R.id.edtBaoCao);
        btnBaoCao = findViewById(R.id.btnBaoCao);

        btnBaoCao.setOnClickListener(this);
        player = MediaPlayer.create(BaoLoiActivity.this, R.raw.amthanh);
        player.setLooping(true);
        player.start();
        SharedPreferences pre = getSharedPreferences("sound", MODE_PRIVATE);
        float volumnAmThanh = pre.getFloat("amThanh",1.f);
        player.setVolume(volumnAmThanh,volumnAmThanh);
        troVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2 = new Intent(BaoLoiActivity.this, MainActivity.class);
                startActivity(i2);
            }
        });
    }
    @Override
    protected void onPause() {
        super.onPause();
        player.stop();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBaoCao:
                String textSave = edtBaoCao.getText().toString();

                Uri uri = Uri.parse("mailto:ktpmlinh2002@gmail.com");
                Intent intent = new Intent(Intent.ACTION_SENDTO,uri);
                intent.putExtra(Intent.EXTRA_SUBJECT,"Thư góp ý");
                intent.putExtra(Intent.EXTRA_TEXT,textSave);
                startActivity(intent);
                writeData(textSave);
                break;

        }
    }

    private void writeData(String textSave) {
        try {
            FileOutputStream fileOutputStream = openFileOutput("testData.txt",MODE_APPEND);
            fileOutputStream.write(textSave.getBytes());
            fileOutputStream.close();
            Toast.makeText(this, "Góp ý của bạn đã được ghi nhận", Toast.LENGTH_SHORT).show();

            edtBaoCao.setText("");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
