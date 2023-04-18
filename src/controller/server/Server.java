package controller.server;

import database.user.DB_user;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Server extends Thread{
    private ServerSocket serverSocket;
    private int port;
    private DB_user dBUser;
    private ArrayList<ClientHandler> clients;

    public Server(int port) {
        this.port = port;
        dBUser = new DB_user();
        clients = new ArrayList<>();
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                clients.add(new ClientHandler(serverSocket.accept(), dBUser));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public static void main(String[] args) {
        new Server(700);
    }
}
