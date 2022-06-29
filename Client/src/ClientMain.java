import command.BaseCommand;
import command.LoginCommand;
import command.RegisterCommand;
import controller.ClientManager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args){
        ClientManager manager = ClientManager.getInstance();
        connectToServer();
        handleCommandLine();
    }

    private static void connectToServer() {
        String ip = ClientManager.config.getProperty("ip");
        int port = Integer.parseInt(ClientManager.config.getProperty("port"));
        try {
            ClientManager.socket = new Socket(ip, port);
            ClientManager.objectOutputStream = new ObjectOutputStream(ClientManager.socket.getOutputStream());
            ClientManager.objectInputStream = new ObjectInputStream(ClientManager.socket.getInputStream());
            System.out.println("成功连接到服务器！");
        } catch (Exception e) {
            System.out.println("网络故障，未连接到服务器！");
            e.printStackTrace();
        }
    }

    private static void handleCommandLine(){
        Scanner in = new Scanner(System.in);
        String argStr;
        while(true) {
            argStr = in.nextLine();
            argStr = argStr.trim();
            if (argStr.equals("shutdown")) {
                shutdown();
                break;
            }
            String[] argsAttr = argStr.split(" ", -1);
            BaseCommand command;
            switch (argsAttr[0]){
                case "login":
                    command = new LoginCommand(argsAttr);
                    break;
                case "register":
                    command = new RegisterCommand(argsAttr);
                    break;
                default:
                    command = null;
            }
            if(command != null){
                command.execute();
            }
        }
    }

    private static void shutdown(){
        try {
            ClientManager.socket.shutdownInput();
            ClientManager.socket.shutdownOutput();
            ClientManager.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}