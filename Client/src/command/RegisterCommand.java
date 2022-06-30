package command;

import controller.ClientManager;
import serializable.Request;
import serializable.Response;
import serializable.TransmissionType;
import serializable.User;

public class RegisterCommand extends BaseCommand{
    private String userName;
    public String password;
    public RegisterCommand(String... args){
        if(ClientManager.getInstance().getCurrentUser() == null){
            if(args.length == 3){
                isValidAttrs = true;
                this.userName = args[1];
                this.password = args[2];
            } else {
                System.out.println("命令参数不符合要求！");
            }
        } else {
            System.out.println("登录状态下不能注册新账户！");
        }

    }

    @Override
    public void execute() {
        if(!isValidAttrs) return;
        Request request = new Request(TransmissionType.REGISTER);
        request.setAttribute("userName", userName);
        request.setAttribute("password", password);
        Response response = ClientManager.sendRequestWithResponse(request);
        ClientManager.getInstance().setCurrentUser((User) response.getAttribute("user"));
        System.out.println(response.shortMessage);
    }
}
