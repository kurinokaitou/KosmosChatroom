package serializable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Group implements Serializable {
    private static final long serialVersionUID = 3898375474103954301L;
    private final String groupCode;
    private Map<Integer, User> userMap;

    public Group(String groupCode){
        this.groupCode = groupCode;
    }

    public User getUserById(int userId){
        return userMap.get(userId);
    }

    public void addUser(User user){
        if(!userMap.containsKey(user.getUserId())){
            userMap.put(user.getUserId(), user);
        } else {
            System.out.println("已经存在此用户！");
        }
    }

    public boolean deleteUser(User user){
        if(userMap.containsKey(user.getUserId())){
            userMap.remove(user.getUserId());
            return true;
        }else return false;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public List<User> getUserList(){
        return new ArrayList<>(userMap.values());
    }
}
