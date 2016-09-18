package com.android.shiz.informationalinfluence.database;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by OldMan on 18.09.2016.
 */
public class RealmUser extends RealmObject {
    @PrimaryKey
    private int id;
    @Required // Name is not nullable
    private String login;
    @Required // Name is not nullable
    private int password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }
}