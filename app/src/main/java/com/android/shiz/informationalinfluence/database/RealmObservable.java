package com.android.shiz.informationalinfluence.database;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.internal.Context;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by OldMan on 19.09.2016.
 */
public class RealmObservable {
    private RealmObservable() {
    }

    public static <T extends RealmObject> Observable<T> object(Context context, final Func1<Realm, T> function) {
        return Observable.create(new OnSubscribeRealm<T>(context) {
            @Override
            public T get(Realm realm) {
                return function.call(realm);
            }
        });
}}
