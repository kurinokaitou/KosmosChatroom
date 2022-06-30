package command;

import controller.ClientManager;
import serializable.Message;
import serializable.Request;
import serializable.TransmissionType;

public class GroupCreateCommand extends BaseCommand{
    /**
     * 群聊创建命令
     * @param args 1参数
     */
    public GroupCreateCommand(String... args){
        if(args.length == 1){
            isValidAttrs = true;
        } else {
            System.out.println("命令参数不符合要求！");
        }
    }
    @Override
    public void execute() {
        if(!isValidAttrs) return;
        ClientManager.sendRequest(new Request(TransmissionType.CREATE_GROUP));
    }
}
