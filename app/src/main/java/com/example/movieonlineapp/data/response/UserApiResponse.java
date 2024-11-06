package com.example.movieonlineapp.data.response;

import com.example.movieonlineapp.domain.model.User;
import com.google.gson.annotations.SerializedName;

public class UserApiResponse {
    @SerializedName("error")
    private boolean error;

    @SerializedName("data")
    private Data data;

    @SerializedName("message")
    private String message;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // Inner class Data
    public static class Data {

        @SerializedName("user")
        private User user;

        @SerializedName("token")
        private String token;

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }

}
