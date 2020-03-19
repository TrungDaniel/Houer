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


/**
 * A simple {@link Fragment} subclass.
 */
public class DangKyFragment extends Fragment {
    private Button btnDangKy;
    private EditText edtEmail, edtPass, edtName;
    private RadioGroup radioGroup;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;
    private Snackbar snackbar;
    private ProgressDialog progress;

    public DangKyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dang_ky, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        dangKyTaiKhoan(view);
    }

    private void dangKyTaiKhoan(View view) {
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                //Lấy giá trị của email và pass người dùng
                final String emailUser = edtEmail.getText().toString();
                final String passUser = edtPass.getText().toString();
                final String nameUser = edtName.getText().toString();
                int selectId = radioGroup.getCheckedRadioButtonId();
                final RadioButton radioButton = view.findViewById(selectId);

                // kiểm tra các dữ liệu nhập vào có null hay không

                if (nameUser.matches("") || radioGroup.getCheckedRadioButtonId() == -1 || emailUser.matches("") || passUser.matches("")) {
                    Snackbar.make(getActivity().findViewById(R.id.ln_dang_ky_frag), "Nhập đầy đủ", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();


                }
                // không null thì vào đây
                else {
                    progress = new ProgressDialog(getContext());
                    progress.setTitle("Vui lòng đợi!!");
                    progress.setMessage("Tài khoản của bạn đang đợi tạo");
                    progress.setCancelable(false);
                    progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progress.show();
                    // đăng ký với email và mật khẩu
                    mAuth.createUserWithEmailAndPassword(emailUser, passUser).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            // nếu đăng ký thất bại
                            if (!task.isSuccessful()) {
                                Snackbar.make(getActivity().findViewById(R.id.ln_dang_ky_frag), "Đăng ký thất bại, kiểm tra lại thông tin", Snackbar.LENGTH_LONG)
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
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    startActivity(intent);

                }
            }
        };
    }

    private void init(View view) {
        btnDangKy = view.findViewById(R.id.btn_dang_ky);
        edtEmail = view.findViewById(R.id.edt_email);
        edtName = view.findViewById(R.id.edt_name);
        radioGroup = view.findViewById(R.id.radioGroup);
        edtPass = view.findViewById(R.id.edt_pass);
        mAuth = FirebaseAuth.getInstance();
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
