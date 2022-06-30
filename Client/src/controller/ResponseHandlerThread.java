package controller;

import serializable.*;

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
            if(status != ResponseStatus.SUCCESS){
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
                }
            }
        }
    }

    private void handleChat(Response response){
        Message message = (Message) response.getAttribute("message");
        System.out.println(message.content);
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
