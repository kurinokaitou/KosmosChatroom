package serializable;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 1725401777770135112L;
    private final String name;
    private String password;
    private final int userId;

    /**
     * 创建一个用户
     * @param name 用户不可重名的账户名
     * @param pwd  密码
     * @param id   用户Id
     */
    public User(String name, String pwd, int id){
        this.name = name;
        this.password = pwd;
        this.userId = id;
    }

    /**
     * 创建一个用户
     * @param name 用户不可重名的账户名
     * @param id   用户Id
     */
    public User(String name, int id){
        this.name = name;
        this.userId = id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", userId=" + userId +
                '}';
    }
}
