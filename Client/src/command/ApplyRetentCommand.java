package command;

import controller.ClientManager;
import serializable.Request;
import serializable.TransmissionType;

public class ApplyRetentCommand extends BaseCommand{
    public ApplyRetentCommand(String... args){
        if(args.length == 1){
            isValidAttrs = true;
        } else {
            System.out.println("命令参数不符合要求！");
        }
    }
    @Override
    public void execute() {
        if(!isValidAttrs)return;
        Request request = new Request(TransmissionType.RETENT);
        ClientManager.sendRequest(request);
    }
}
