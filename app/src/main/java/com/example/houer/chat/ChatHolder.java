package com.example.houer.chat;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.houer.R;

public class ChatHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView tvMessage;
    public LinearLayout mContainer;

    public ChatHolder(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        tvMessage = itemView.findViewById(R.id.tv_message);
        mContainer = itemView.findViewById(R.id.ln_container);
    }

    @Override
    public void onClick(View view) {

    }
}
