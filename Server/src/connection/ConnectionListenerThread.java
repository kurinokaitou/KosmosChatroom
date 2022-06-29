package connection;

import controller.RequestHandlerThread;
import controller.ServerManager;
import java.io.IOException;
import java.net.Socket;

public class ConnectionListenerThread implements Runnable{
    @Override
    public void run() {
        ServerManager serverManager = ServerManager.getInstance();
        try {
            while (true){
                Socket socket = ServerManager.serverSocket.accept();   // 新的客户端链接
                String clientAddress = socket.getInetAddress().getHostAddress();
                ConnectedClient newConnectedClient = new ConnectedClient(clientAddress);
                Thread clientThread = new Thread(new RequestHandlerThread(socket, newConnectedClient), clientAddress);
                newConnectedClient.setClientThreadHandle(clientThread);
                clientThread.start();
                serverManager.connectClient(newConnectedClient);
                System.out.println("[用户连接]" + clientAddress + " 当前有"+serverManager.getConnectedClientNum()+"个客户端连接");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
