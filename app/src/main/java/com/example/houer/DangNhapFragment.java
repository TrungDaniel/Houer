package com.example.houer;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * A simple {@link Fragment} subclass.
 */
public class DangNhapFragment extends Fragment {
    private Button btnDangNhap;
    private EditText edtDangNhapEmail, edtDangNhapPass;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;
    private ProgressDialog progress;
    private Snackbar snackbar;

    public DangNhapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dang_nhap, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                String strEmail = edtDangNhapEmail.getText().toString();
                String strPass = edtDangNhapPass.getText().toString();

                progress.setTitle("Vui lòng đợi!!");
                progress.setMessage("Đang đăng nhập");
                progress.setCancelable(false);
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.show();
                mAuth.signInWithEmailAndPassword(strEmail, strPass).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            progress.dismiss();
                            Snackbar.make(getActivity().findViewById(R.id.ln_dang_nhap), "Đăng nhập thất bại", Snackbar.LENGTH_LONG)
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
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    startActivity(intent);

                }
            }
        };
    }

    private void init(View view) {
        btnDangNhap = view.findViewById(R.id.btn_dang_nhap);
        edtDangNhapEmail = view.findViewById(R.id.edt_login_email);
        edtDangNhapPass = view.findViewById(R.id.edt_login_pass);
        mAuth = FirebaseAuth.getInstance();
        progress = new ProgressDialog(getContext());
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthStateListener);
    }
}
