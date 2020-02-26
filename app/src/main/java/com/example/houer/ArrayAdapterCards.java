package com.example.houer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ArrayAdapterCards extends ArrayAdapter<Cards> {
    Context context;

    public ArrayAdapterCards(Context context, int resourceId, List<Cards> items) {
        super(context,resourceId,items);
    }
    public View getView(int position, View convertView, ViewGroup parent){
        Cards card_item = getItem(position);
        if (convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.item,parent,false);
        }
        TextView tvNameItem= convertView.findViewById(R.id.tv_item_name);
        ImageView imgItem=convertView.findViewById(R.id.img_item);
        tvNameItem.setText(card_item.getName());
        imgItem.setImageResource(R.mipmap.ic_launcher);
        return convertView;
    }
}
