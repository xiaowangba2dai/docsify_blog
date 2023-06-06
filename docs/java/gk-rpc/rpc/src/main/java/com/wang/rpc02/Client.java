package com.wang.rpc02;

import com.wang.rpc.common.User;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        User user = Stub.findUserById(123);
        System.out.println(user.toString());
    }
}
