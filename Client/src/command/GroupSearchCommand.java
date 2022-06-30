package command;

import controller.ClientManager;
import serializable.Request;
import serializable.TransmissionType;

public class GroupSearchCommand extends BaseCommand{
    private String targetGroupCode;
    public GroupSearchCommand(String... args){
        if(args.length == 2){
            isValidAttrs = true;
            this.targetGroupCode = args[1];
        } else {
            System.out.println("命令参数不符合要求！");
        }
    }

    @Override
    public void execute() {
        if(!isValidAttrs) return;
        Request request = new Request(TransmissionType.SEARCH_GROUP);
        request.setAttribute("groupCode", targetGroupCode);
        ClientManager.sendRequest(request);
    }
}
