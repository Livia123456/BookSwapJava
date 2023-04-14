package controller.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Server extends Thread{
    private ServerSocket serverSocket;
    private int port;

    private ArrayList<ClientHandler> clients;

    public Server(int port) {
        this.port = port;
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
                clients.add(new ClientHandler(serverSocket.accept()));

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
