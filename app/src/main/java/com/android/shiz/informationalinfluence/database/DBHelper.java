package com.android.shiz.informationalinfluence.database;

import android.util.Log;

import com.android.shiz.informationalinfluence.MyApplication;
import com.android.shiz.informationalinfluence.mvp.common.RX.User;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmObject;
import io.realm.internal.Context;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.subscriptions.Subscriptions;

/**
 * Created by OldMan on 18.09.2016.
 */
public class DBHelper implements DBInterface {
    private Realm realm;
    private String TAG = "DBHelper";
    private Context context;

    public DBHelper() {
        realm = Realm.getDefaultInstance();
//        this.context = Realm.get();
//        ??
    }


    @Override
    public void insertUser(final String login, final String parol) {
        RealmUser user = realm.where(RealmUser.class).equalTo("login", login).findFirst();
        if (user != null)
            return;
        realm.executeTransaction(realm -> {
            RealmUser mission = realm.createObject(RealmUser.class);
            mission.setId(getPrimaryKey(mission));
            mission.setLogin(login);
            mission.setPassword(parol.hashCode());
            Log.d(TAG, "insertUser");
        });
    }

    @Override
    public boolean checkUser(String login, String password) {
        RealmUser user = realm.where(RealmUser.class).equalTo("login", login).findFirst();
        if (user != null)
            if (user.getPassword() == password.hashCode())
                return true;
        Log.d(TAG, "user empty");
        return false;
    }

    @Override
    public Observable<Boolean> isChecked() {
        return null;
    }

    @Override
    public Observable<User> newUser(String login, String password) {
        // map internal UI objects to Realm objects
        final RealmUser realmUser = new RealmUser();
        realmUser.setLogin(login);
        realmUser.setPassword(password.hashCode());

        return RealmObservable.object(context, new Func1<Realm, RealmUser>() {
            @Override
            public RealmUser call(Realm realm) {
                // internal object instances are not created by realm
                // saving them using copyToRealm returning instance associated with realm
                return realm.copyToRealm(realmUser);
            }
        }).map(new Func1<RealmUser, User>() {
            @Override
            public User call(RealmUser realmUser) {
                // map to UI object
                return userFromRealm(realmUser);
            }
        });
    }

    private User userFromRealm(RealmUser realmUser) {
        final String login = realmUser.getLogin();
        final int password = realmUser.getPassword();
        return new User(login, password);
    }

    private static Observable<Realm> getManagedRealm() {
        return Observable.create(new Observable.OnSubscribe<Realm>() {
            @Override
            public void call(final Subscriber<? super Realm> subscriber) {
                final Realm realm = Realm.getDefaultInstance();
                subscriber.add(Subscriptions.create(new Action0() {
                    @Override
                    public void call() {
                        realm.close();
                    }
                }));
                subscriber.onNext(realm);
            }
        });
    }
    public Observable<RealmUser> checkUser1(String login, String password) {
        return realm.where(RealmUser.class)
                .equalTo("login", login)
                .findFirstAsync()
                .asObservable()
                .filter(realmObject -> realmObject.isValid() && realmObject.isLoaded())
                .subscribeOn(AndroidSchedulers.mainThread())
                .cast(RealmUser.class);}

    public int getPrimaryKey(RealmObject object) {
        return realm.where(object.getClass()).max("id").intValue() + 1;
    }

    public void closeRealm(RealmChangeListener listener) {
        realm.removeChangeListener(listener);
        realm.close();
    }

    public void closeRealm() {
        realm.close();
    }
}
