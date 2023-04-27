package model;

import java.io.Serializable;

public class MessageObject implements Serializable {

    private final static long serialVersionUID = 10L;
    private int sender;
    private int reciever;
    private String message;

    public MessageObject(int sender, int reciever, String message){
        this.sender = sender;
        this.reciever = reciever;
        this.message = message;
    }

    public int getSender() {
        return sender;
    }

    public void setSender(int sender) {
        this.sender = sender;
    }

    public int getReciever() {
        return reciever;
    }

    public void setReciever(int reciever) {
        this.reciever = reciever;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
