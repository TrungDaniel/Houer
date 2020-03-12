package com.example.houer.chat;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.houer.R;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatHolder> {
    private List<Chat> chatList;
    private Context context;

    public ChatAdapter(List<Chat> chatList, Context context) {
        this.chatList = chatList;
        this.context = context;
    }

    @NonNull
    @Override
    public ChatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        ChatHolder rcv = new ChatHolder(layoutView);

        return rcv;

    }

    @Override
    public void onBindViewHolder(@NonNull ChatHolder holder, int position) {
        holder.tvMessage.setText(chatList.get(position).getMessage());
        if (chatList.get(position).getCurrentUser()) {
            holder.tvMessage.setGravity(Gravity.END);
            holder.tvMessage.setTextColor(Color.parseColor("#404040"));
            holder.mContainer.setBackgroundColor(Color.parseColor("#F4F4F4"));
        } else {
            holder.tvMessage.setGravity(Gravity.START);
            holder.tvMessage.setTextColor(Color.parseColor("#FFFFFF"));
            holder.mContainer.setBackgroundColor(Color.parseColor("#2DB4C8"));
        }
    }


    @Override
    public int getItemCount() {
        return chatList.size();
    }
}
