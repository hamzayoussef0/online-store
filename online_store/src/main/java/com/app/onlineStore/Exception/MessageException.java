package com.app.onlineStore.Exception;

public class MessageException extends Exception{

    String message;

    public MessageException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
