package controller;

import serializable.Request;
import serializable.Response;
import serializable.User;

import java.io.*;
import java.net.Socket;
import java.util.Properties;

public class ClientManager {
    public static Properties config;
    public static Socket socket;

    public static ObjectInputStream objectInputStream;
    public static ObjectOutputStream objectOutputStream;

    private User currentUser;
    private static ClientManager instance = null;

    private ClientManager(){
        config = new Properties();
        FileReader reader;
        try {
            reader = new FileReader("config.properties");
            config.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ClientManager getInstance(){
        if(instance == null){
            instance = new ClientManager();
        }
        return instance;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public static Response sendRequestWithResponse(Request request){
        Response response = new Response(request.type);
        try {
            objectOutputStream.writeObject(request);
            objectOutputStream.flush();
            synchronized (objectInputStream){
                response = (Response) objectInputStream.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return response;
    }

    public static void sendRequest(Request request){
        try {
            objectOutputStream.writeObject(request);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Response readResponse(){
        Response response = null;
        try {
            synchronized (objectInputStream){
                response = (Response) objectInputStream.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return response;
    }
}
