package network.basic;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class IpDemo {
    public static void main(String[] args) {
        try {
            InetAddress inetAddress = InetAddress.getByName("www.baidu.com"); // 查询网站IP地址
            System.out.println(inetAddress); // www.baidu.com/14.215.177.39

            // 常用方法
            System.out.println(inetAddress.getHostAddress()); // 查询网站IP地址
            System.out.println(inetAddress.getHostName()); // 域名

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
