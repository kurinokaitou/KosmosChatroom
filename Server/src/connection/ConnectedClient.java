package connection;

import serializable.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnectedClient {
    private User user = null;
    private String clientAddress;
    private Thread clientThreadHandle = null;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    public ConnectedClient(String address){
        this.clientAddress = address;
    }

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

    public void writeObject(){

    }
}
