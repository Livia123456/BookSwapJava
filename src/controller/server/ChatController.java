package controller.server;

import database.chat.DatabaseChat;
import model.chat.ChatHistory;
import model.chat.ChatObject;
import model.chat.MessageObject;

import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * This class is responsible for all the chat-related communications with the database
 * @author Kaser Lindberg
 */
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

        ArrayList<ClientHandler> connectedClients = clientHandler.getServer().getClients();
        for (ClientHandler client : connectedClients) {
            if (client.getCurrentUser().getUserId() == messageObject.getReceiver()) {
                int chatId = dbChat.getChatId(messageObject);
                client.sendMessage(new ChatHistory(dbChat.getChatHistory(chatId)));
            }
        }


    }


    public void checkObject(ChatObject chatObject) {
        int chatId;

        switch(chatObject.getStatus()) {

            case populate:
                clientHandler.sendMessage(dbChat.getActiveChats(chatObject));
                break;

            case newChat:
                if (dbChat.getChatId(new MessageObject(chatObject.getUser1(), chatObject.getUser2(), "")) == 0){
                    dbChat.addChat(new MessageObject(chatObject.getUser1(), chatObject.getUser2(), ""));
            }

            case open:
<<<<<<< Updated upstream
                chatId = dbChat.getChatId(new MessageObject(chatObject.getUser1(), chatObject.getUser2(), ""));
                clientHandler.sendMessage(dbChat.getChatHistory(chatId));
=======
                chatId = dbChat.getChatId(new MessageObject(chatObject.getCurrentUser(), chatObject.getUser2(), ""));
                clientHandler.sendMessage(new ChatHistory(dbChat.getChatHistory(chatId)));
>>>>>>> Stashed changes
                break;

            case delete:
                dbChat.deleteChat(chatObject);
                break;

        }


    }


}
