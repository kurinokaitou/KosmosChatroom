package serializable;

import java.util.Map;

public class Group {
    private int groupId;
    private Map<Integer, User> userMap;

    public Group(int groupId){
        this.groupId = groupId;
    }

    public User getUserById(int userId){
        return userMap.get(userId);
    }

    public boolean addUser(User user){
        if(!userMap.containsKey(user.getUserId())){
            userMap.put(user.getUserId(), user);
            return true;
        } else return false;
    }

    public boolean deleteUser(User user){
        if(userMap.containsKey(user.getUserId())){
            userMap.remove(user.getUserId());
            return true;
        }else return false;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
}
