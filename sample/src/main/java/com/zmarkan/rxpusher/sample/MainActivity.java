package com.zmarkan.rxpusher.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.ChannelEventListener;
import com.pusher.client.channel.SubscriptionEventListener;
import com.pusher.client.connection.ConnectionEventListener;
import com.pusher.client.connection.ConnectionState;
import com.pusher.client.connection.ConnectionStateChange;
import com.zmarkan.rxpusher.PusherSubscriptionEvent;
import com.zmarkan.rxpusher.R;
import com.zmarkan.rxpusher.RxPusher;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO: integrate retrolambda
        //TODO: add rx event handling

        PusherOptions options = new PusherOptions();
        options.setCluster("eu");

        Pusher pusher = new Pusher("c4b492988e9674461319", options);
        final String channelName = "messages";
        final String eventName = "messageReceived";

        final RxPusher rxPusher = new RxPusher(pusher);
        rxPusher.connect()
                .filter(new Func1<ConnectionStateChange, Boolean>() {
                    @Override
                    public Boolean call(ConnectionStateChange connectionStateChange) {
                        return ConnectionState.CONNECTED.equals(connectionStateChange.getCurrentState().name());
                    }
                })
                .flatMap(new Func1<ConnectionStateChange, Observable<PusherSubscriptionEvent>>() {
                    @Override
                    public Observable<PusherSubscriptionEvent> call(ConnectionStateChange connectionStateChange) {
                        return rxPusher.subscribe(channelName, eventName);
                    }
                });

    }
}