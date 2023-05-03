package controller.server;

import database.chat.DatabaseChat;
import database.user.DatabaseUser;
import model.Email;
import model.UserInfo;
import model.UserInfoUpdate;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class ChatController {

    private DatabaseChat dbChat;
    private ClientHandler clientHandler;
    private ObjectOutputStream oos;

    public ChatController(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
        this.oos = clientHandler.getOOS();
        dbChat = new DatabaseChat();
    }



}
