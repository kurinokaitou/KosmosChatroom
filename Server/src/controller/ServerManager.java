package controller;

import connection.ConnectedClient;
import serializable.Response;
import serializable.User;

import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class ServerManager {
    public static ServerSocket serverSocket;
    public static Properties config;
    private final Map<String, ConnectedClient> connectedClientMap;
    private static ServerManager instance = null;

    /**
     * 获取连接数
     * @return 连接数
     */
    public int getConnectedClientNum(){
        return connectedClientMap.size();
    }

    /**
     * 连接一个客户端并记录到map中，相同ip的客户端将加数字下标区分
     * @param client 连接的客户端
     */
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
            client.setClientAddress(newAddress);
            connectedClientMap.put(newAddress, client);
        }
    }

    /**
     * 在用户关闭客户端时关闭这个连接
     * @param client 关闭的客户端
     */
    public void disconnectClient(ConnectedClient client){
        String address = client.getClientAddress();
        connectedClientMap.remove(address);
    }

    /**
     * 将用户端的临时连接转为登录连接，更改clientAddress为用户名
     * @param user 登录用户
     * @param clientAddress 原本的clientAddress
     */
    public void ClientLogin(User user, String clientAddress){
        UserManager.getInstance().userLogin(user);
        ConnectedClient client = connectedClientMap.get(clientAddress);
        if(client != null){
            client.setUser(user);
            client.setClientAddress(user.getName());
            connectedClientMap.remove(clientAddress);
            connectedClientMap.put(user.getName(), client);
        }
    }

    /**
     * 系统通知所有用户
     * @param response 返回信息
     */
    public void broadcastAllClient(Response response){
        connectedClientMap.values().forEach(client -> client.writeObject(response));
    }

    /**
     * 返回信息给一个用户
     * @param response 返回信息
     * @param targetUser 目标用户
     */
    public void notifyClient(Response response, User targetUser){
        // 已经登录的客户端的key已经改为了用户名
        ConnectedClient targetClient = connectedClientMap.get(targetUser.getName());
        targetClient.writeObject(response);
    }

    /**
     * 返回信息给一组用户
     * @param response 返回信息
     * @param userGroupList 目标用户组
     */
    public void notifyGroupClients(Response response, List<User> userGroupList){
        userGroupList.forEach(user -> {
            ConnectedClient targetClient = connectedClientMap.get(user.getName());
            targetClient.writeObject(response);
        });
    }


    private ServerManager(){
        config = new Properties();
        connectedClientMap = new ConcurrentHashMap<>();
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
