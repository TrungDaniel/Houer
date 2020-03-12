package com.example.houer.userKetNoi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.houer.chat.ChatsActivity;
import com.example.houer.R;

public class NguoiKetNoiViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView tvIdMatch;
    public TextView tvNameMatch;
    public ImageView imgUserMatch;

    public NguoiKetNoiViewHolder(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        tvIdMatch = itemView.findViewById(R.id.tv_id_match);
        tvNameMatch = itemView.findViewById(R.id.tv_name_match);
        imgUserMatch = itemView.findViewById(R.id.img_match_user);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(view.getContext(), ChatsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("matchId", tvIdMatch.getText().toString());
        intent.putExtras(bundle);
        view.getContext().startActivity(intent);
    }
}
