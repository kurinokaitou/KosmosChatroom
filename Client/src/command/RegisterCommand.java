package command;

import controller.ClientManager;
import serializable.Request;
import serializable.Response;
import serializable.TransmissionType;

import java.io.IOException;

public class RegisterCommand extends BaseCommand{
    private String userName;
    public String password;
    public RegisterCommand(String... args){
        if(args.length == 3){
            isValidAttrs = true;
            this.userName = args[1];
            this.password = args[2];
        } else {
            System.out.println("命令参数不符合要求！");
        }
    }

    @Override
    public void execute() {
        if(!isValidAttrs) return;
        Request request = new Request(TransmissionType.REGISTER);
        request.setAttribute("userName", userName);
        request.setAttribute("password", password);
        Response response = ClientManager.sendRequestWithResponse(request);
        System.out.println(response.shortMessage);
    }
}
