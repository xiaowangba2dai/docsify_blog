
/*
    一个BIO的方式，多线程
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketIOPropertites {

    // server socket listen property
    private static final int RECEIVE_BUFFER = 10; // 应用程序向内核提供的关于通过套接字接收数据所需要的缓冲区大小
    private static final int SO_TIMEOUT = 0; // 设置阻塞Socket操作的超时时间, 包括 ServerSocket.accept(),SocketInputStream.read(); DatagramSocket.receive(); 如果超时，该操作将阻塞
    private static final boolean REUSE_ADDR = false; // This option enables and disables the ability to have multiple sockets listen to the same address and port.
    private static final int BACK_LOG = 2; // 如果有很多连接过来，但是这边的线程资源不够了，容许排队的个数，超过的拒绝

    // client socket listen property on server endpoint
    /*
        当为TCP套接字设置了keepalive选项后，如果在两个小时内(注:实际值与具体实现有关)没有任何数据交换，TCP将自动向对端发送keepalive探测。
        该探针是一个TCP段，对端必须响应。预期的反应有三种:
            1. 对等体响应预期的ACK。没有通知应用程序(因为一切正常)。
            2. 对等端响应一个RST，它告诉Locat TCP对等端的主机已经崩溃并重新启动。
            3. 没有回应,socket关闭,这个选项的目的是检测对等主机是否崩溃
    * */
    private static final boolean CLI_KEEPALIVE = false; // 心跳，为false不是长连接

    /*
        当设置了00BINLINE选项时，套接字上接收到的任何TCP紧急数据都将通过套接字输入流接收。
        当禁用该选项(默认)时，紧急数据将被静默丢弃。
     */
    private static final boolean CLI_OOB = false;

    /*
        设置一个提示，提示平台用于传入网络I/0的底层缓冲区的大小。当在set中使用时，这是应用程序向内核提供的关于通过套接字接收数据的缓冲区大小的建议。当在get中使用时，它必须返回平台在这个套接字上接收数据时实际使用的缓冲区的大小
     */
    private static final int CLI_REC_BUF = 20; // 和 Recv_Q有什么关系

    private static final int CLI_SEND_BUF = 20; // 和 Send_Q有什么关系


    private static final boolean CLI_REUSE_ADDR = false; // 设置socket的SO_REUSEADDR。这仅用于java中的MulticastSockets，并且默认为MulticastSockets设置。

    /*
        1. LINGER = false
            这是默认行为，当为false时，linger对应的设置就没有意义，
            当 socket 主动 close，调用的线程会马上返回，不会阻塞，残留在缓冲区中的数据将继续发送给对端，并且与对端进行 FIN-ACK 协议交换，最后进入 TIME_WAIT 状态。

        2. on = true, linger > 0
           调用 close 的线程将阻塞，发生两种可能的情况：
            （1）是剩余的数据继续发送，进行关闭协议交换；
            （2）就是超时过期，剩余的数据将被删除，进行 FIN-ACK 交换。

        3. on = true, linger = 0
           这种方式就是所谓 hard-close，这个方式是讨论或者争论最多的用法，
           任何剩余的数据都被立即丢弃，并且 FIN-ACK 交换也不会发生，替代产生 RST ，让对端抛出connection reset的 SocketException 。
     */
    private static final boolean CLI_LINGER = true; // 断开连接的速度
    private static final int CLI_LINGER_N = 0;

    /*
        设置阻塞Socket操作的超时时间:
     */
    private static final int CLI_TIMEOUT = 0; // 读取客户端的时候，如果超时抛异常
    private static final boolean CLI_NO_DELAY = false; // 写入到网络的数据在等待先前写入数据的确认时不会被缓冲,默认为false

    public static void main(String[] args) {
        ServerSocket server = null;
        try {
            server = new ServerSocket();
            server.bind(new InetSocketAddress(9090), BACK_LOG);
            server.setReceiveBufferSize(RECEIVE_BUFFER);
            server.setReuseAddress(REUSE_ADDR);
            server.setSoTimeout(SO_TIMEOUT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(("server up use 9090"));

        while (true) {
            try {
                System.in.read(); // 分水岭，先输入一个东东

                Socket client = server.accept(); // 会在这里阻塞
                System.out.println(("client: " + client.getInetAddress() + ":" + client.getPort()));

                client.setKeepAlive(CLI_KEEPALIVE);
                client.setOOBInline(CLI_OOB);
                client.setReceiveBufferSize(CLI_REC_BUF);
                client.setSendBufferSize(CLI_SEND_BUF);
                client.setReuseAddress(CLI_REUSE_ADDR);
                client.setSoLinger(CLI_LINGER, CLI_LINGER_N);
                client.setSoTimeout(CLI_TIMEOUT);
                client.setTcpNoDelay(CLI_NO_DELAY);

                new Thread(
                        ()->{
                            while (true) {
                                try {
                                    InputStream in = client.getInputStream();
                                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                                    char[] data = new char[1024];
                                    int num = reader.read(data);

                                    if (num > 0) {
                                        System.out.println(("client read some data is : " + num + ", val: " + new String(data, 0, num)));
                                    }
                                    else if (num == 0) {
                                        System.out.println(("client read nothing"));
                                    }
                                    else {
                                        System.out.println(("client readed -1"));
                                        client.close();
                                        break;
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                ).start();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
