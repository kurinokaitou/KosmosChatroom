package controller;

import serializable.*;

/**
 * 用于监听从服务器返回的Response
 */
public class ResponseListenerThread implements Runnable {
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
                }
            }
        }
    }

    private void handleChat(Response response){
        Message message = (Message) response.getAttribute("message");
        System.out.println(message.content);
    }

    private void handleGroupChat(Response response){

    }

    private void handleLogout(Response response){
        ClientManager.getInstance().setCurrentUser(null);
    }


    private void handleSearch(Response response){

    }

    private void handleSearchGroup(Response response){

    }

}
