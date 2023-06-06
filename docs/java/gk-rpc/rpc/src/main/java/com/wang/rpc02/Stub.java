package com.wang.rpc02;

import com.wang.rpc.common.User;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Stub {
    public static User findUserById(int writeId) throws IOException {
        Socket socket = new Socket("127.0.0.1", 8888);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);

        dataOutputStream.writeInt(writeId);

        socket.getOutputStream().write(byteArrayOutputStream.toByteArray());
        socket.getOutputStream().flush();

        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

        int id = dataInputStream.readInt();
        String name = dataInputStream.readUTF();

        dataOutputStream.close();
        dataInputStream.close();
        socket.close();

        return new User(id,name);
    }
}
