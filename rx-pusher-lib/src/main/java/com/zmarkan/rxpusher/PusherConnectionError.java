package com.zmarkan.rxpusher;

public class PusherConnectionError extends Exception {

    public final String message;
    public final String code;

    public PusherConnectionError(final String message, final String code, Exception e) {
        super(e);
        this.message = message;
        this.code = code;
    }
}
