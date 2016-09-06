package com.zmarkan.rxpusher;

import com.pusher.client.Pusher;
import com.pusher.client.connection.ConnectionEventListener;
import com.pusher.client.connection.ConnectionState;
import com.pusher.client.connection.ConnectionStateChange;

import rx.Observable;
import rx.functions.Func0;
import rx.subjects.PublishSubject;

public class RxPusher {

    private final Pusher pusher;

    public RxPusher(final Pusher pusher) {
        this.pusher = pusher;
    }


    public Observable<ConnectionStateChange> connect(){
        return Observable.defer(new Func0<Observable<ConnectionStateChange>>() {

            @Override
            public Observable<ConnectionStateChange> call() {
                PublishSubject<ConnectionStateChange> connectionSubject = PublishSubject.create();

                pusher.connect(new ConnectionEventListener() {
                    @Override
                    public void onConnectionStateChange(ConnectionStateChange connectionStateChange) {
                        connectionSubject.onNext(connectionStateChange);
                    }

                    @Override
                    public void onError(String s, String s1, Exception e) {

                        connectionSubject.onError(new PusherConnectionError(s, s1, e));
                    }
                });
                return connectionSubject.asObservable();
            }
        });
    }
}
