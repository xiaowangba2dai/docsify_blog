package network.tcp;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

// 客户端
public class clientDemo1 {
    public static void main(String[] args) {
        InetAddress serverIP = null;
        Socket socket = null;
        OutputStream outputStream = null;
        try {
            // 1. 知道服务器的地址
            serverIP = InetAddress.getByName("127.0.0.1");
            // 2. 端口号
            int port = 9999;

            // 3. 创建一个socket连接
            socket = new Socket(serverIP, port);
            // 4. 发送消息IO流
            outputStream = socket.getOutputStream();
            outputStream.write("你好，欢迎".getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
