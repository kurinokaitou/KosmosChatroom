package controller;

import serializable.Message;
import serializable.User;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserManager {
    private final Map<String, User> userMap;    // 根据用户名存储的所有注册用户
    private final Map<Integer, User> connectedUserMap;     // 根据用户ID存储的所有登录用户
    private final Map<Integer, List<Message>> userRetentMessageListMap;
    private static UserManager instance;
    private static int newUserId = 0;
    private UserManager(){
        userMap = new ConcurrentHashMap<>();
        connectedUserMap = new ConcurrentHashMap<>();
        userRetentMessageListMap = new ConcurrentHashMap<>();
    }

    public static UserManager getInstance(){
        if(instance == null){
            instance = new UserManager();
        }
        return instance;
    }

    public List<Message> getUserRetentMessages(int userId){
        List<Message> messages = userRetentMessageListMap.get(userId);
        userRetentMessageListMap.remove(userId);
        return messages;
    }

    public void retentUserMessage(int userId, Message message){
        if(userRetentMessageListMap.containsKey(userId)){
            userRetentMessageListMap.get(userId).add(message);
        } else {
            List<Message> newMessageList = new LinkedList<>();
            newMessageList.add(message);
            userRetentMessageListMap.put(userId, newMessageList);
        }
    }

    public void createNewUser(String name, String password){
        userMap.put(name, new User(name, password, newUserId++));
    }

    public boolean hasUser(String name){
        return userMap.containsKey(name);
    }

    public boolean hasUserLogin(int userId){
        return connectedUserMap.containsKey(userId);
    }

    public User getLoginUserById(int userId){
        return connectedUserMap.get(userId);
    }

    public User getUserByName(String name){
        return userMap.get(name);
    }

    public void userLogin(User user){
        connectedUserMap.put(user.getUserId(), user);
    }

    public void userLogout(User user){
        connectedUserMap.remove(user.getUserId());
    }

    public void loadAllUser(String fileName) throws IOException {
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(fileName));
            List<User> list = (List<User>)objectInputStream.readObject();
            list.forEach((ele)-> {
                userMap.put(ele.getName(), ele);
                newUserId = Math.max(newUserId, ele.getUserId());
            });
            newUserId++;
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            assert objectInputStream != null;
            objectInputStream.close();
        }
    }

    public void saveAllUser(String fileName) throws IOException {
        ObjectOutputStream objectOutputStream = null;
        try {
            File file = new File(fileName);
            if(!file.exists()){
                boolean success = file.createNewFile();
            }
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
            List<User> userList = new ArrayList<>(userMap.values());
            objectOutputStream.writeObject(userList);
            objectOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            assert  objectOutputStream != null;
            objectOutputStream.close();
        }
    }

    public void showAllUser(){
        if(userMap.size() == 0){
            System.out.println("系统内暂无用户。");
        } else {
            userMap.values().forEach(System.out::println);
        }
    }

    public void showConnectedUser(){
        if(connectedUserMap.size() == 0){
            System.out.println("系统内暂无登录用户。");
        } else {
            connectedUserMap.values().forEach(System.out::println);
        }
    }

    public void initUsers(String fileName){
        User user1 = new User("Bob", "123", 1);
        User user2 = new User("Alice", "123", 2);
        User user3 = new User("Mary", "123", 3);
        userMap.put(user1.getName(), user1);
        userMap.put(user2.getName(), user2);
        userMap.put(user3.getName(), user3);
        try {
            saveAllUser(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
