package command;

import controller.UserManager;

public class ShowAllUserCommand extends BaseCommand {
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
