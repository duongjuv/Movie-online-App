package com.example.movieonlineapp.data.response;

import java.io.Serializable;

public class UpdateRoleVipResponse implements Serializable {
    private boolean error;
    private String data;
    private String message;

    public boolean getError() {
        return error;
    }

    public String getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
