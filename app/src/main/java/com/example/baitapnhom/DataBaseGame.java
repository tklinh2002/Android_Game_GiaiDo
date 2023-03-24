package com.example.baitapnhom;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class DataBaseGame {
    SQLiteDatabase db;
    String query;
    SQLiteStatement stm = null;
    public DataBaseGame() {
        this.db = SQLiteDatabase.openDatabase("/data/data/com.example.baitapnhom/DBTest.db",null,SQLiteDatabase.CREATE_IF_NECESSARY);
    }
    public void close(){
        db.close();
    }
    public ArrayList<Question> getQuestion(ArrayList listNumber){
        ArrayList<Question> listQuestion = new ArrayList<>();
        for (int i = 0; i < listNumber.size() ; i++){
            query = "SELECT * from cauHoi WHERE id";
            query =query + " = " + listNumber.get(i);
            Cursor cursor = db.rawQuery(query,null);
           while ( cursor.moveToNext()) {
               int id = cursor.getInt(0);
               String cauHoi = cursor.getString(1);
               String cauA = cursor.getString(2);
               String cauB = cursor.getString(3);
               String cauC = cursor.getString(4);
               String cauD = cursor.getString(5);
               String dapAn = cursor.getString(6);
               Question q = new Question(id, cauHoi, cauA, cauB, cauC, cauD, dapAn);
               listQuestion.add(q);
           }
        }
        return listQuestion;
    }
    public boolean addPlayer(Player p){
        query ="INSERT into NguoiChoi(ten,diem) VALUES(?,?) ";
        try {
            stm = db.compileStatement(query);
            stm.bindString(1,p.getName());
            stm.bindLong(2,(long)p.getDiem());
            long rs = stm.executeInsert();
            if (rs==-1){
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    public ArrayList<Player> getAllPlayer(){
        ArrayList<Player> players = new ArrayList<>();
        query = "SELECT * from NguoiChoi ORDER by diem DESC";
        Cursor cursor = db.rawQuery(query,null);
        while ( cursor.moveToNext()) {
            String name = cursor.getString(1);
            int diem = cursor.getInt(2);
            Player p = new Player(name, diem);
            players.add(p);
        }
        return players;
    }
    public boolean deleteAllPlayer(){
        query = "DELETE FROM NguoiChoi";
        stm = db.compileStatement(query);
        long rs = stm.executeUpdateDelete();
        if(rs == 0)
            return false;
        return true;
    }
    public int getSizeQuestion(){
        query = "SELECT count(*) as count from cauHoi";
        Cursor cursor = db.rawQuery(query,null);
        while ( cursor.moveToNext()) {
            return cursor.getInt(0);
        }
        return 0;
    }
}
