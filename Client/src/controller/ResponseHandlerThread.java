package controller;

import serializable.*;

import java.util.List;
import java.util.Map;

/**
 * 用于监听从服务器返回的Response
 */
public class ResponseHandlerThread implements Runnable {
    @Override
    public void run() {
        while (ClientManager.socket.isConnected()){
            Response response = ClientManager.readResponse();
            TransmissionType type = response.type;
            ResponseStatus status = response.status;
            if(status != ResponseStatus.SUCCESS && response.shortMessage != null){
                System.out.println(status.name() + " " + response.shortMessage);
            } else {
                if(response.shortMessage != null){
                    System.out.println(response.shortMessage);
                }
                switch (type){
                    case CHAT:
                    case GROUP_CHAT:
                        handleChat(response);
                        break;
                    case LOGOUT:
                        handleLogout(response);
                        break;
                    case SEARCH:
                        handleSearch(response);
                        break;
                    case SEARCH_GROUP:
                        handleSearchGroup(response);
                        break;
                    case CREATE_GROUP:
                        handleCreateGroup(response);
                        break;
                    case INIT:
                        handleInitialize(response);
                        break;
                }
            }
        }
    }

    private void handleInitialize(Response response){
        Map<String, User> userHistory = (Map<String, User>)response.getAttribute("userHistory");
        Map<String, Group> groupHistory = (Map<String, Group>) response.getAttribute("groupHistory");
        if(userHistory.size() != 0){
            System.out.println(userHistory);
        }else {
            System.out.println("没有历史私聊用户");
        }
        if(groupHistory.size() != 0){
            System.out.println(groupHistory);
        } else {
            System.out.println("没有历史群聊");
        }

    }

    private void handleChat(Response response){
        Message message = (Message) response.getAttribute("message");
        List<Message> messages = (List<Message>) response.getAttribute("messages");
        if(messages != null){
            messages.forEach(System.out::println);
        }
        if(message != null){
            System.out.println(message);
        }
    }

    private void handleLogout(Response response){
        ClientManager.getInstance().setCurrentUser(null);
    }


    private void handleSearch(Response response){
        User targetUser = (User) response.getAttribute("user");
        System.out.println(targetUser);
    }

    private void handleSearchGroup(Response response){
        System.out.println(response.getAttribute("group"));
    }

    private void handleCreateGroup(Response response){
        System.out.println(response.getAttribute("group"));
    }

}
