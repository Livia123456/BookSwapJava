package controller.server;

import database.books.DatabaseBooks;
import database.user.DatabaseUser;
import model.Book;
import model.Email;
import model.UserInfo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;

public class ClientHandler {
    private ObjectOutputStream oos;
    private DatabaseUser dbUser;
    private DatabaseBooks dbBook;
    private Socket socket;
    private UserInfo currentUser;


    public ClientHandler(Socket socket, DatabaseUser dbUser) {
        this.dbUser = dbUser;
        this.dbBook = new DatabaseBooks();
        this.socket = socket;
        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        new receiverThread().start();
    }


    private void createNewUser(UserInfo userInfo) {

        dbUser.newUser(userInfo);
        userInfo.setCorrectInfo(true);
        
        try {
            oos.writeObject(userInfo);
            oos.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void checkEmail(Email email) {

        email.setRegistered(dbUser.checkEmail(email.getEmailAddress()));

        try {
            oos.writeObject(email);
            oos.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void logIn(UserInfo userInfo) {

        try {
            oos.writeObject(dbUser.checkUserInfo(userInfo));
            oos.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private class receiverThread extends Thread {

        private ObjectInputStream ois;

        public receiverThread() {
            try {
                ois = new ObjectInputStream(socket.getInputStream());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


        @Override
        public void run() {
            try {
                Object message;
                while ((message = ois.readObject()) != null) {

                    if (message instanceof UserInfo) {
                        System.out.println("Userinfo received");
                        if (((UserInfo) message).getName() == null || ((UserInfo) message).getName().isEmpty()) {
                            logIn((UserInfo) message);
                            currentUser = (UserInfo) message;
                        } else {
                            createNewUser((UserInfo) message);
                            currentUser = (UserInfo) message;
                        }
                    }

                    else if (message instanceof String) {
                        System.out.println(message);
                    }

                    else if (message instanceof Email) {
                        checkEmail((Email) message);
                    }

                    else if (message instanceof Book) {

                        Book book = (Book) message;
                        book.upload(currentUser.getUserId());
                    }
                }
            } catch(IOException | ClassNotFoundException | SQLException e){
                throw new RuntimeException(e);
            }

        }
    }
}
