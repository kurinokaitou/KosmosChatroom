import command.BaseCommand;
import command.ShowAllUserCommand;
import connection.ConnectionListenerThread;
import controller.ServerManager;
import controller.UserManager;

import java.io.IOException;
import java.util.Scanner;

public class ServerMain {
    public static void main(String[] args){
        Thread monitorThread = new Thread(new ConnectionListenerThread());
        monitorThread.setDaemon(true);
        monitorThread.start();
        System.out.println("KosmosChatroom 服务器已启动");
        handleCommandLine();
        System.out.println("KosmosChatroom 服务器已关闭");
        System.exit(0);
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
                case "showAllUser":
                    command = new ShowAllUserCommand(argsAttr);
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
        String name = ServerManager.config.getProperty("database");
        try {
            UserManager.getInstance().saveAllUser(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

