package command;

import controller.ClientManager;
import controller.ResponseListenerThread;
import serializable.Request;
import serializable.Response;
import serializable.TransmissionType;
import serializable.User;

public class LoginCommand extends BaseCommand{
    private String userName;
    public String password;
    public LoginCommand(String... args){
        if(ClientManager.getInstance().getCurrentUser() == null){
            if(args.length == 3){
                isValidAttrs = true;
                this.userName = args[1];
                this.password = args[2];
            } else {
                System.out.println("命令参数不符合要求！");
            }
        } else {
            System.out.println("您已登录，不能重复登录");
        }

    }

    @Override
    public void execute() {
        if(!isValidAttrs) return;
        Request request = new Request(TransmissionType.LOGIN);
        request.setAttribute("userName", userName);
        request.setAttribute("password", password);
        Response response = ClientManager.sendRequestWithResponse(request);
        ClientManager.getInstance().setCurrentUser((User) response.getAttribute("user"));
        System.out.println(response.shortMessage);
        new Thread(new ResponseListenerThread()).start();
    }
}
