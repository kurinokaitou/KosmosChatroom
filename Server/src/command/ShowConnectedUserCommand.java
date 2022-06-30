package command;

import controller.UserManager;

public class ShowConnectedUserCommand extends BaseCommand{
    /**
     * 打印所有已登录用户
     * @param args 1参数
     */
    public ShowConnectedUserCommand(String[] args){
        if(args.length == 1){
            isValidAttrs = true;
        } else {
            System.out.println("命令参数不符合要求！");
        }
    }
    @Override
    public void execute() {
        if(isValidAttrs){
            UserManager.getInstance().showConnectedUser();
        }
    }
}
