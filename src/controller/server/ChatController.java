package controller.server;

import database.chat.DatabaseChat;
import model.chat.ChatObject;
import model.chat.MessageObject;

import java.io.ObjectOutputStream;

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

        for (ClientHandler client : clientHandler.getServer().getClients()) {
            if (client.getCurrentUser().getUserId() == messageObject.getReceiver()) {
                int chatId = dbChat.getChatId(messageObject);
                client.sendMessage(dbChat.getChatHistory(chatId));
            }
        }


    }


    public void checkObject(ChatObject chatObject) {
        int chatId;

        switch(chatObject.getStatus()) {

            case populate:
                clientHandler.sendMessage(dbChat.getActiveChats(chatObject));
                break;

            case newChat: //TODO fixa så att både chattsidan öppnas och meddelandehistoriken skickas
                if (dbChat.getChatId(new MessageObject(chatObject.getUser1(), chatObject.getUser2(), "")) == 0){
                    dbChat.addChat(new MessageObject(chatObject.getUser1(), chatObject.getUser2(), ""));
                }
                break;
            case open:
                chatId = dbChat.getChatId(new MessageObject(chatObject.getUser1(), chatObject.getUser2(), ""));
                clientHandler.sendMessage(dbChat.getChatHistory(chatId));
                break;

            case delete:
                dbChat.deleteChat(chatObject);
                break;

        }


    }


}
