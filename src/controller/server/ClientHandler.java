package controller.server;

import model.UserInfo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler extends Thread{
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    public ClientHandler(Socket socket) {
        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        new receiverThread().start();
    }

    private class receiverThread extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    Object message = ois.readObject();
                    if (message instanceof UserInfo) {

                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private class senderThread extends Thread {

    }
}
