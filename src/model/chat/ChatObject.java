package model.chat;

import java.io.Serializable;

/**
 * Class to store information about a chat between two users,
 * the status instance variable is used to communicate instructions to the server.
 * @author Kasper Lindberg
 */

public class ChatObject implements Serializable {

    private final static long serialVersionUID = 7L;
    private int currentUser;
    private int user2;
    private ChatStatus status;

    public ChatObject(int currentUser, int user2, ChatStatus status) {
        this.currentUser = currentUser;
        this.user2 = user2;
        this.status = status;
    }


    public int getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(int currentUser) {
        this.currentUser = currentUser;
    }

    public int getUser2() {
        return user2;
    }

    public void setUser2(int user2) {
        this.user2 = user2;
    }

    public ChatStatus getStatus() {
        return status;
    }

    public void setStatus(ChatStatus status) {
        this.status = status;
    }
}