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
import com.pusher.client.connection.ConnectionStateChange;
import com.zmarkan.rxpusher.R;
import com.zmarkan.rxpusher.RxPusher;

import rx.functions.Action1;

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

        RxPusher rxPusher = new RxPusher(pusher);
        rxPusher.connect().subscribe(new Action1<ConnectionStateChange>() {
            @Override
            public void call(ConnectionStateChange connectionStateChange) {
                //TODO: do something cool
            }
        });


// TODO: remove - this is just for reference
//        pusher.connect(new ConnectionEventListener() {
//            @Override
//            public void onConnectionStateChange(ConnectionStateChange connectionStateChange) {
//                Log.d("PUSHER", String.format(">>>>>>>>>>> connectionStateChange: %s", connectionStateChange.getCurrentState().name()));
//
//            }
//
//            @Override
//            public void onError(String s, String s1, Exception e) {
//                Log.d("PUSHER", String.format(">>>>>>>>>>> error: %s %s", s, s1));
//                e.printStackTrace();
//
//            }
//        });
//
//        Channel channel = pusher.subscribe("test_channel", new ChannelEventListener() {
//            @Override
//            public void onSubscriptionSucceeded(String s) {
//                Log.d("PUSHER", String.format(">>>>>>>>>>> channelName: %s", s));
//
//            }
//
//            @Override
//            public void onEvent(String s, String s1, String s2) {
//                Log.d("PUSHER", String.format(">>>>>>>>>>> channelName: %s, eventName: %s, data: %s", s, s1, s2));
//
//            }
//        });
//
//        channel.bind("my_event", new SubscriptionEventListener() {
//            @Override
//            public void onEvent(String channelName, String eventName, final String data) {
//                Log.d("PUSHER", String.format(">>>>>>>>>>> channelName: %s, eventName: %s, data: %s", channelName, eventName, data));
//            }
//        });
    }
}
