package com.example.movieonlineapp.domain.register;

import com.example.movieonlineapp.domain.login.FilterLogin;
import com.example.movieonlineapp.domain.login.FilterLoginImpl;
import com.example.movieonlineapp.domain.model.User;

import java.util.List;

public abstract class FilterRegisterImpl implements FilterRegister {
    @Override
    public boolean isFieldEmpty(String data) {
        return false;
    }

    @Override
    public boolean isAccountExisted(List<User> userList, String email) {
        return false;
    }


    // Interface Callback để xử lý kết quả đăng ky
    public interface RegisterCallback {
        void onLoginSuccess();

        void onLoginFailure(String msg);
    }
}
