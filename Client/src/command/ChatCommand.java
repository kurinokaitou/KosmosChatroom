package command;

import controller.ClientManager;
import jdk.jfr.Frequency;
import serializable.Message;
import serializable.Request;
import serializable.TransmissionType;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatCommand extends BaseCommand{
    private Message message;

    /**
     * 创建发送私聊信息
     * @param args 3参数
     */
    public ChatCommand(String... args){
        if(args.length == 3){
            int userId;
            try {
                userId = Integer.parseInt(args[1]);
            } catch (NumberFormatException e){
                System.out.println("请输入用户ID！");
                return;
            }
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            this.message = new Message(ClientManager.getInstance().getCurrentUser(),args[2], format.format(new Date()));
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
