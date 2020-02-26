package com.example.houer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Cards cards_data[];

    private ArrayAdapterCards arrayAdapterCards;
    private int i;
    private Button btnDangXuat;
    private FirebaseAuth mAuth;
    private DatabaseReference userDb;
    ListView listView;
    List<Cards> rowItems;
    private String currentUId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        checkUserSex();
        dangXuat();
        rowItems = new ArrayList<Cards>();

        arrayAdapterCards = new ArrayAdapterCards(this, R.layout.item, rowItems);

        SwipeFlingAdapterView flingContainer = findViewById(R.id.frame);
        flingContainer.setAdapter(arrayAdapterCards);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                rowItems.remove(0);
                arrayAdapterCards.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject'

                Cards obj = (Cards) dataObject;
                String userId = obj.getUserId();
                userDb.child(notuserSex).child(userId).child("connection").child("nope").child(currentUId).setValue(true);
                Toast.makeText(MainActivity.this, "Left", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onRightCardExit(Object dataObject) {
                Cards obj = (Cards) dataObject;
                String userId = obj.getUserId();
                userDb.child(notuserSex).child(userId).child("connection").child("yeps").child(currentUId).setValue(true);
                Toast.makeText(MainActivity.this, "Right", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // Ask for more data here

            }

            @Override
            public void onScroll(float scrollProgressPercent) {

            }
        });


        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                Toast.makeText(MainActivity.this, "clicked", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private String userSex;
    private String notuserSex;

    private void checkUserSex() {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference maleDb = FirebaseDatabase.getInstance().getReference().child("Users").child("Nam");
        maleDb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.getKey().equals(user.getUid())) {
                    userSex = "Nam";
                    notuserSex = "Nu";
                    getNotUserSex();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


        DatabaseReference femaleDb = FirebaseDatabase.getInstance().getReference().child("Users").child("Nu");
        femaleDb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.getKey().equals(user.getUid())) {
                    userSex = "Nu";
                    notuserSex = "Nam";
                    getNotUserSex();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void getNotUserSex() {
        DatabaseReference notSexDb = FirebaseDatabase.getInstance().getReference().child("Users").child(notuserSex);
        notSexDb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.exists() && !dataSnapshot.child("connection").child("nope").hasChild(currentUId) && !dataSnapshot.child("connection").child("yeps").hasChild(currentUId)) {
                    Cards cardsName = new Cards(dataSnapshot.getKey(), dataSnapshot.child("Name").getValue().toString());
                    rowItems.add(cardsName);
                    arrayAdapterCards.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void dangXuat() {
        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent intent = new Intent(MainActivity.this, ChooseLoginRegistationActivity.class);
                startActivity(intent);
                finish();
                return;

            }
        });
    }

    private void init() {
        btnDangXuat = findViewById(R.id.btn_dang_xuat);
        mAuth = FirebaseAuth.getInstance();
        userDb = FirebaseDatabase.getInstance().getReference().child("Users");
        currentUId = mAuth.getCurrentUser().getUid();
    }


}