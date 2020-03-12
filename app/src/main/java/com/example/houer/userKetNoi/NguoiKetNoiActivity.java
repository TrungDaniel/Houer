package com.example.houer.userKetNoi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.houer.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NguoiKetNoiActivity extends AppCompatActivity {
    private RecyclerView rvKetNoi;
    private RecyclerView.Adapter ketnoiAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nguoi_ket_noi);
        init();
    }

    private void init() {
        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        rvKetNoi = findViewById(R.id.rv_ket_noi);
        rvKetNoi.setNestedScrollingEnabled(false);
        rvKetNoi.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(NguoiKetNoiActivity.this);
        rvKetNoi.setLayoutManager(layoutManager);
        ketnoiAdapter = new NguoiKetNoiAdapter(getDataKetNoi(), NguoiKetNoiActivity.this);
        rvKetNoi.setAdapter(ketnoiAdapter);
        // lấy id người kết nôi
        getUserMatch();


    }

    private void getUserMatch() {

        DatabaseReference matchDb = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId).child("connections").child("matches");
        matchDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot match : dataSnapshot.getChildren()) {
                        // lấy thông tin người kết nối
                        fetchMatchInformation(match.getKey());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void fetchMatchInformation(String key) {
        DatabaseReference userDb = FirebaseDatabase.getInstance().getReference().child("Users").child(key);
        userDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String Name = "";
                    String profileImageUrl = "";
                    String userId = dataSnapshot.getKey();
                    if (dataSnapshot.child("Name").getValue() != null) {
                        Name = dataSnapshot.child("Name").getValue().toString();

                    }
                    if (dataSnapshot.child("profileImageUrl").getValue() != null) {
                        profileImageUrl = dataSnapshot.child("profileImageUrl").getValue().toString();

                    }
                    NguoiKetNoi nguoiKetNoi = new NguoiKetNoi(userId, Name, profileImageUrl);
                    nguoiKetNoiList.add(nguoiKetNoi);
                    ketnoiAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private ArrayList<NguoiKetNoi> nguoiKetNoiList = new ArrayList<>();

    private List<NguoiKetNoi> getDataKetNoi() {
        return nguoiKetNoiList;
    }
}
