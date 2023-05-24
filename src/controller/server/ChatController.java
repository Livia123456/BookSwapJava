package controller.server;

import database.chat.DatabaseChat;
import model.chat.ChatObject;
import model.chat.ChatsWith;
import model.chat.MessageObject;
import model.chat.StartChat;

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

        for (ClientHandler client : clientHandler.getServer().getClients()) {
            if (client.getCurrentUser().getUserId() == messageObject.getReceiver()) {
                int chatId = dbChat.getChatId(messageObject);
                client.sendMessage(dbChat.getChatHistory(chatId));
            }
        }


    }

    private ArrayList<ChatsWith> setContactsBooks(ArrayList<ChatsWith> array) {
        for (ChatsWith cw : array) {
            cw.getUser().setCurrentUsersUploadedBooks(
                    clientHandler.getBookController().loadBooksUploadedByUser(cw.getUserId()));
        }
        return array;
    }


    public void checkObject(ChatObject chatObject) {
        int chatId;

        switch(chatObject.getStatus()) {

            case populate:
                ArrayList<ChatsWith> chatsWiths = setContactsBooks(dbChat.getActiveChats(chatObject));
                clientHandler.sendMessage(chatsWiths);
                break;

            case newChat:
                StartChat startChat = dbChat.startChatFromSearch(chatObject);
                startChat.setContacts(setContactsBooks(startChat.getContacts()));
                startChat.setImage(clientHandler.getUserController().getProfileImage(startChat.getChatsWith().getUserId()));
                clientHandler.sendMessage(startChat);
                break;
            case open:
                chatId = dbChat.getChatId(new MessageObject(chatObject.getCurrentUser(), chatObject.getUser2(), ""));
                clientHandler.sendMessage(dbChat.getChatHistory(chatId));
                clientHandler.sendMessage(clientHandler.getUserController().getProfileImage(chatObject.getUser2()));
                break;

            case delete:
                dbChat.deleteChat(chatObject);
                break;

        }


    }


}
