package network.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class ServerDemo {
    public static void main(String[] args) {
        try {
            // 1. DatagramSocket 代表 声明一个UDP协议的socket
            DatagramSocket datagramSocket = new DatagramSocket(8888);

            // 2. byte数组用于数据存储
            byte[] car = new byte[1024];

            // 3. DatagramPacket 用来表示数据报包
            DatagramPacket datagramPacket = new DatagramPacket(car, car.length);

            System.out.println(("等待UDP协议传输数据"));

            datagramSocket.receive(datagramPacket);

            int length = datagramPacket.getLength();
            System.out.println((new String(car, 0, length)));
            datagramSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
