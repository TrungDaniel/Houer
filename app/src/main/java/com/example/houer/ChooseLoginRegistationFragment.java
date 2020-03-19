package com.example.houer;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChooseLoginRegistationFragment extends Fragment {
    private Button btnLogin, btnRegister;
    private believe.cht.fadeintextview.TextView textView;
    Handler handler;
    private NavController navController;

    public ChooseLoginRegistationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_choose_login_registation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        hienChu();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_chooseLoginRegistationFragment_to_dangNhapFragment);
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_chooseLoginRegistationFragment_to_dangKyFragment);
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
        }, 1000);
    }

    private void init(View view) {
        btnLogin = view.findViewById(R.id.btn_login);
        btnRegister = view.findViewById(R.id.btn_register);
        textView = view.findViewById(R.id.tv_welcome);
        handler = new Handler();
        navController = Navigation.findNavController(view);
    }
}
