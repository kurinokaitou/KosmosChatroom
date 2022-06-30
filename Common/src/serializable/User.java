package serializable;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 1725401777770135112L;
    private String name;
    transient private String password;
    private int userId;

    public User(String name, String pwd, int id){
        this.name = name;
        this.password = pwd;
        this.userId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", userId=" + userId +
                '}';
    }
}
