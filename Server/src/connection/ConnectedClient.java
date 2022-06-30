package connection;

import controller.UserManager;
import serializable.*;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class ConnectedClient {
    private User user = null;
    private String clientAddress;
    private Thread clientThreadHandle = null;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    public ConnectedClient(String address){
        this.clientAddress = address;
    }

    public boolean clientIsLogin(){return user != null;}
    public ObjectInputStream getObjectInputStream() {
        return objectInputStream;
    }

    public void setObjectInputStream(ObjectInputStream objectInputStream) {
        this.objectInputStream = objectInputStream;
    }

    public ObjectOutputStream getObjectOutputStream() {
        return objectOutputStream;
    }

    public void setObjectOutputStream(ObjectOutputStream objectOutputStream) {
        this.objectOutputStream = objectOutputStream;
    }

    public Thread getClientThreadHandle() {
        return clientThreadHandle;
    }

    public void setClientThreadHandle(Thread clientThreadHandle) {
        this.clientThreadHandle = clientThreadHandle;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user){
        this.user = user;
    }

    public synchronized void writeObject(Object writeObject){
        try {
            objectOutputStream.writeObject(writeObject);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object readObject() throws IOException{
        Object readObject = null;
        try {
            readObject = objectInputStream.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return readObject;
    }

    public void readRetentMessages(){
        List<Message> retentMessages = UserManager.getInstance().getUserRetentMessages(user.getUserId());
        if(retentMessages != null){
            for(Message message : retentMessages){
                Response res = new Response(TransmissionType.CHAT);
                res.status = ResponseStatus.SUCCESS;
                res.setAttribute("message", message);
                writeObject(res);
            }
        }
    }
}
