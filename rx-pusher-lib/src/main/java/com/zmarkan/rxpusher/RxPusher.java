package com.zmarkan.rxpusher;

import com.pusher.client.Pusher;
import com.pusher.client.channel.SubscriptionEventListener;
import com.pusher.client.connection.ConnectionEventListener;
import com.pusher.client.connection.ConnectionStateChange;

import rx.AsyncEmitter;
import rx.Observable;
import rx.functions.Action1;

public class RxPusher {

    private final Pusher pusher;

    public RxPusher(final Pusher pusher) {
        this.pusher = pusher;
    }

    public Observable<ConnectionStateChange> connect(){

        return Observable.fromEmitter(new Action1<AsyncEmitter<ConnectionStateChange>>() {
            @Override
            public void call(AsyncEmitter<ConnectionStateChange> subscriber) {
                final ConnectionEventListener connectionEventListener = new ConnectionEventListener() {
                    @Override
                    public void onConnectionStateChange(ConnectionStateChange connectionStateChange) {
                        subscriber.onNext(connectionStateChange);
                    }

                    @Override
                    public void onError(String message, String code, Exception exception) {
                        subscriber.onError(new PusherConnectionError(message, code, exception));
                    }
                };

                pusher.connect(connectionEventListener);
            }
        }, AsyncEmitter.BackpressureMode.LATEST);
    }

    public Observable<PusherSubscriptionEvent> subscribe(String channelName, String eventName){

        return Observable.fromEmitter(new Action1<AsyncEmitter<PusherSubscriptionEvent>>() {
            @Override
            public void call(AsyncEmitter<PusherSubscriptionEvent> subscriber) {

                SubscriptionEventListener eventListener = new SubscriptionEventListener() {

                    @Override
                    public void onEvent(String channelName, String eventName, String data) {
                        subscriber.onNext(new PusherSubscriptionEvent(channelName, eventName, data));
                    }
                };

                pusher.subscribe(channelName).bind(eventName, eventListener);
            }
        }, AsyncEmitter.BackpressureMode.LATEST);
    }
}
