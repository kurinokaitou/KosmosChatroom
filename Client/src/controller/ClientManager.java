package controller;

import serializable.User;

import java.io.*;
import java.net.Socket;
import java.util.Properties;

public class ClientManager {
    public static Properties config;
    public static Socket socket;

    public static ObjectInputStream objectInputStream = null;
    public static ObjectOutputStream objectOutputStream = null;
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
}
