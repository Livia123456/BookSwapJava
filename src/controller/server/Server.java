package controller.server;

import controller.ClientDatabaseCommunication;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Server extends Thread{
    private ServerSocket serverSocket;
    private int port;
    private ClientDatabaseCommunication cdc;

    private ArrayList<ClientHandler> clients;

    public Server(int port) {
        this.port = port;
        cdc = new ClientDatabaseCommunication();
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
                clients.add(new ClientHandler(serverSocket.accept(), cdc));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public static void main(String[] args) {
        new Server(700);
    }
}
