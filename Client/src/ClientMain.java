import command.*;
import controller.ClientManager;
import controller.CommandLineThread;
import ui.LoginFrame;


import javax.swing.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientMain {
    public static void main(String[] args){
        boolean success = connectToServer();
        initializeUI(success);
        if(success){
            System.out.println("开始运行");
            Thread thread = new Thread(new CommandLineThread(),"consoleCommandLine");
            thread.start();
            while (thread.isAlive() && !ClientManager.shouldShutdown){
                while(!ClientManager.commandQueue.isEmpty()){
                    BaseCommand command = ClientManager.commandQueue.poll();
                    command.execute();
                }
            }
        }
        System.exit(0);
    }

    private static boolean connectToServer() {
        ClientManager.getInstance();
        String ip = ClientManager.config.getProperty("ip");
        int port = Integer.parseInt(ClientManager.config.getProperty("port"));
        try {
            ClientManager.socket = new Socket(ip, port);
            ClientManager.objectOutputStream = new ObjectOutputStream(ClientManager.socket.getOutputStream());
            ClientManager.objectInputStream = new ObjectInputStream(ClientManager.socket.getInputStream());
            System.out.println("成功连接到服务器！");
            return true;
        } catch (Exception e) {
            System.out.println("网络故障，未连接到服务器！");
            e.printStackTrace();
            return false;
        }
    }

    private static void initializeUI(boolean success){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        new LoginFrame(success);
    }
}
