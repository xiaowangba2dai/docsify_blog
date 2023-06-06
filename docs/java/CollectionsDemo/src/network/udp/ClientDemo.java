package network.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class ClientDemo {
    public static void main(String[] args) {
        try {
            DatagramSocket datagramSocket = new DatagramSocket(2468);
            byte[] car = "udp协议的socket请求，有可能失败".getBytes();
            InetSocketAddress socketAddress = new InetSocketAddress("127.0.0.1", 8888);
            DatagramPacket datagramPacket = new DatagramPacket(car, car.length, socketAddress);
            datagramSocket.send(datagramPacket);
            datagramSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
