package com.example.movieonlineapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.movieonlineapp.R;

public class RegisterActivity extends AppCompatActivity {
    EditText edtEmail, edtFullName, edtPassWord, edtPhone;
    Button btnRegister;
    ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initViews();
    }

    private void initViews() {
        edtEmail = findViewById(R.id.edtEmailRegister);
        edtFullName = findViewById(R.id.edtFullNameRegister);
        edtPassWord = findViewById(R.id.edtPassWordLogin);
        edtPhone = findViewById(R.id.edtphoneNumberRegister);
        btnRegister = findViewById(R.id.btnRegister);
        btnBack = findViewById(R.id.btnBack);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputs()) {
                    registerAccount();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();  // Quay lại Activity trước
            }
        });

        registerAccount();
    }

    private void registerAccount() {
        String email = edtEmail.getText().toString();
        String fullName = edtFullName.getText().toString();
        String passWord = edtPassWord.getText().toString();
        String phone = edtPhone.getText().toString();

    }

    private boolean validateInputs() {
        String email = edtEmail.getText().toString();
        String fullName = edtFullName.getText().toString();
        String passWord = edtPassWord.getText().toString();
        String phone = edtPhone.getText().toString();

        if (fullName.isEmpty()) {
            Toast.makeText(this, "Họ tên không được để trống", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (passWord.isEmpty() || passWord.length() < 6) {
            Toast.makeText(this, "Mật khẩu ít nhất 6 ký tự", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (phone.isEmpty() || !android.util.Patterns.PHONE.matcher(phone).matches()) {
            Toast.makeText(this, "Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}