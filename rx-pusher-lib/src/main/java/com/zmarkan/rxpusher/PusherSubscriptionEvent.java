package com.zmarkan.rxpusher;

public class PusherSubscriptionEvent {
    public final String channelName;
    public final String eventName;
    public final String data;

    public PusherSubscriptionEvent(String channelName, String eventName, String data) {

        this.channelName = channelName;
        this.eventName = eventName;
        this.data = data;
    }
}
