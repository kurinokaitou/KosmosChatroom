package command;

import controller.ClientManager;
import serializable.Request;
import serializable.Response;
import serializable.TransmissionType;

public class LogoutCommand extends BaseCommand{
    public LogoutCommand(String... args){
        if(args.length == 1){
            isValidAttrs = true;
        } else {
            System.out.println("命令参数不符合要求！");
        }
    }
    @Override
    public void execute() {
        if(!isValidAttrs)return;
        Request request = new Request(TransmissionType.LOGOUT);
        ClientManager.sendRequest(request);
    }
}
