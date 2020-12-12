package com.thang.a17047711_thaivanthang_api_crud_mock;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class DangNhap extends AppCompatActivity {

    EditText editEmail, editMatKhau;
    Button btnDangNhap, btnDangKy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dangnhap);

        editEmail = findViewById(R.id.editEmai);
        editMatKhau = findViewById(R.id.editMatKhau);

        btnDangNhap = findViewById(R.id.btnDangNhap);
        btnDangKy = findViewById(R.id.btnDangKy);


        btnDangKy.setOnClickListener(v -> {
            FirebaseAuth auth = FirebaseAuth.getInstance();

            auth.createUserWithEmailAndPassword(editEmail.getText().toString().trim(), editMatKhau.getText().toString().trim())
                    .addOnCompleteListener(DangNhap.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Toast.makeText(DangNhap.this, "Đăng ký thành công!" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                Toast.makeText(DangNhap.this, "Đăng ký thất bại" + task.getException(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        });

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth auth = FirebaseAuth.getInstance();

                auth.signInWithEmailAndPassword(editEmail.getText().toString().trim(), editMatKhau.getText().toString().trim())
                        .addOnCompleteListener(DangNhap.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (editMatKhau.getText().toString().trim().length() < 6) {
                                        editMatKhau.setError("Mật khẩu lớn hơn 6 ký tự");
                                    } else {
                                        Toast.makeText(DangNhap.this, "Sai email hoặc mật khẩu.", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Toast.makeText(DangNhap.this, "Đăng nhập thành công.", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(DangNhap.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
            }
        });


    }


}
