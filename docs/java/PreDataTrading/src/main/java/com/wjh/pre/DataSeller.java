package com.wjh.pre;


import it.unisa.dia.gas.jpbc.Element;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class DataSeller {
    private String keyFileName = "keyFile";
    private final int keySize = 256; // bit

    // 1. Generate N aes key
    public byte[][] genAESKeys(int N) {
//        System.out.println("aes key gen..");
        byte[][] res = new byte[N][];
        int index = 0;

        File file = new File(keyFileName);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file, true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for (int i=0; i<N; i++) {
            try {
                res[index] =  AES256Util.initkey();
                out.write(res[index]);
                index++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return res;
    }

    public static Map<String, Element> keyGen() {
        return PRE.keyGen();
    }

    public static Map<String, Object> encrypt(Element pk1, Element pk2, byte[] m) throws NoSuchAlgorithmException {
        return PRE.encrypt(pk1,pk2,m);
    }

    public static Map<String, Object> reKeyGen(Element ski_1, Element ski_2, Element pki_2, Element pkj_2) throws NoSuchAlgorithmException {
        return PRE.reKeyGen(ski_1, ski_2, pki_2, pkj_2);
    }

    public static Map<String, Object> reEncrypt(Map<String, Object> rk_i_j, Map<String, Object> c, Element pki_1, Element pki_2, Element pkj_1, Element pkj_2) throws NoSuchAlgorithmException {
        return PRE.reEncrypt(rk_i_j, c, pki_1, pki_2, pkj_1, pkj_2);
    }

}
