package serializable;

import java.io.Serializable;

public class Message implements Serializable {
    private static final long serialVersionUID = -5273070854571257639L;
    public String content;
    public User fromUser;
    public int toUserId;
    public String groupCode;
    public boolean isGroupChat;     // 当是群聊时，toUserId代表群聊的Id

    public Message(User fromUser, String message){
        this.fromUser = fromUser;
        this.content = message;
    }

    public void setGroupMessage(String groupCode){
        this.groupCode = groupCode;
        this.isGroupChat = true;
    }

    public void setUserMessage(int toUserId){
        this.toUserId = toUserId;
        this.isGroupChat = false;
    }
}
