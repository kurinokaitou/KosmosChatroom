package command;

import static controller.ClientManager.shutdown;

public class ShutdownCommand extends BaseCommand{
    public ShutdownCommand(String... args){
        if(args.length == 1){
            isValidAttrs = true;
        } else {
            System.out.println("命令参数不符合要求！");
        }
    }
    @Override
    public void execute() {
        if(!isValidAttrs) return;
        shutdown();
    }
}
