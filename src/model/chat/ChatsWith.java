package model.chat;

import model.UserInfo;

import java.io.Serializable;

/**
 * Class to store information linking the current user to a chat and the
 * recipient user of the chat.
 * @author Kasper Lindberg
 */

public class ChatsWith implements Serializable {

    private final static long serialVersionUID = 14L;
    private int chatId;
    private UserInfo user;

    public ChatsWith(int chatId, UserInfo user) {
        this.chatId = chatId;
        this.user = user;

    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public String getName() {
        return user.getName();
    }


    public int getUserId() {
        return user.getUserId();
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }
}
