package com.android.shiz.informationalinfluence.mvp.common.RX;

import io.realm.annotations.Required;

/**
 * Created by OldMan on 18.09.2016.
 */
public class User {
    private String login;
    private int password;
    private int id;
    public User(String login, int password) {
        this.login = login;
    }
    public int getId() {
        return id;
    }
    public String getLogin() {
        return login;
    }

    public int getPassword() {
        return password;
    }

}
