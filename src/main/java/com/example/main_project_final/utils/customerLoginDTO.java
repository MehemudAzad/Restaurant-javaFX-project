package com.example.main_project_final.utils;

import java.io.Serializable;

public class customerLoginDTO implements Serializable {
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }

    private String userName;
    private boolean status;
}
