package controller.server;

import controller.ClientDatabaseCommunication;
import model.Email;
import model.UserInfo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler extends Thread{
    private ObjectOutputStream oos;
    private ClientDatabaseCommunication cdc;
    private Socket socket;
    public ClientHandler(Socket socket, ClientDatabaseCommunication cdc) {
        this.cdc = cdc;
        this.socket = socket;
        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        new receiverThread().start();
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
                            login((UserInfo) message);
                        } else {
                            createNewUser((UserInfo) message);
                        }
                        
                    }
                    else if (message instanceof String) {
                        System.out.println(message);
//                        oos.writeObject("hello world");
//                        oos.flush();
                    } else if (message instanceof Email) {
                        checkEmail((Email) message);
                    }
                }
            } catch(IOException e){
                throw new RuntimeException(e);
            } catch(ClassNotFoundException e){
                throw new RuntimeException(e);
            }

        }
    }

    private void createNewUser(UserInfo userInfo) {
        cdc.newUser(userInfo);
        try {
            oos.writeObject(userInfo);
            oos.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void checkEmail(Email email) {
        email.setRegistered(cdc.checkEmail(email.getEmail()));
        try {
            oos.writeObject(email);
            oos.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void login(UserInfo message) {
        try {
            oos.writeObject(cdc.checkUserInfo(message));
            oos.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
