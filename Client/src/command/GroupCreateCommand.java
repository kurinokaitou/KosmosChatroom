package command;

import controller.ClientManager;
import serializable.Request;
import serializable.TransmissionType;

public class GroupCreateCommand extends BaseCommand{
    private String groupName;
    /**
     * 群聊创建命令
     * @param args 1参数
     */
    public GroupCreateCommand(String... args){
        if(args.length == 2){
            isValidAttrs = true;
            this.groupName = args[1];
        } else {
            System.out.println("命令参数不符合要求！");
        }
    }
    @Override
    public void execute() {
        if(!isValidAttrs) return;
        Request request = new Request(TransmissionType.CREATE_GROUP);
        request.setAttribute("groupName", groupName);
        ClientManager.sendRequest(request);
    }
}
