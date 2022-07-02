package command;

import controller.ClientManager;
import serializable.*;

public class ChatCommand extends BaseCommand{
    private Message message;

    /**
     * 创建发送私聊信息
     * @param args 3参数
     */
    public ChatCommand(String... args){
        super();
        if(args.length == 3){
            int userId;
            try {
                userId = Integer.parseInt(args[1]);
            } catch (NumberFormatException e){
                System.out.println("请输入用户名！");
                return;
            }

            String userName = null;
            for(User user : ClientManager.userHistory.values()){
                if(userId == user.getUserId()){
                    userName = user.getName();
                }
            }
            if(userName != null){
                this.message = new Message(ClientManager.getInstance().getCurrentUser(),args[2]);
                this.message.setUserMessage(userId, userName);
                isValidAttrs = true;
            } else {
                System.out.println("找不到此用户");
            }
        } else {
            System.out.println("命令参数不符合要求！");
        }
    }

    /**
     * 发送信息
     * @param message 信息
     */
    public ChatCommand(Message message){
        super();
        this.message = message;
        isValidAttrs = true;
    }

    @Override
    public void execute() {
        if(!isValidAttrs)return;
        Request request = new Request(TransmissionType.CHAT);
        request.setAttribute("message", message);
        ClientManager.sendRequest(request);
    }
}
