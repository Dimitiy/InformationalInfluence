package com.android.shiz.informationalinfluence.database;


import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.exceptions.RealmException;
import io.realm.internal.Context;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.subscriptions.Subscriptions;

/**
 * Created by OldMan on 19.09.2016.
 */
public abstract class OnSubscribeRealm <T extends RealmObject> implements Observable.OnSubscribe<T> {
    private Context context;
    private String fileName;

    public OnSubscribeRealm(Context context) {
        this.context = context;
        fileName = null;
    }

    @Override
    public void call(final Subscriber<? super T> subscriber) {
        final Realm realm = Realm.getDefaultInstance();
        subscriber.add(Subscriptions.create(new Action0() {
            @Override
            public void call() {
                try {
                    realm.close();
                } catch (RealmException ex) {
                    subscriber.onError(ex);
                }
            }
        }));

        T object;
        realm.beginTransaction();
        try {
            object = get(realm);
            realm.commitTransaction();
        } catch (RuntimeException e) {
            realm.cancelTransaction();
            subscriber.onError(new RealmException("Error during transaction.", e));
            return;
        } catch (Error e) {
            realm.cancelTransaction();
            subscriber.onError(e);
            return;
        }
        if (object != null) {
            subscriber.onNext(object);
        }
        subscriber.onCompleted();
    }

    public abstract T get(Realm realm);
}