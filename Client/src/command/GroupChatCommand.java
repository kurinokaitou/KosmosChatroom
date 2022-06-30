package command;

import controller.ClientManager;
import serializable.Message;
import serializable.Request;
import serializable.TransmissionType;

public class GroupChatCommand extends BaseCommand{
    private Message message;

    public GroupChatCommand(String... args){
        if(args.length == 3){
            this.message = new Message(ClientManager.getInstance().getCurrentUser(),args[2]);
            this.message.setGroupMessage(args[1]);
            isValidAttrs = true;
        } else {
            System.out.println("命令参数不符合要求！");
        }
    }
    @Override
    public void execute() {
        if(!isValidAttrs)return;
        Request request = new Request(TransmissionType.GROUP_CHAT);
        request.setAttribute("message", message);
        ClientManager.sendRequest(request);
    }
}