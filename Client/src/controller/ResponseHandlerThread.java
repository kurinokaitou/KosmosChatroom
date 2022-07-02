package controller;

import command.ApplyInitCommand;
import serializable.*;
import ui.MainFrame;
import ui.UIFrames;
import ui.panel.ChatPanel;
import ui.panel.GroupChatPanel;

import java.util.List;
import java.util.Map;

/**
 * 用于监听从服务器返回的Response
 */
public class ResponseHandlerThread implements Runnable {
    @Override
    public void run() {
        while (ClientManager.socket.isConnected() && ClientManager.getInstance().isLogin()){
            Response response = ClientManager.readResponse();
            if(response == null){
                break;
            }
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
                        handleChat(response);
                        break;
                    case GROUP_CHAT:
                        handleGroupChat(response);
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
                }
            }
        }
    }


    private void handleGroupChat(Response response) {
        Message message = (Message) response.getAttribute("message");
        System.out.println(message);
        GroupChatPanel.getInstance().distributeMessage(message);
    }


    @SuppressWarnings("unchecked")
    private void handleChat(Response response){
        Message message = (Message) response.getAttribute("message");
        List<Message> messages = (List<Message>) response.getAttribute("messages");
        if(messages != null){
            messages.forEach(e->{
                System.out.println(message);
                ChatPanel.getInstance().distributeMessage(message);
            });
        }
        if(message != null){
            System.out.println(message);
            ChatPanel.getInstance().distributeMessage(message);
        }
    }

    private void handleLogout(Response response){
        ClientManager.getInstance().setCurrentUser(null);
    }


    private void handleSearch(Response response){
        User targetUser = (User) response.getAttribute("user");
        ClientManager.userHistory.put(targetUser.getName(), targetUser);
        ChatPanel.getInstance().addChatListItem(targetUser);
        System.out.println(targetUser);
    }

    private void handleSearchGroup(Response response){
        Group group = (Group) response.getAttribute("group");
        ClientManager.groupHistory.add(group);
        GroupChatPanel.getInstance().addChatListItem(group);
        System.out.println(group);
    }

    private void handleCreateGroup(Response response){
        Group group = (Group) response.getAttribute("group");
        ClientManager.groupHistory.add(group);
        GroupChatPanel.getInstance().addChatListItem(group);
        System.out.println(group);
    }
}
