package network.basic;

import java.net.InetSocketAddress;

public class TestInetSocketAddress {
    public static void main(String[] args) {
        InetSocketAddress socketAddress = new InetSocketAddress("163.177.151.110", 80);

        System.out.println(socketAddress.getAddress()); // /163.177.151.110
        System.out.println(socketAddress.getHostName()); // 163.177.151.110
        System.out.println(socketAddress.getHostString()); // 163.177.151.110
        System.out.println(socketAddress.getPort()); // 80
    }
}
