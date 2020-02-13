package com.example.houer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DangKyActivity extends AppCompatActivity {
    private Button btnDangKy;
    private EditText edtEmail,edtPass,edtName;
    private RadioGroup radioGroup;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        init();
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Lấy giá trị của email và pass người dùng
                final String emailUser = edtEmail.getText().toString();
                final String passUser = edtPass.getText().toString();
                final String nameUser = edtName.getText().toString();
                int selectId=radioGroup.getCheckedRadioButtonId();
                final RadioButton radioButton = findViewById(selectId);
                if (radioButton.getText()==null){
                    return;
                }

                mAuth.createUserWithEmailAndPassword(emailUser,passUser).addOnCompleteListener(DangKyActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()){
                            Toast.makeText(DangKyActivity.this, "Xin vui lòng kiểm tra lại kết nối mạng", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            String userId=mAuth.getCurrentUser().getUid();
                            DatabaseReference currentDb = FirebaseDatabase.getInstance().getReference()
                                    .child("Users").child(radioButton.getText().toString()).child(userId).child("Name");
                            currentDb.setValue(nameUser);

                            Toast.makeText(DangKyActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        firebaseAuthStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user!=null){
                    Intent intent = new Intent(DangKyActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        };
    }

    private void init() {
        btnDangKy=findViewById(R.id.btn_dang_ky);
        edtEmail=findViewById(R.id.edt_email);
        edtName=findViewById(R.id.edt_name);
        radioGroup=findViewById(R.id.radioGroup);
        edtPass=findViewById(R.id.edt_pass);
        mAuth= FirebaseAuth.getInstance();


    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.addAuthStateListener(firebaseAuthStateListener);
    }
}
