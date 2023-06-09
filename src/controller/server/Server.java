package controller.server;

import database.user.DatabaseUser;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;


/**
 * Main class for the server. Server has the connection to the systems' database.
 * Clients need to be connected to the server to access information stored in the database.
 * @author Livia Tengelin
 */

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

    /**
     * Listens for clients wanting to connect to the server.
     * When a client connects, ClientHandler instance gets created.
     * adds the new ClientHandler object to list of connected clients. 
     */
    @Override
    public void run() {

        while (true) {
            try {
                clients.add(new ClientHandler(serverSocket.accept(), this));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public ArrayList<ClientHandler> getClients() {
        return clients;
    }

    public static void main(String[] args) {
        new Server(700);
    }
}
