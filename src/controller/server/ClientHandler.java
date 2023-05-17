package controller.server;

import database.chat.DatabaseChat;
import database.search.DatabaseSearch;
import database.user.DatabaseUser;
import model.*;
import model.search.*;
import model.chat.ChatObject;
import model.chat.MessageObject;
import model.search.AdvancedSearchObject;
import model.search.SearchObject;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * This class is responsible for handling the clients connected to the server.
 * @author Kasper Lindberg, Livia Tengelin, Sebastian Zulj
 */
public class ClientHandler {
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    //private DatabaseBooks dbBook;
    private DatabaseSearch dbSearch;
    private DatabaseChat dbChat;
    private Socket socket;
    //private UserInfo currentUser;
    private UserController userController;
    private BookController bookController;
    private ChatController chatController;
    private SearchController searchController;
    private Server server;


    public ClientHandler(Socket socket, Server server) {
        this.server = server;
        this.dbChat = new DatabaseChat();
        this.socket = socket;
        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.dbSearch = new DatabaseSearch();
        this.userController = new UserController(this);
        this.bookController = new BookController(this);
        this.searchController = new SearchController(this);
        this.chatController = new ChatController(this);
        new receiverThread().start();
    }

    public void addChatMessage(MessageObject messageObject){

        dbChat.addMessage(messageObject);

    }


    public ObjectInputStream getOIS() {
        return ois;
    }

    public ObjectOutputStream getOOS() {
        return oos;
    }

    public UserInfo getCurrentUser() {
        return userController.getCurrentUser();
    }


    public BookController getBookController() {
        return bookController;
    }

    public void sendMessage(Object object) {
        try {
            oos.writeObject(object);
            oos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Server getServer() {
        return server;
    }

    /**
     * The reciever thread is responsible for checking what types of messages/requests a connected client sends.
     */
    private class receiverThread extends Thread {

        @Override
        public void run() {
            try {
                Object message;
                while ((message = ois.readObject()) != null) {

                    if (message instanceof UserInfo) {
                        userController.userInfoReceived((UserInfo) message);
                    }

                    else if (message instanceof String) {
                        System.out.println(message);
                    }

                    else if (message instanceof Email) {
                        userController.checkEmail((Email) message);
                    }

                    else if (message instanceof Book) {
                        bookController.uploadBook((Book) message);

                    }

                    else if (message instanceof AccountToDelete) {
                        userController.deleteAccount((AccountToDelete) message);
                    }

                    else if (message instanceof SearchObject) {
                        searchController.search((SearchObject) message);
                    }

                    else if (message instanceof MessageObject) {
                        chatController.addChatMessage((MessageObject) message);
                    }

                    else if (message instanceof ChatObject) {
                        chatController.checkObject((ChatObject) message);
                    }

                    else if (message instanceof UserInfoUpdate) {
                        userController.updateUserInfo((UserInfoUpdate) message);
                    }

                    else if (message instanceof AdvancedSearchObject) {
                        searchController.advancedSearch((AdvancedSearchObject) message);
                    }

                    else if (message instanceof BookToDelete) {
                        bookController.deleteBook(((BookToDelete) message).getBookId());
                    }


                }
            } catch (EOFException e) {
                System.out.println("Client disconnected");
            }
            catch(IOException | ClassNotFoundException e){
                throw new RuntimeException(e);
            }

        }
    }
}
