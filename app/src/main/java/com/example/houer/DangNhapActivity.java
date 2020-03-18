package com.example.houer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DangNhapActivity extends AppCompatActivity {
    private Button btnDangNhap;
    private EditText edtDangNhapEmail, edtDangNhapPass;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;
    private ProgressDialog progress;
    private Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        init();
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strEmail = edtDangNhapEmail.getText().toString();
                String strPass = edtDangNhapPass.getText().toString();

                progress.setTitle("Vui lòng đợi!!");
                progress.setMessage("Đang đăng nhập");
                progress.setCancelable(false);
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.show();
                mAuth.signInWithEmailAndPassword(strEmail, strPass).addOnCompleteListener(DangNhapActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            progress.dismiss();
                            Snackbar.make(findViewById(R.id.ln_dang_nhap), "Đăng nhập thất bại", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();

                        }
                    }
                });


            }
        });
        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    progress.dismiss();
                    Intent intent = new Intent(DangNhapActivity.this, MainActivity.class);
                    startActivity(intent);

                }
            }
        };
    }

    private void init() {
        btnDangNhap = findViewById(R.id.btn_dang_nhap);
        edtDangNhapEmail = findViewById(R.id.edt_login_email);
        edtDangNhapPass = findViewById(R.id.edt_login_pass);
        mAuth = FirebaseAuth.getInstance();
        progress = new ProgressDialog(DangNhapActivity.this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthStateListener);
    }
}
