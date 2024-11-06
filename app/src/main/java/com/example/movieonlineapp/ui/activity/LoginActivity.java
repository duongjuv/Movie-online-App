package com.example.movieonlineapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;

import com.example.movieonlineapp.MyApplication;
import com.example.movieonlineapp.R;

import com.example.movieonlineapp.data.repository.Repository;
import com.example.movieonlineapp.data.source.remote.RemoteDataSource;
import com.example.movieonlineapp.domain.login.FilterLogin;
import com.example.movieonlineapp.domain.login.FilterLoginImpl;
import com.example.movieonlineapp.domain.model.User;
import com.example.movieonlineapp.ui.personal.user.UserItemViewModel;
import com.example.movieonlineapp.ui.main.MainActivity;
import com.google.android.material.snackbar.Snackbar;

import java.util.concurrent.ExecutorService;

public class LoginActivity extends AppCompatActivity implements FilterLoginImpl.LoginCallback {
    private EditText userEdt, passEdit;
    private TextView textViewRegister;
    private Button loginBtn;
    private FilterLogin checkLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initViews();
        click();
    }

    private void initViews() {
        userEdt = findViewById(R.id.edtUserNameLogin);
        passEdit = findViewById(R.id.edtPassWordLogin);
        loginBtn = findViewById(R.id.btnLogin);
        textViewRegister = findViewById(R.id.txtRegister);
    }

    private void click() {
//        ExecutorService executorService = ((MyApplication) getApplicationContext()).homeFragmentExecutorService;
//        RemoteDataSource remoteDataSource = new RemoteDataSource(executorService);
//        Repository repository = new Repository.Builder()
//                .setRemoteDataSource(remoteDataSource)
//                .build();
        checkLogin = new FilterLoginImpl(this);
        UserItemViewModel model = UserItemViewModel.getInstance();
        model.getUserData().observe(this, user -> {});
        loginStart();
        registerStart();
    }

    @Override
    public void onLoginSuccess() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }

    @Override
    public void onLoginFailure(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void loginStart() {
        loginBtn.setOnClickListener(v -> {
            String username = userEdt.getText().toString();
            String password = passEdit.getText().toString();
            if (checkLogin.isFieldEmpty(username) || checkLogin.isFieldEmpty(password)) {
                String msg = getString(R.string.txt_errorLoginIsEmpty);
                if (LoginActivity.this.getCurrentFocus() != null) {
                    Snackbar.make(LoginActivity.this.getCurrentFocus(), msg, Snackbar.LENGTH_SHORT)
                            .show();
                }
            } else {
                checkLogin.login(username, password, this);
            }
        });
    }

    private void registerStart() {
        textViewRegister.setOnClickListener(v -> {
            // Mở RegisterActivity khi nhấn vào TextView
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}