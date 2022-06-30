package serializable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Group implements Serializable {
    private static final long serialVersionUID = 3898375474103954301L;
    private final String groupCode;
    private final Map<Integer, User> userMap;

    /**
     * 根据随机生成的特定groupCode创建一个群组
     * @param groupCode 随机生成的特定groupCode
     */
    public Group(String groupCode){
        this.groupCode = groupCode;
        this.userMap = new ConcurrentHashMap<>();
    }

    /**
     * 根据id获取用户
     * @param userId 用户Id
     * @return 返回用户
     */
    public User getUserById(int userId){
        return userMap.get(userId);
    }

    /**
     * 群组中是否有此用户
     * @param userId 用户Id
     * @return 返回是否有
     */
    public boolean hasUser(int userId) {return userMap.containsKey(userId);}

    /**
     * 为群组中添加一用户
     * @param user 用户
     */
    public void addUser(User user){
        if(!userMap.containsKey(user.getUserId())){
            userMap.put(user.getUserId(), user);
        } else {
            System.out.println("已经存在此用户！");
        }
    }

    /**
     * 从群组中删除一用户
     * @param user 用户
     */
    public void deleteUser(User user){
        if(userMap.containsKey(user.getUserId())){
            userMap.remove(user.getUserId());
        }else {
            System.out.println("不存在此用户！");
        }
    }

    /**
     * 获取groupCode
     * @return groupCode
     */
    public String getGroupCode() {
        return groupCode;
    }

    /**
     * 获取群组的用户列表
     * @return 用户的列表
     */
    public List<User> getUserList(){
        return new ArrayList<>(userMap.values());
    }

    @Override
    public String toString() {
        return "Group{" +
                "groupCode='" + groupCode + '\'' +
                ", userMap=" + userMap +
                '}';
    }
}
