package com.android.shiz.informationalinfluence;

import com.android.shiz.informationalinfluence.database.DBHelper;
import com.arellomobile.mvp.MvpApplication;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by OldMan on 18.09.2016.
 */
public class MyApplication extends MvpApplication {
    private static MyApplication sInstance;

    public MyApplication() {
        sInstance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        RealmConfiguration config = new RealmConfiguration.Builder(this).build();
        Realm.deleteRealm(config);
        Realm.setDefaultConfiguration(config);
        setUser();
    }

    public static MyApplication get() {
        return sInstance;
    }


    private void setUser() {

        DBHelper database = new DBHelper();
        database.insertUser("admin", "admin");
        database.closeRealm();
    }
}
