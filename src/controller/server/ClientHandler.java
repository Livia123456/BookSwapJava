package controller.server;

import database.books.DatabaseBooks;
import database.chat.DatabaseChat;
import database.search.DatabaseSearch;
import database.user.DatabaseUser;
import model.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;

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


    public ClientHandler(Socket socket, DatabaseUser dbUser) {

        //this.dbUser = dbUser;
        //this.dbBook = new DatabaseBooks();
        this.dbSearch = new DatabaseSearch();
        this.dbChat = new DatabaseChat();
        this.socket = socket;
        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //this.dbBook = new DatabaseBooks();
        this.dbSearch = new DatabaseSearch();
        this.userController = new UserController(this);
        this.bookController = new BookController(this);
        new receiverThread().start();
    }


    public void search(SearchObject searchObject){
        try {
            oos.writeObject(dbSearch.search(searchObject.getSearchString()));
            oos.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
    public void sendMessage(Object object) {
        try {
            oos.writeObject(object);
            oos.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private class receiverThread extends Thread {

        @Override
        public void run() {
            try {
                Object message;
                while ((message = ois.readObject()) != null) {

                    if (message instanceof UserInfo) {
                        System.out.println("Userinfo received");
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
                        search((SearchObject) message);

                    }

                    else if (message instanceof MessageObject) {
                        addChatMessage((MessageObject) message);
                    }


                    else if (message instanceof UserInfoUpdate) {
                        userController.updateUserInfo((UserInfoUpdate) message);
                    }

                }
            } catch(IOException | ClassNotFoundException e){
                throw new RuntimeException(e);
            }

        }
    }
}
