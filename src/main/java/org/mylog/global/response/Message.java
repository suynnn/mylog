package org.mylog.global.response;

import lombok.Setter;

@Setter
public class Message {

    private ResponseStatus responseStatus;
    private String message;
    private Object data;

    public Message() {
        this.responseStatus = ResponseStatus.BAD_REQUEST;
        this.message = null;
        this.data = null;
    }
}
