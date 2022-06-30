package controller;

import serializable.Group;
import serializable.Message;
import serializable.User;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class UserManager {
    private final Map<String, User> userMap;    // 根据用户名存储的所有注册用户
    private final Map<Integer, User> connectedUserMap;     // 根据用户ID存储的所有登录用户
    private final Map<Integer, List<Message>> userRetentMessagesMap;
    private static UserManager instance;
    private static int newUserId = 0;

    private final Map<String, Group> groupMap;

    private UserManager(){
        userMap = new ConcurrentHashMap<>();
        connectedUserMap = new ConcurrentHashMap<>();
        userRetentMessagesMap = new ConcurrentHashMap<>();
        groupMap = new ConcurrentHashMap<>();
    }

    public static UserManager getInstance(){
        if(instance == null){
            instance = new UserManager();
        }
        return instance;
    }

    public List<Message> getUserRetentMessages(int userId){
        List<Message> messages = userRetentMessagesMap.get(userId);
        userRetentMessagesMap.remove(userId);
        return messages;
    }

    public void retentUserMessage(int userId, Message message){
        if(userRetentMessagesMap.containsKey(userId)){
            userRetentMessagesMap.get(userId).add(message);
        } else {
            List<Message> newMessageList = new LinkedList<>();
            newMessageList.add(message);
            userRetentMessagesMap.put(userId, newMessageList);
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
            System.out.println("系统内暂无用户");
        } else {
            userMap.values().forEach(System.out::println);
        }
    }

    public void showConnectedUser(){
        if(connectedUserMap.size() == 0){
            System.out.println("系统内暂无登录用户");
        } else {
            connectedUserMap.values().forEach(System.out::println);
        }
    }

    public void showAllGroup(){
        if(groupMap.size() == 0){
            System.out.println("系统内暂无群组");
        } else {
            groupMap.values().forEach(System.out::println);
        }
    }

    public void loadAllGroup(String fileName) throws IOException {
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(fileName));
            List<Group> list = (List<Group>)objectInputStream.readObject();
            list.forEach(ele-> groupMap.put(ele.getGroupCode(), ele));
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            assert objectInputStream != null;
            objectInputStream.close();
        }
    }

    public void saveAllGroup(String fileName) throws IOException {
        ObjectOutputStream objectOutputStream = null;
        try {
            File file = new File(fileName);
            if(!file.exists()){
                boolean success = file.createNewFile();
            }
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
            List<Group> userList = new ArrayList<>(groupMap.values());
            objectOutputStream.writeObject(userList);
            objectOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            assert  objectOutputStream != null;
            objectOutputStream.close();
        }
    }

    public Group createNewGroup(String createUserName){
        String code = generateGroupCode();
        Group group = new Group(code);
        group.addUser(userMap.get(createUserName));
        groupMap.put(code, group);
        return group;
    }

    public Group getGroupByCode(String groupCode){
        return groupMap.get(groupCode);
    }

    private String generateGroupCode(){
        Random random = new Random();
        String code;
        do{
            StringBuilder codeBuilder = new StringBuilder();
            for(int i = 0; i < 4; i++){
                int index = random.nextInt(36);
                if(index <= 9){
                    codeBuilder.append(index);
                }else {
                    codeBuilder.append((char) ('a'+ index-10));
                }
            }
            code = codeBuilder.toString();
        }while (groupMap.containsKey(code));
        return code;
    }

    public void initGroups(){
        createNewGroup("Kurino");
        createNewGroup("Alice");
        createNewGroup("Bob");
    }
}
