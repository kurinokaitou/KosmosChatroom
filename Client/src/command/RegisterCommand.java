package command;

import controller.ClientManager;
import serializable.Request;
import serializable.Response;
import serializable.TransmissionType;
import serializable.User;

public class RegisterCommand extends BaseCommand{
    private String userName;
    public String password;

    /**
     * 注册命令，密码必须5位以上
     * @param args 3参数
     */
    public RegisterCommand(String... args){
        super();
        if(ClientManager.getInstance().getCurrentUser() == null){
            if(args.length == 3){
                this.userName = args[1];
                if(args[2].length() <= 5){
                    System.out.println("密码长度必须6位以上！");
                } else {
                    this.password = args[2];
                    isValidAttrs = true;
                }
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
        response = ClientManager.sendRequestWithResponse(request);
        ClientManager.getInstance().setCurrentUser((User) response.getAttribute("user"));
        System.out.println(response.shortMessage);
    }
}
