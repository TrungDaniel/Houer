package com.example.houer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import believe.cht.fadeintextview.TextViewListener;

public class ChooseLoginRegistationActivity extends AppCompatActivity {
    private Button btnLogin, btnRegister;
    private believe.cht.fadeintextview.TextView textView;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_login_registation);
        init();
        hienChu();
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

    private void hienChu() {

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.setLetterDuration(50); // sets letter duration programmatically
                textView.setText("Kết nối và trao đi yêu thương đến \n mọi người "); // sets the text with animation (Read "KNOWN BUGS" if it doesn't give desired results)
                textView.isAnimating(); // returns current boolean animation state (optional)
            }
        },1000);

    }

    private void init() {
        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);
        textView = findViewById(R.id.tv_welcome);
        handler = new Handler();

    }
}
