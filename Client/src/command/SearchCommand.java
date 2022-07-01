package command;

import controller.ClientManager;
import serializable.Request;
import serializable.TransmissionType;

public class SearchCommand extends BaseCommand{
    private String targetUserName;

    /**
     * 搜索用户命令
     * @param args 2参数
     */
    public SearchCommand(String... args){
        super();
        if(args.length == 2){
            isValidAttrs = true;
            this.targetUserName = args[1];
        } else {
            System.out.println("命令参数不符合要求！");
        }
    }
    @Override
    public void execute() {
        if (!isValidAttrs) return;
        Request request = new Request(TransmissionType.SEARCH);
        request.setAttribute("userName", targetUserName);
        ClientManager.sendRequest(request);
    }
}
