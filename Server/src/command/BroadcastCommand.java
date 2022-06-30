package command;

import controller.ServerManager;
import serializable.Message;

public class BroadcastCommand extends BaseCommand{
    private Message message;
    public BroadcastCommand(String... args){
        if(args.length == 2){
            isValidAttrs = true;
            message = new Message(ServerManager.admin, "[系统消息]"+args[1]);
        } else {
            System.out.println("命令参数不符合要求！");
        }
    }
    @Override
    public void execute() {
        if(!isValidAttrs) return;
        ServerManager.getInstance().broadcastAllClient(message);
    }
}
