package command;

import controller.ClientManager;
import serializable.Group;
import serializable.Message;
import serializable.Request;
import serializable.TransmissionType;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GroupChatCommand extends BaseCommand{
    private Message message;

    /**
     * 发送群聊信息
     * @param args 3参数
     */
    public GroupChatCommand(String... args){
        super();
        if(args.length == 3){
            this.message = new Message(ClientManager.getInstance().getCurrentUser(),args[2]);
            String groupName = null;
            for(Group group: ClientManager.groupHistory){
                if(group.getGroupCode().equals(args[1])){
                    groupName = group.getGroupName();
                }
            }
            if(groupName != null){
                this.message.setGroupMessage(args[1], groupName);
                isValidAttrs = true;
            } else {
                System.out.println("历史群聊中不存在群号");
            }
        } else {
            System.out.println("命令参数不符合要求！");
        }
    }

    public GroupChatCommand(Message message) {
        super();
        this.message = message;
        isValidAttrs = true;
    }

    @Override
    public void execute() {
        if(!isValidAttrs)return;
        Request request = new Request(TransmissionType.GROUP_CHAT);
        request.setAttribute("message", message);
        ClientManager.sendRequest(request);
    }
}
