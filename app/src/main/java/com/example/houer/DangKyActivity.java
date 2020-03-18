package com.example.houer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class DangKyActivity extends AppCompatActivity {
    private Button btnDangKy;
    private EditText edtEmail, edtPass, edtName;
    private RadioGroup radioGroup;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;
    private Snackbar snackbar;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        init();
        dangKyTaiKhoan();

    }

    private void dangKyTaiKhoan() {
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Lấy giá trị của email và pass người dùng
                final String emailUser = edtEmail.getText().toString();
                final String passUser = edtPass.getText().toString();
                final String nameUser = edtName.getText().toString();
                int selectId = radioGroup.getCheckedRadioButtonId();
                final RadioButton radioButton = findViewById(selectId);

                // kiểm tra các dữ liệu nhập vào có null hay không

                if (nameUser.matches("") || radioGroup.getCheckedRadioButtonId() == -1 || emailUser.matches("") || passUser.matches("")) {
                    Snackbar.make(findViewById(R.id.ln_dang_ky), "Vui lòng nhập đầy đủ", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();


                }
                // không null thì vào đây
                else {
                    progress = new ProgressDialog(DangKyActivity.this);
                    progress.setTitle("Vui lòng đợi!!");
                    progress.setMessage("Tài khoản của bạn đang đợi tạo");
                    progress.setCancelable(false);
                    progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progress.show();
                    // đăng ký với email và mật khẩu
                    mAuth.createUserWithEmailAndPassword(emailUser, passUser).addOnCompleteListener(DangKyActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            // nếu đăng ký thất bại
                            if (!task.isSuccessful()) {
                                Snackbar.make(findViewById(R.id.ln_dang_ky), "Đăng ký thất bại, kiểm tra lại thông tin", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                                progress.dismiss();
                            }
                            // nếu đăng ký thành công
                            else {

                                String userId = mAuth.getCurrentUser().getUid(); // lấy uId người dùng
                                // truyền dữ liệu lên firebase
                                DatabaseReference currentDb = FirebaseDatabase.getInstance().getReference()
                                        .child("Users").child(userId);
                                Map userInfo = new HashMap();
                                userInfo.put("Name", nameUser);
                                userInfo.put("sex", radioButton.getText().toString());
                                userInfo.put("profileImageUrl", "default");
                                currentDb.updateChildren(userInfo);

                                progress.dismiss();

                            }
                        }
                    });
                }


            }
        });
        // kiểm tra nếu tài khoản đã tồn tại thì sẽ chuyển sang màn hình Main
        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(DangKyActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
    }

    private void init() {
        btnDangKy = findViewById(R.id.btn_dang_ky);
        edtEmail = findViewById(R.id.edt_email);
        edtName = findViewById(R.id.edt_name);
        radioGroup = findViewById(R.id.radioGroup);
        edtPass = findViewById(R.id.edt_pass);
        mAuth = FirebaseAuth.getInstance();


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
