package model.chat;

import java.io.Serializable;
import java.util.ArrayList;

public class StartChat implements Serializable {
    private final static long serialVersionUID = 1177L;
    private ArrayList<MessageObject> messages;
    private ArrayList<ChatsWith> contacts;

    public StartChat(ArrayList<MessageObject> messages) {
        this.messages = messages;
    }

    public ArrayList<MessageObject> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<MessageObject> messages) {
        this.messages = messages;
    }

    public ArrayList<ChatsWith> getContacts() {
        return contacts;
    }

    public void setContacts(ArrayList<ChatsWith> contacts) {
        this.contacts = contacts;
    }
}
