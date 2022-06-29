package serializable;

import java.io.Serializable;

public class Message implements Serializable {
    private static final long serialVersionUID = -5273070854571257639L;
    public String message;
    public User fromUser;
    public int toUserId;
    public boolean isGroupChat;     // 当是群聊时，toUserId代表群聊的Id

    public Message(User fromUser, String message, int toUserId, boolean isGroupChat){
        this.fromUser = fromUser;
        this.message = message;
        this.toUserId = toUserId;
        this.isGroupChat = isGroupChat;
    }
}
