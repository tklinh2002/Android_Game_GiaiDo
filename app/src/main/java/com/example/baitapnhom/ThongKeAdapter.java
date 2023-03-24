package com.example.baitapnhom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ThongKeAdapter extends BaseAdapter {
    Context context;
    int layout;
    ArrayList<ThongKe> listThongKe;

    public ThongKeAdapter(Context context, int layout, ArrayList<ThongKe> listThongKe) {
        this.context = context;
        this.layout = layout;
        this.listThongKe = listThongKe;
    }

    @Override
    public int getCount() {
        return listThongKe.size();
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
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.layout_thongke_listview,null);
        TextView tvTitle = convertView.findViewById(R.id.tvThongKeTitle);
        tvTitle.setText(listThongKe.get(position).getTitle());
        TextView tvThongKe = convertView.findViewById(R.id.tvThongKe);
        tvThongKe.setText(listThongKe.get(position).getThongKe());
        return convertView;
    }
}
