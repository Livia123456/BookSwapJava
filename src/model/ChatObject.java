package model;

import java.io.Serializable;

public class ChatObject implements Serializable {

    private final static long serialVersionUID = 7L;
    private int user1;
    private int user2;
    private ChatStatus status;

    
    public ChatObject(int user1, int user2, ChatStatus status){
        this.user1 = user1;
        this.user2 = user2;
        this.status = status;
    }


    public int getUser1() {
        return user1;
    }

    public void setUser1(int user1) {
        this.user1 = user1;
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
