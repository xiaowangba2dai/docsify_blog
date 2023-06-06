package com.wjh.pre;

import it.unisa.dia.gas.jpbc.Element;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class DataBuyer {
    public static Map<String, Element> keyGen() {
        return PRE.keyGen();
    }

    public static byte[] reDecrypt(Map<String, Object> reEncryptC, Element sk_1, Element sk_2, Element pk_1, Element pk_2) throws NoSuchAlgorithmException {
        return PRE.reDecrypt(reEncryptC, sk_1, sk_2, pk_1, pk_2);
    }
}
