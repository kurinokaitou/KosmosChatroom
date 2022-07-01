package command;

import controller.ClientManager;
import controller.ResponseHandlerThread;
import serializable.*;
import ui.MainFrame;
import ui.UIFrames;

import java.util.List;
import java.util.Map;

public class ApplyInitCommand extends BaseCommand{
    public ApplyInitCommand(String... args){
        if(args.length == 1){
            isValidAttrs = true;
        } else {
            System.out.println("命令参数不符合要求！");
        }
    }
    @Override
    public void execute() {
        if(!isValidAttrs)return;
        Request request = new Request(TransmissionType.INIT);
        response = ClientManager.sendRequestWithResponse(request);
        ClientManager.userHistory = (Map<String, User>)response.getAttribute("userHistory");
        ClientManager.groupHistory = (List<Group>) response.getAttribute("groupHistory");
        if(ClientManager.userHistory.size() != 0){
            System.out.println(ClientManager.userHistory);
        }else {
            System.out.println("没有历史私聊用户");
        }
        if(ClientManager.groupHistory.size() != 0){
            System.out.println(ClientManager.groupHistory);
        } else {
            System.out.println("没有历史群聊");
        }
        new Thread(new ResponseHandlerThread(), "ResponseHandlerThread").start();
        if(response.status == ResponseStatus.SUCCESS){
            UIFrames.mainFrame = new MainFrame();
            UIFrames.loginFrame.dispose();
        }
    }
}
