package command;

import controller.UserManager;

public class ShowAllUserCommand extends BaseCommand {
    /**
     * 打印所有的用户信息
     * @param args 1参数
     */
    public ShowAllUserCommand(String[] args){
        if(args.length == 1){
            isValidAttrs = true;
        } else {
            System.out.println("命令参数不符合要求！");
        }
    }
    @Override
    public void execute() {
        if(isValidAttrs){
            UserManager.getInstance().showAllUser();
        }
    }
}
