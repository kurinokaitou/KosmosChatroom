package serializable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class UserStorage implements Serializable {
    private static final long serialVersionUID = -1458736067758975813L;
    public UserStorage(User user){
        this.user = user;
        this.groupHistoryMap = new HashMap<>();
        this.userHistoryMap = new HashMap<>();
    }
    public User user;
    public Map<String, Group> groupHistoryMap; // 历史群聊
    public Map<String, User> userHistoryMap;   // 历史私聊用户
}
