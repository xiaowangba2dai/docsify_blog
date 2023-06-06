package com.wang.rpc02;

import com.wang.rpc.common.User;
import com.wang.rpc01.UserServiceImpl;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static boolean running = true;

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(8888);
        while (running) {
            Socket socket = serverSocket.accept();
            process(socket);
            socket.close();
        }
        serverSocket.close();
    }

    private static void process(Socket socket) throws IOException {
        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

        int id = dataInputStream.readInt(); // 读取四个输入字节并返回一个int值
        // readUTF:从流 in 中读取用 UTF-8 修改版格式编码的 Unicode 字符格式的字符串；然后以 String 形式返回此字符串。
        com.wang.rpc01.UserServiceImpl userService = new UserServiceImpl();
        User user = userService.findUserById(id);

        dataOutputStream.writeInt(user.getId());
        dataOutputStream.writeUTF(user.getName());
        dataOutputStream.flush();
    }


}
