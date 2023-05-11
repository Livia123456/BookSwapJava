package model.chat;

import java.io.Serializable;

/**
 * Class to store information linking the current user to a chat and the
 * recipient user of the chat.
 * @author Kasper Lindberg
 */

public class ChatsWith implements Serializable {

    private final static long serialVersionUID = 14L;
    private int chatId;
    private String name;
    private int userId;

    public ChatsWith(int chatId, String name, int userId) {
        this.chatId = chatId;
        this.name = name;
        this.userId = userId;

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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
