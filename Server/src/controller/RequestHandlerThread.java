package controller;

import connection.ConnectedClient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RequestHandlerThread implements Runnable {
    private final Socket socket;
    private final ConnectedClient client;
    private boolean stopListening;

    public RequestHandlerThread(Socket socket, ConnectedClient connectedClient){
        this.socket = socket;
        this.client = connectedClient;
    }
    @Override
    public void run() {
        try {
            client.setObjectInputStream(new ObjectInputStream(socket.getInputStream()));
            client.setObjectOutputStream(new ObjectOutputStream(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        while(!stopListening){
            // TODO: handle client behaviour
        }
    }
}
