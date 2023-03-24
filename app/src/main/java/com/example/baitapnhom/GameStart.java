package com.example.baitapnhom;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class GameStart extends AppCompatActivity implements View.OnClickListener {
    // them nguoi choi, nhạc nhẽo,
    Button btnThoat;
    ImageView imgQuestion, img1;
    TextView tvCauHoi, tvTitle, tvA, tvB, tvC, tvD, tvDiem, tvName;
    ArrayList<Question> listQuestion;
    ArrayList listNumber, listNumber2;
    int diem;
    int index;
    int indexCauHoi;
    DataBaseGame db;
    String namePlayer;
    MediaPlayer mediaPlayer;
    private MediaPlayer player;
    float volumnAmThanh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start2);
        anhxa();
        dangKyLangNghe();
        player = MediaPlayer.create(GameStart.this, R.raw.amthanh);
        player.setLooping(true);
        player.start();
        SharedPreferences pre = getSharedPreferences("sound", MODE_PRIVATE);
        float VolumnNhacNen = pre.getFloat("nhacNen",1.f);
        player.setVolume(VolumnNhacNen,VolumnNhacNen);
        volumnAmThanh = pre.getFloat("amThanh",1.f);
        db = new DataBaseGame();
        // chơi mới
        reShare();
        /// đổ dữ liệu vào câu 1
        loadDuLieu();

    }
    @Override
    protected void onPause() {
        super.onPause();
        player.stop();
    }
    public void dangKyLangNghe() {
        tvA.setOnClickListener(this);
        tvB.setOnClickListener(this);
        tvC.setOnClickListener(this);
        tvD.setOnClickListener(this);
        btnThoat.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tvA) {

            tvA.setBackground(getResources().getDrawable(R.drawable.color_answer_green));
            if (listQuestion.get(index).getDapAn().compareTo("a") == 0) {
                try {
                    dapAnDung();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dapAnSai();
                        tvA.setBackground(getResources().getDrawable(R.drawable.color_answer_red));
                    }
                }, 1000);
            }
        }
        if (v.getId() == R.id.tvB) {
            tvB.setBackground(getResources().getDrawable(R.drawable.color_answer_green));
            if (listQuestion.get(index).getDapAn().compareTo("b") == 0) {
                try {
                    dapAnDung();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dapAnSai();
                        tvB.setBackground(getResources().getDrawable(R.drawable.color_answer_red));
                    }
                }, 1000);

            }
        }
        if (v.getId() == R.id.tvC) {
            tvC.setBackground(getResources().getDrawable(R.drawable.color_answer_green));
            if (listQuestion.get(index).getDapAn().compareTo("c") == 0) {
                try {
                    dapAnDung();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dapAnSai();
                        tvC.setBackground(getResources().getDrawable(R.drawable.color_answer_red));
                    }
                }, 1000);

            }
        }
        if (v.getId() == R.id.tvD) {
            tvD.setBackground(getResources().getDrawable(R.drawable.color_answer_green));
            if (listQuestion.get(index).getDapAn().compareTo("d") == 0) {
                try {
                    dapAnDung();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dapAnSai();
                        tvD.setBackground(getResources().getDrawable(R.drawable.color_answer_red));
                    }
                }, 1000);
            }
        }
        if (v.getId() == R.id.btnThoat) {
            AlertDialog.Builder build = new AlertDialog.Builder(GameStart.this);
            build.setTitle("Xác nhận thoát game !!!!");
            build.setMessage("Bạn có chắc muốn thoát");
            build.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    saveShare();
                    Intent intent = new Intent(GameStart.this, MainActivity.class);
                    startActivity(intent);
                }
            });
            build.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog dialog = build.create();
            dialog.show();
        }
    }

    public void anhxa() {
        btnThoat = findViewById(R.id.btnThoat);
        imgQuestion = findViewById(R.id.imgQuestion);
        imgQuestion.setImageResource(R.drawable.question);
        img1 = findViewById(R.id.img1);
        img1.setImageResource(R.drawable.question1);
        tvCauHoi = findViewById(R.id.tvCauHoi);
        tvTitle = findViewById(R.id.tvTitleCauHoi);
        tvA = findViewById(R.id.tvA);
        tvB = findViewById(R.id.tvB);
        tvC = findViewById(R.id.tvC);
        tvD = findViewById(R.id.tvD);
        tvDiem = findViewById(R.id.tvDiem);
        tvName = findViewById(R.id.tvName);
    }

    public void randomQuestion() {
        index = 0;
        diem = 0;
        indexCauHoi = 0;
        tvDiem.setText(diem + "/100");
        listQuestion = new ArrayList<>();
        listNumber = new ArrayList();
        for (int i = 1; i <= db.getSizeQuestion(); i++) {
            listNumber.add(i);
        }
        listNumber2 = new ArrayList();
        for (int i = 1; i <= 10; i++) {
            Random rand = new Random();
            int ranNum = rand.nextInt((listNumber.size() - 1 - 0) + 1);
            listNumber2.add(listNumber.get(ranNum));
            listNumber.remove(ranNum);
        }
        listQuestion = db.getQuestion(listNumber2);// lấy question
    }

    public void loadDuLieu() {
        Question q = listQuestion.get(index);
        tvTitle.setText("Câu hỏi số " + (indexCauHoi + 1) + "");
        tvCauHoi.setText(q.getCauHoi());
        tvA.setText(q.getCauA());
        tvB.setText(q.getCauB());
        tvC.setText(q.getCauC());
        tvD.setText(q.getCauD());
        tvA.setBackground(getResources().getDrawable(R.drawable.color_answer_blue));
        tvB.setBackground(getResources().getDrawable(R.drawable.color_answer_blue));
        tvC.setBackground(getResources().getDrawable(R.drawable.color_answer_blue));
        tvD.setBackground(getResources().getDrawable(R.drawable.color_answer_blue));
    }

    public void dapAnDung() throws IOException {
        mediaPlayer = MediaPlayer.create(GameStart.this,R.raw.correct);
        mediaPlayer.setVolume(volumnAmThanh,volumnAmThanh);
        mediaPlayer.start();
        listNumber2.remove(0); // xóa id câu hỏi đã trả lời
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                index++;
                indexCauHoi++;
                diem += 10;
                tvDiem.setText(diem + "/100");
                if (diem == 100) {
                    SharedPreferences pre = getSharedPreferences("player", MODE_PRIVATE);
                    SharedPreferences.Editor edit = pre.edit();
                    edit.clear();
                    edit.commit();
                    Player player = new Player(namePlayer, diem);
                    boolean kq = db.addPlayer(player);
                    AlertDialog.Builder builder = new AlertDialog.Builder(GameStart.this);
                    builder.setTitle("Bạn thắng!!!!");
                    builder.setMessage("Chúc mừng bạn hoàn thành với " + diem + " điểm");
                    builder.setPositiveButton("Chơi lại", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            LayoutInflater layoutinflater = LayoutInflater.from(GameStart.this);
                            View view = layoutinflater.inflate(R.layout.alert_dialog_getname_player, null);

                            AlertDialog.Builder builder = new AlertDialog.Builder(GameStart.this);

                            builder.setView(view);

                            final EditText editText = (EditText) view.findViewById(R.id.edGetName);

                            builder.setPositiveButton("OK!!", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    final String name = editText.getText().toString();
                                    if (!name.equals("")) {
                                        randomQuestion();
                                        loadDuLieu();
                                        namePlayer = name;
                                        tvName.setText("Chào " + namePlayer);
                                    }
                                    dialog.cancel();
                                }
                            });
                            builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(GameStart.this, MainActivity.class);
                                    startActivity(intent);
                                    dialog.cancel();
                                }
                            });
                            AlertDialog dialog2 = builder.create();
                            dialog2.show();
                        }
                    });
                    builder.setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            Intent intent = new Intent(GameStart.this, MainActivity.class);
                            startActivity(intent);
                        }
                    });
                    AlertDialog dialog = builder.create();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dialog.show();
                        }
                    }, 1000);
                } else {
                    loadDuLieu();
                }
            }
        }, 1000);
    }

    public void dapAnSai() {
        mediaPlayer = MediaPlayer.create(GameStart.this,R.raw.wrong);
        mediaPlayer.setVolume(volumnAmThanh,volumnAmThanh);
        mediaPlayer.start();
        // Xóa sharePre
        SharedPreferences pre = getSharedPreferences("player", MODE_PRIVATE);
        SharedPreferences.Editor edit = pre.edit();
        edit.clear();
        edit.commit();
        // hiển thị đáp án đúng
        if (listQuestion.get(index).getDapAn().equals("a"))
            tvA.setBackground(getResources().getDrawable(R.drawable.color_answer_green));
        if (listQuestion.get(index).getDapAn().equals("b"))
            tvB.setBackground(getResources().getDrawable(R.drawable.color_answer_green));
        if (listQuestion.get(index).getDapAn().equals("c"))
            tvC.setBackground(getResources().getDrawable(R.drawable.color_answer_green));
        if (listQuestion.get(index).getDapAn().equals("d"))
            tvD.setBackground(getResources().getDrawable(R.drawable.color_answer_green));
        /// tạo thông báo
        Player player = new Player(namePlayer, diem);
        boolean kq = db.addPlayer(player);
        Log.d("lc", "dapAnSai: " + kq);
        AlertDialog.Builder builder = new AlertDialog.Builder(GameStart.this);
        builder.setTitle("Game over");
        builder.setMessage("Bạn đạt được " + diem + " điểm");
        builder.setPositiveButton("Chơi lại", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                LayoutInflater layoutinflater = LayoutInflater.from(GameStart.this);
                View view = layoutinflater.inflate(R.layout.alert_dialog_getname_player, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(GameStart.this);

                builder.setView(view);

                final EditText editText = (EditText) view.findViewById(R.id.edGetName);

                builder.setPositiveButton("OK!!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String name = editText.getText().toString();
                        if (!name.equals("")) {
                            randomQuestion();
                            loadDuLieu();
                            namePlayer = name;
                            tvName.setText("Chào " + namePlayer);
                        }
                        dialog.cancel();
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(GameStart.this, MainActivity.class);
                        startActivity(intent);
                        dialog.cancel();
                    }
                });
                AlertDialog dialog2 = builder.create();
                dialog2.show();
            }
        });
        builder.setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Intent intent = new Intent(GameStart.this, MainActivity.class);
                startActivity(intent);
            }
        });
        AlertDialog dialog = builder.create();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.show();
            }
        }, 1000);
        ///
    }

    public void saveShare() {
        SharedPreferences pre = getSharedPreferences("player", MODE_PRIVATE);
        SharedPreferences.Editor edit = pre.edit();
        edit.putString("name", namePlayer);
        edit.putInt("diem", diem);
        String s = listNumber2.toString();
        s = s.substring(1, s.length() - 1);
        edit.putString("list", s);
        edit.putBoolean("newGame",false);
        edit.commit();
    }

    public void reShare() {
        SharedPreferences pre = getSharedPreferences("player", MODE_PRIVATE);
        boolean newGame = pre.getBoolean("newGame",true);
        if(newGame){
            namePlayer = getIntent().getStringExtra("name");
            randomQuestion();
        }else{
            namePlayer = pre.getString("name", "");
            diem = pre.getInt("diem", 0);
            String s = pre.getString("list", "");
            String s1[] = s.split(", ");
            listNumber2 = new ArrayList();
            for (String string : s1) {
                int num = Integer.parseInt(string);
                listNumber2.add(num);
            }
            tvDiem.setText(diem + "/100");
            index = 0;
            indexCauHoi = 10 - listNumber2.size();
            listQuestion = db.getQuestion(listNumber2);
        }
        tvName.setText("Chào " + namePlayer);
    }


}