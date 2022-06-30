package command;

import controller.ClientManager;
import jdk.jfr.Frequency;
import serializable.Message;
import serializable.Request;
import serializable.TransmissionType;

public class ChatCommand extends BaseCommand{
    private Message message;

    public ChatCommand(String... args){
        if(args.length == 3){
            int userId;
            try {
                userId = Integer.parseInt(args[1]);
            } catch (NumberFormatException e){
                System.out.println("请输入用户ID！");
                return;
            }
            this.message = new Message(ClientManager.getInstance().getCurrentUser(),args[2]);
            this.message.setUserMessage(userId);
            isValidAttrs = true;
        } else {
            System.out.println("命令参数不符合要求！");
        }
    }
    @Override
    public void execute() {
        if(!isValidAttrs)return;
        Request request = new Request(TransmissionType.CHAT);
        request.setAttribute("message", message);
        ClientManager.sendRequest(request);
    }
}
