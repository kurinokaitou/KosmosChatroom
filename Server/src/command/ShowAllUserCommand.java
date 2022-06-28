package command;

import controller.UserManager;

public class ShowAllUserCommand extends BaseCommand {
    public ShowAllUserCommand(String[] args){
        if(args.length == 1){
            isValidAttrs = true;
        }
    }
    @Override
    public void execute() {
        if(isValidAttrs){
            UserManager.getInstance().showAllUser();
        }
    }
}
