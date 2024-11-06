package com.example.movieonlineapp.domain.login;


public interface FilterLogin {
    boolean isFieldEmpty(String data);

    void login(String userName, String passWord, FilterLoginImpl.LoginCallback callback);


}
