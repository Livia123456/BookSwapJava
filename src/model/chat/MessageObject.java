package model.chat;

import java.io.Serializable;

/**
 * Class to store information about a specific chat message.
 * @author Kasper Lindberg
 */

public class MessageObject implements Serializable {

    private final static long serialVersionUID = 10L;
    private int sender;
    private int receiver;
    private String message;

    public MessageObject(int sender, int receiver, String message) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
    }

    public int getSender() {
        return sender;
    }

    public void setSender(int sender) {
        this.sender = sender;
    }

    public int getReceiver() {
        return receiver;
    }

    public void setReceiver(int receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
