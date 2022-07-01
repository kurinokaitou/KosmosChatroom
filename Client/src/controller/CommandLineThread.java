package controller;

import command.*;
import java.util.Scanner;

public class CommandLineThread implements Runnable{
    @Override
    public void run() {
        Scanner in = new Scanner(System.in);
        String argStr;
        while(true) {
            argStr = in.nextLine();
            argStr = argStr.trim();
            BaseCommand command;
            String[] argsAttr = argStr.split(" ", -1);
            if (argStr.equals("shutdown")) {
                ClientManager.commandQueue.offer(new ShutdownCommand(argsAttr));
                break;
            }

            switch (argsAttr[0]){
                case "login":
                    command = new LoginCommand(argsAttr);
                    break;
                case "register":
                    command = new RegisterCommand(argsAttr);
                    break;
                case "logout":
                    command = new LogoutCommand(argsAttr);
                    break;
                case "chat":
                    command = new ChatCommand(argsAttr);
                    break;
                case "search":
                    command = new SearchCommand(argsAttr);
                    break;
                case "groupSearch":
                    command = new GroupSearchCommand(argsAttr);
                    break;
                case "groupChat":
                    command = new GroupChatCommand(argsAttr);
                    break;
                case "groupCreate":
                    command = new GroupCreateCommand(argsAttr);
                    break;
                default:
                    command = null;
            }
            if(command != null){
                ClientManager.commandQueue.offer(command);
            } else {
                System.out.println("命令不存在!");
            }
        }
    }
}
