package com.example.houer.userKetNoi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.houer.R;

import java.util.List;

public class NguoiKetNoiAdapter extends RecyclerView.Adapter<NguoiKetNoiViewHolder> {
    private List<NguoiKetNoi> nguoiKetNoiList;
    private Context context;

    public NguoiKetNoiAdapter(List<NguoiKetNoi> nguoiKetNoiList, Context context) {
        this.nguoiKetNoiList = nguoiKetNoiList;
        this.context = context;
    }

    @NonNull
    @Override
    public NguoiKetNoiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_nguoi_ket_noi, parent, false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(layoutParams);
        NguoiKetNoiViewHolder nguoiKetNoiViewHolder = new NguoiKetNoiViewHolder(view);
        return nguoiKetNoiViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull NguoiKetNoiViewHolder holder, int position) {
        holder.tvIdMatch.setText(nguoiKetNoiList.get(position).getUserId());
        holder.tvNameMatch.setText(nguoiKetNoiList.get(position).getName());
        if (!nguoiKetNoiList.get(position).getProfileImageUrl().equals("default")) {
            Glide.with(context).load(nguoiKetNoiList.get(position).getProfileImageUrl()).into(holder.imgUserMatch);
        }


    }

    @Override
    public int getItemCount() {
        return nguoiKetNoiList.size();
    }
}
