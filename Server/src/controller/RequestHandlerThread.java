package controller;

import connection.ConnectedClient;
import serializable.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RequestHandlerThread implements Runnable {
    private final Socket socket;
    private final ConnectedClient client;

    public RequestHandlerThread(Socket socket, ConnectedClient connectedClient){
        this.socket = socket;
        this.client = connectedClient;
    }
    @Override
    public void run() {
        initializeIOStream();
        while(true){
            Request request;
            try {
                request = (Request) client.readObject();
            } catch (IOException e){
                ServerManager.getInstance().disconnectClient(client);
                System.out.println("[用户断开连接]" + client.getClientAddress() +
                        " 当前有"+ServerManager.getInstance().getConnectedClientNum()+"个客户端连接");
                break;
            }
            // 未登录情况下拒绝所有业务请求
            if(!client.clientIsLogin()){
                if(request.type != TransmissionType.LOGIN && request.type != TransmissionType.REGISTER){
                    continue;
                }
            }
            switch (request.type){
                case CHAT:
                    handleChat(request);
                    break;
                case GROUP_CHAT:
                    handleGroupChat(request);
                    break;
                case LOGIN:
                    handleLogin(request);
                    break;
                case LOGOUT:
                    handleLogout(request);
                    break;
                case REGISTER:
                    handleRegister(request);
                    break;
                case SEARCH:
                    handleSearch(request);
                    break;
                case SEARCH_GROUP:
                    handleSearchGroup(request);
                    break;
                case CREATE_GROUP:
                    handleCreateGroup(request);
                default: break;
            }
        }
    }

    private void initializeIOStream(){
        try {
            client.setObjectInputStream(new ObjectInputStream(socket.getInputStream()));
            client.setObjectOutputStream(new ObjectOutputStream(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleChat(Request request){
        Message message = (Message) request.getAttribute("message");
        int targetUserId = message.toUserId;
        Response response = new Response(TransmissionType.CHAT);
        if(UserManager.getInstance().hasUserLogin(targetUserId)){
            User targetUser = UserManager.getInstance().getLoginUserById(targetUserId);
            response.status = ResponseStatus.SUCCESS;
            response.setAttribute("message", message);
            ServerManager.getInstance().notifyClient(response,targetUser);
        } else {
            UserManager.getInstance().retentUserMessage(targetUserId, message);
        }
    }

    private void handleGroupChat(Request request){
        Message message = (Message) request.getAttribute("message");
        String targetGroupCode = message.groupCode;
        Group group =  UserManager.getInstance().getGroupByCode(targetGroupCode);
        Response response = new Response(TransmissionType.GROUP_CHAT);
        if(group != null){
            response.setAttribute("message", message);
            ServerManager.getInstance().notifyGroupClients(response, group.getUserList());
        } else {
            response.shortMessage = "群组不存在";
            response.status = ResponseStatus.NOT_FOUND;
            client.writeObject(response);
        }
    }

    private void handleLogin(Request request){
        String userName = (String) request.getAttribute("userName");
        String password = (String) request.getAttribute("password");
        User user = UserManager.getInstance().getUserByName(userName);
        Response response = new Response(TransmissionType.LOGIN);
        if(user == null){
            response.shortMessage = "无此用户名";
            response.status = ResponseStatus.NOT_FOUND;
        } else {
            if(!user.getPassword().equals(password)){
                response.shortMessage = "密码不正确";
                response.status = ResponseStatus.FAILED;
            } else {
                if(client.clientIsLogin()){
                    response.shortMessage = "您已登录，不能重复登录";
                    response.status = ResponseStatus.FAILED;
                } else {
                    response.shortMessage = "您已登录";
                    response.setAttribute("user", user);
                    response.status = ResponseStatus.SUCCESS;
                    ServerManager.getInstance().ClientLogin(user, client.getClientAddress());
                }
            }
        }
        client.writeObject(response);
    }

    private void handleLogout(Request request){
        Response response = new Response(TransmissionType.LOGOUT);
        User user = client.getUser();
        client.setUser(null);
        UserManager.getInstance().userLogout(user);
        response.shortMessage = "您已登出，再见！";
        response.status = ResponseStatus.SUCCESS;
        client.writeObject(response);
    }

    private void handleRegister(Request request){
        String userName = (String) request.getAttribute("userName");
        String password = (String) request.getAttribute("password");
        Response response = new Response(TransmissionType.REGISTER);
        if(UserManager.getInstance().hasUser(userName)){
            response.shortMessage = "用户名已被使用";
            response.status = ResponseStatus.FAILED;
        } else {
            UserManager.getInstance().createNewUser(userName, password);
            response.shortMessage = "注册成功";
            response.status = ResponseStatus.SUCCESS;
        }
        client.writeObject(response);
    }

    private void handleSearch(Request request){
        String targetName = (String) request.getAttribute("userName");
        Response response = new Response(TransmissionType.SEARCH);
        User targetUser = UserManager.getInstance().getUserByName(targetName);
        if(targetUser == null){
            response.shortMessage = "未找到用户";
            response.status = ResponseStatus.FAILED;
        } else {
            response.shortMessage = "搜索成功";
            response.status = ResponseStatus.SUCCESS;
            response.setAttribute("user", targetUser);
        }
        client.writeObject(response);
    }

    public void handleSearchGroup(Request request){
        String groupCode = (String) request.getAttribute("groupCode");
        Group group = UserManager.getInstance().getGroupByCode(groupCode);
        Response response = new Response(TransmissionType.SEARCH_GROUP);
        response.shortMessage = "发现群组！";
        response.setAttribute("group", group);
        client.writeObject(response);
    }

    private void handleCreateGroup(Request request) {
        Group group =  UserManager.getInstance().createNewGroup(client.getUser().getName());
        Response response = new Response(TransmissionType.CREATE_GROUP);
        response.shortMessage = "群组创建成功";
        response.setAttribute("group", group);
        client.writeObject(response);
    }
}
