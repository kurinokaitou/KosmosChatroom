package controller;

import command.BaseCommand;
import serializable.Group;
import serializable.Request;
import serializable.Response;
import serializable.User;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ClientManager {
    public static Properties config;
    public static Socket socket;

    public static ObjectInputStream objectInputStream;
    public static ObjectOutputStream objectOutputStream;
    public static Queue<BaseCommand> commandQueue;
    public static boolean shouldShutdown = false;

    private User currentUser;
    private static ClientManager instance = null;

    public static Map<String, User> userHistory;
    public static List<Group> groupHistory;

    private ClientManager(){
        config = new Properties();
        commandQueue = new ConcurrentLinkedQueue<>();
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

    /**
     * 判断客户端是否登录
     * @return 是否登录
     */
    public boolean isLogin(){
        return currentUser != null;
    }

    /**
     * 将链接切断并通知结束主线程
     */
    public static void shutdown(){
        try {
            socket.shutdownInput();
            socket.shutdownOutput();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            shouldShutdown = true;
        }
    }

    /**
     * 获取当前用户
     * @return 当前user
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * 设置当前用户
     * @param currentUser 当前用户
     */
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * 向服务器发送请请求，并获得回复
     * @param request 请求
     * @return response 回复
     */
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

    /**
     * 向服务器发送请请求，无回复
     * @param request 请求
     */
    public static void sendRequest(Request request){
        try {
            objectOutputStream.writeObject(request);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从服务器读取回复
     * @return response 回复
     */
    public static Response readResponse(){
        Response response = null;
        try {
            synchronized (objectInputStream){
                response = (Response) objectInputStream.readObject();
            }
        } catch (SocketException e){
            System.out.println("Socket关闭");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return response;
    }
}
