package com.example.movieonlineapp.domain.register;

import com.example.movieonlineapp.domain.login.FilterLoginImpl;
import com.example.movieonlineapp.domain.model.User;

import java.util.List;

public interface FilterRegister {

    public boolean isFieldEmpty(String data);
    public boolean isAccountExisted(List<User> userList, String email);
    public void register(String userName, String passWord, FilterLoginImpl.LoginCallback callback);
}
