package com.example.houer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChooseLoginRegistationActivity extends AppCompatActivity {
    private Button btnLogin, btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_login_registation);
        init();
        // Màn hình khởi động đầu tiên

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLogin = new Intent(ChooseLoginRegistationActivity.this, DangNhapActivity.class);
                startActivity(intentLogin);

            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLogin = new Intent(ChooseLoginRegistationActivity.this, DangKyActivity.class);
                startActivity(intentLogin);
            }
        });
    }

    private void init() {
        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);

    }
}
