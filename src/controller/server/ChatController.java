package controller.server;

import database.chat.DatabaseChat;
import model.*;

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


    public void addChatMessage(MessageObject messageObject){

        dbChat.addMessage(messageObject);
    }


    public void checkObject(ChatObject chatObject) {
        int chatId;

        switch(chatObject.getStatus()) {
            case newChat:
                dbChat.addChat(new MessageObject(chatObject.getUser1(), chatObject.getUser2(), ""));
                break;

            case open:
                chatId = dbChat.getChatId(new MessageObject(chatObject.getUser1(), chatObject.getUser2(), ""));
                clientHandler.sendMessage(dbChat.getChatHistory(chatId));
                break;

            case delete:
                dbChat.deleteChat(chatObject);
                break;

        }

       /* if (chatObject.getStatus().equals(ChatStatus.open)) {
            chatId = dbChat.getChatId(new MessageObject(chatObject.getUser1(), chatObject.getUser2(), ""));
            clientHandler.sendMessage(dbChat.getChatHistory(chatId));
        } */

    }


}
