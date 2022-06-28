package controller;

import connection.ConnectedClient;
import serializable.User;

import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ServerManager {
    public static ServerSocket serverSocket;
    public static Properties config;
    private final Map<String, ConnectedClient> connectedClientMap;
    private static ServerManager instance = null;

    public int getConnectedClientNum(){
        return connectedClientMap.size();
    }

    public void connectClient(ConnectedClient client){
        String address = client.getClientAddress();
        if(!connectedClientMap.containsKey(address)){
            connectedClientMap.put(address, client);
        } else {
            int time = 1;
            String newAddress = address + "_" + time;
            while(connectedClientMap.containsKey(newAddress)){
                time++;
                newAddress = address + "_" + time;
            }
            connectedClientMap.put(newAddress, client);
        }
    }

    public void disconnectClient(ConnectedClient client){
        String address = client.getClientAddress();
        connectedClientMap.remove(address);
    }

    private ServerManager(){
        config = new Properties();
        connectedClientMap = new HashMap<>();
        try {
            FileReader reader = new FileReader("config.properties");
            config.load(reader);
            serverSocket = new ServerSocket(Integer.parseInt(config.getProperty("port")));
            String databasePath = config.getProperty("database");
            //UserManager.getInstance().initUsers(databasePath);
            UserManager.getInstance().loadAllUser(databasePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ServerManager getInstance(){
        if(instance == null){
            instance = new ServerManager();
        }
        return instance;
    }
}
