package controller.server;

import database.user.DatabaseUser;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;


public class Server extends Thread{
    private ServerSocket serverSocket;
    private int port;
    private DatabaseUser dbUser;
    private ArrayList<ClientHandler> clients;


    public Server(int port) {

        this.port = port;
        dbUser = new DatabaseUser();
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
                clients.add(new ClientHandler(serverSocket.accept(), dbUser));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
        new Server(700);
    }
}
