package controller;

import serializable.Group;
import serializable.Message;
import serializable.User;
import serializable.UserStorage;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

public class UserManager {
    private final Map<String, UserStorage> userMap;    // 根据用户名存储的所有注册用户
    private final Map<Integer, User> idMap;
    private final Set<Integer> connectedUserSet;     // 根据用户ID存储的所有登录用户
    private final Map<Integer, List<Message>> userRetentMessagesMap;
    private static UserManager instance;
    private static int newUserId = 1;

    private final Map<String, Group> groupMap;

    private UserManager(){
        userMap = new ConcurrentHashMap<>();
        connectedUserSet = new ConcurrentSkipListSet<>();
        userRetentMessagesMap = new ConcurrentHashMap<>();
        groupMap = new ConcurrentHashMap<>();
        idMap = new ConcurrentHashMap<>();
    }

    public static UserManager getInstance(){
        if(instance == null){
            instance = new UserManager();
        }
        return instance;
    }

    /**
     * 获取用户的历史群聊
     * @param userName 用户
     * @return 历史群聊表
     */
    public Map<String, Group> getUserHistoryGroup(String userName){
        return userMap.get(userName).groupHistoryMap;
    }

    /**
     * 获取用户的历史私聊用户
     * @param userName 用户
     * @return 历史私聊用户表
     */
    public Map<String, User> getUserHistoryUser(String userName){
        return userMap.get(userName).userHistoryMap;
    }

    /**
     * 将历史群聊添加到用户的存储中
     * @param group 历史群聊
     * @param userName 用户名
     */
    public void addGroupToUserHistory(Group group, String userName){
        Map<String, Group> history = userMap.get(userName).groupHistoryMap;
        if(!history.containsKey(group.getGroupCode())){
            history.put(group.getGroupCode(), group);
        }
    }

    /**
     * 将历史私聊用户添加到用户的存储中
     * @param user 历史私聊用户
     * @param userName 用户名
     */
    public void addUserToUserHistory(User user, String userName){
        Map<String, User> history = userMap.get(userName).userHistoryMap;
        if(!history.containsKey(user.getName())){
            userMap.get(userName).userHistoryMap.put(user.getName(), user);
        }
    }

    /**
     * 获取某用户未登录时接受的暂存信息
     * @param userId 用户Id
     * @return 返回信息列表
     */
    public List<Message> getUserRetentMessages(int userId){
        List<Message> messages = userRetentMessagesMap.get(userId);
        userRetentMessagesMap.remove(userId);
        return messages;
    }

    /**
     * 用户未登录时接受暂存信息
     * @param userId 用户Id
     * @param message 接受信息
     */
    public void retentUserMessage(int userId, Message message){
        if(userRetentMessagesMap.containsKey(userId)){
            userRetentMessagesMap.get(userId).add(message);
        } else {
            List<Message> newMessageList = new LinkedList<>();
            newMessageList.add(message);
            userRetentMessagesMap.put(userId, newMessageList);
        }
    }

    /**
     * 创建新用户
     * @param name 姓名
     * @param password 密码
     */
    public void createNewUser(String name, String password){
        UserStorage userStorage = new UserStorage(new User(name, password, newUserId));
        userMap.put(name, userStorage);
        idMap.put(newUserId, userStorage.user);
        newUserId++;
    }

    /**
     * 用户表中是否有此用户
     * @param name 姓名
     * @return 是否有
     */
    public boolean hasUser(String name){
        return userMap.containsKey(name);
    }

    /**
     * 根据用户姓名在用户表中获取用户
     * @param name 姓名
     * @return 用户
     */
    public User getUserByName(String name){
        if(userMap.containsKey(name)){
            return userMap.get(name).user;
        } else return null;

    }

    /**
     * 根据用户id在用户表中获取用户
     * @param userId id
     * @return 用户
     */
    public User getUserById(int userId){
        return idMap.get(userId);
    }

    /**
     * 用户是否登录
     * @param userId 用户Id
     * @return 是否登录
     */
    public boolean hasUserLogin(int userId){
        return connectedUserSet.contains(userId);
    }


