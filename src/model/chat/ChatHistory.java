package model.chat;

import java.io.Serializable;
import java.util.ArrayList;

public class ChatHistory implements Serializable {
    private final static long serialVersionUID = 112L;
    private ArrayList<MessageObject> messages;

    public ChatHistory(ArrayList<MessageObject> messages) {
        this.messages = messages;
    }

    public ArrayList<MessageObject> getMessages() {
        return messages;
    }
}
