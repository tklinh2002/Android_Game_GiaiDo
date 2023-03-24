package com.example.baitapnhom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class BangXepHangAdapter extends BaseAdapter {
    Context context;
    int layout;
    ArrayList<Player> listPlayer;

    public BangXepHangAdapter(Context context, int layout, ArrayList<Player> listPlayer) {
        this.context = context;
        this.layout = layout;
        this.listPlayer = listPlayer;
    }

    @Override
    public int getCount() {
        return listPlayer.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.layout_bangxephang_listview,null);
        TextView tvRank = convertView.findViewById(R.id.tvrank);
        tvRank.setText((position+1)+"");
        ImageView imgRank = convertView.findViewById(R.id.imgRank);
        if(position==0){
            imgRank.setImageResource(R.drawable.numberone);
        }
        if(position==1){
            imgRank.setImageResource(R.drawable.numbertwo);
        }
        if(position==2){
            imgRank.setImageResource(R.drawable.numberthree);
        }
        TextView tvNamePlayer = convertView.findViewById(R.id.tvNamePlayer);
        tvNamePlayer.setText(listPlayer.get(position).getName());
        TextView tvDiemPlayer = convertView.findViewById(R.id.tvDiemPlayer);
        tvDiemPlayer.setText(listPlayer.get(position).getDiem()+"");
        return convertView;
    }
}