    /**
     * 用户登录
     * @param user 用户
     */
    public void userLogin(User user){
        connectedUserSet.add(user.getUserId());
    }

    /**
     * 用户登出
     */
    public void userLogout(User user){
        connectedUserSet.remove(user.getUserId());
    }

    /**
     * 从数据库中读取所有用户到内存
     * @param fileName 数据库名称
     * @throws IOException 读取异常
     */
    @SuppressWarnings("unchecked")
    public void loadAllUser(String fileName) throws IOException {
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(fileName));
            List<UserStorage> list = (List<UserStorage>)objectInputStream.readObject();
            list.forEach((ele)-> {
                if(ele.userHistoryMap == null){
                    ele.userHistoryMap = new HashMap<>();
                }
                if(ele.groupHistoryMap == null){
                    ele.groupHistoryMap = new HashMap<>();
                }
                userMap.put(ele.user.getName(), ele);
                idMap.put(ele.user.getUserId(), ele.user);
                newUserId = Math.max(newUserId, ele.user.getUserId());
            });
            newUserId++;
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            assert objectInputStream != null;
            objectInputStream.close();
        }
    }

    /**
     * 保存所有用户到数据库
     * @param fileName 数据库名称
     * @throws IOException 写入异常
     */
    public void saveAllUser(String fileName) throws IOException {
        ObjectOutputStream objectOutputStream = null;
        try {
            File file = new File(fileName);
            boolean success = true;
            if(!file.exists()){
                success = file.createNewFile();
            }
            if(success){
                objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
                List<UserStorage> userList = new ArrayList<>(userMap.values());
                objectOutputStream.writeObject(userList);
                objectOutputStream.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            assert  objectOutputStream != null;
            objectOutputStream.close();
        }
    }

    /**
     * 打印所有用户
     */
    public void showAllUser(){
        if(userMap.size() == 0){
            System.out.println("系统内暂无用户");
        } else {
            userMap.values().forEach(System.out::println);
        }
    }

    /**
     * 打印所有登录用户
     */
    public void showConnectedUser(){
        if(connectedUserSet.size() == 0){
            System.out.println("系统内暂无登录用户");
        } else {
            connectedUserSet.forEach(id-> System.out.println(idMap.get(id)));
        }
    }

    /**
     * 打印所有群组信息
     */
    public void showAllGroup(){
        if(groupMap.size() == 0){
            System.out.println("系统内暂无群组");
        } else {
            groupMap.values().forEach(System.out::println);
        }
    }

    /**
     * 从数据库中读取所有群组到内存
     * @param fileName 数据库名称
     * @throws IOException 读取异常
     */
    @SuppressWarnings("unchecked")
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

    /**
     * 保存所有群组信息到数据库
     * @param fileName 数据库名称
     * @throws IOException 写入异常
     */
    public void saveAllGroup(String fileName) throws IOException {
        ObjectOutputStream objectOutputStream = null;
        try {
            File file = new File(fileName);
            boolean success = true;
            if(!file.exists()){
                success = file.createNewFile();
            }
            if(success){
                objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
                List<Group> userList = new ArrayList<>(groupMap.values());
                objectOutputStream.writeObject(userList);
                objectOutputStream.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            assert  objectOutputStream != null;
            objectOutputStream.close();
        }
    }

    /**
     * 创建新的群组，并生成一个随机的群号
     * @param createUserName 创建者名称
     * @return 群组
     */
    public Group createNewGroup(String createUserName, String groupName){
        String code = generateGroupCode();
        Group group = new Group(code, groupName);
        group.addUser(userMap.get(createUserName).user);
        groupMap.put(code, group);
        return group;
    }

    /**
     * 根据群号获取群组
     * @param groupCode 群号
     * @return 群组
     */
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
        createNewGroup("Alice", "AliceGroup");
        createNewGroup("Bob", "BobGroup");
    }
//
//    public void initUserStorage(){
//        createNewUser("Bob", "123");
//        createNewUser("Alice", "123");
//        createNewUser("Mary", "123");
//    }
}
