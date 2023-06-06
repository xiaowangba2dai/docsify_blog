package com.wang.rpc01;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 8888);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);

        dataOutputStream.writeInt(123);

        socket.getOutputStream().write(byteArrayOutputStream.toByteArray());
        socket.getOutputStream().flush();

        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

        int id = dataInputStream.readInt();
        String name = dataInputStream.readUTF();

        System.out.println(id);
        System.out.println(name);

        dataOutputStream.close();
        dataInputStream.close();
        socket.close();

    }
}
