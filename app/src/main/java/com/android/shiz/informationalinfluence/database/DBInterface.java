package com.android.shiz.informationalinfluence.database;

import com.android.shiz.informationalinfluence.mvp.common.RX.User;

import rx.Observable;

/**
 * Created by OldMan on 18.09.2016.
 */
public interface DBInterface {
    void insertUser(String login, String password);
    boolean checkUser(String login, String password);
    Observable<Boolean> isChecked();
    Observable<User> newUser(String login, String password);

}
