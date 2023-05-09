package model.chat;

import java.io.Serializable;

public class ChatsWith implements Serializable {

    private final static long serialVersionUID = 14L;
    private int chatId;
    private String name;

    public ChatsWith(int chatId, String name){
        this.chatId = chatId;
        this.name = name;

    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
