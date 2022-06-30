package connection;

import controller.UserManager;
import serializable.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class ConnectedClient {
    private User user = null;
    private String clientAddress;
    private Thread clientThreadHandle = null;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;

    /**
     * 根据客户端IP地址创建client
     * @param address 地址
     */
    public ConnectedClient(String address){
        this.clientAddress = address;
    }

    /**
     * 客户端是否已经登录
     * @return 是否登录
     */
    public boolean clientIsLogin(){return user != null;}

    /**
     * 设置客户端的对象输入流
     * @param objectInputStream 对象输入流
     */
    public void setObjectInputStream(ObjectInputStream objectInputStream) {
        this.objectInputStream = objectInputStream;
    }

    /**
     * 设置客户端的对象输出流
     * @param objectOutputStream 对象输出流
     */
    public void setObjectOutputStream(ObjectOutputStream objectOutputStream) {
        this.objectOutputStream = objectOutputStream;
    }

    /**
     * 获取客户端线程的句柄
     * @return 线程句柄
     */
    public Thread getClientThreadHandle() {
        return clientThreadHandle;
    }

    /**
     * 保存客户端线程的句柄
     * @param clientThreadHandle 线程句柄
     */
    public void setClientThreadHandle(Thread clientThreadHandle) {
        this.clientThreadHandle = clientThreadHandle;
    }

    /**
     * 获取客户端地址，在登录后为用户名
     * @return 客户端地址
     */
    public String getClientAddress() {
        return clientAddress;
    }

    /**
     * 设置客户端地址
     * @param clientAddress 客户端地址
     */
    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    /**
     * 获取客户端对应的用户
     * @return 用户
     */
    public User getUser() {
        return user;
    }

    /**
     * 设置客户端用户
     * @param user 用户
     */
    public void setUser(User user){
        this.user = user;
    }

    /**
     * 同步地向对象输出流中写入对象
     * @param writeObject 写入对象
     */
    public synchronized void writeObject(Object writeObject){
        try {
            objectOutputStream.writeObject(writeObject);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从对象输入流中获取对象
     * @return 获取对象
     * @throws IOException 当无法读取到对象流或是对象流意外终结代表客户端已下线
     */
    public Object readObject() throws IOException{
        Object readObject = null;
        try {
            readObject = objectInputStream.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return readObject;
    }

    /**
     * 读取服务器上用户未登录时接受的暂存信息，并且一次性发送给客户端
     */
    public void writeRetentMessages(){
        List<Message> retentMessages = UserManager.getInstance().getUserRetentMessages(user.getUserId());
        if(retentMessages != null){
            Response res = new Response(TransmissionType.CHAT);
            res.status = ResponseStatus.SUCCESS;
            // 一次返回一个List<Message>
            res.setAttribute("messages", retentMessages);
            writeObject(res);
        }
    }
}
