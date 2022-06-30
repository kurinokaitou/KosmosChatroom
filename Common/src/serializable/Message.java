package serializable;

import java.io.Serializable;

public class Message implements Serializable {
    private static final long serialVersionUID = -5273070854571257639L;
    public String content;
    public User fromUser;
    public int toUserId;
    public String groupCode;
    public boolean isGroupChat;     // 当是群聊时，toUserId代表群聊的Id

    /**
     * 创建一条信息
     * @param fromUser 来自用户
     * @param content 信息文本
     */
    public Message(User fromUser, String content){
        this.fromUser = fromUser;
        this.content = content;
    }

    /**
     * 设定信息为群发
     * @param groupCode 群号
     */
    public void setGroupMessage(String groupCode){
        this.groupCode = groupCode;
        this.isGroupChat = true;
    }

    /**
     * 设定信息为私聊
     * @param toUserId 用户Id
     */
    public void setUserMessage(int toUserId){
        this.toUserId = toUserId;
        this.isGroupChat = false;
    }
}
