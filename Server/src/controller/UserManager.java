package controller;

import serializable.User;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserManager {
    private final Map<Integer, User> userMap;
    private static UserManager instance;
    private UserManager(){
        userMap = new HashMap<>();
    }

    public static UserManager getInstance(){
        if(instance == null){
            instance = new UserManager();
        }
        return instance;
    }

    public void loadAllUser(String fileName) throws IOException {
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(fileName));
            List<User>list = (List<User>)objectInputStream.readObject();
            list.forEach((ele)-> userMap.put(ele.getUserId(), ele));
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
        userMap.values().forEach(System.out::println);
    }

    public void initUsers(String fileName){
        User user1 = new User("Bob", "123", 1);
        User user2 = new User("Alice", "123", 2);
        User user3 = new User("Mary", "123", 3);
        userMap.put(1, user1);
        userMap.put(2, user2);
        userMap.put(3, user3);
        try {
            saveAllUser(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
