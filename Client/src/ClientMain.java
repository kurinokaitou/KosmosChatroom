import controller.ClientManager;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientMain {
    public static void main(String[] args){
        ClientManager manager = ClientManager.getInstance();
        connectToServer();
    }

    private static void connectToServer() {
        String ip = ClientManager.config.getProperty("ip");
        int port = Integer.parseInt(ClientManager.config.getProperty("port"));
        try {
            ClientManager.socket = new Socket(ip, port);
            System.out.println("成功连接到服务器！");
            try {
                ClientManager.objectInputStream = new ObjectInputStream(ClientManager.socket.getInputStream());
                ClientManager.objectOutputStream = new ObjectOutputStream(ClientManager.socket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("网络故障，未连接到服务器！");
            e.printStackTrace();
        }
    }
}
