package com.wjh.measure;

import com.wjh.pre.DataBuyer;
import com.wjh.pre.DataSeller;
import it.unisa.dia.gas.jpbc.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

public class Measure {

    private static final Logger log = LoggerFactory.getLogger(Measure.class);

    public static void main(String[] args) throws Exception {

        // load the properties
        FileInputStream propertiesFIS = new FileInputStream("./properties");
        Properties properties = new Properties();
        properties.load(propertiesFIS);
        propertiesFIS.close();

        // the count of aes key
        int keyCount = Integer.parseInt(properties.getProperty("keyCount"));


        DataSeller seller = new DataSeller();
        DataBuyer buyer = new DataBuyer();

        // 1. Generate n sample aes key, and store to a file
        byte[][] K = seller.genAESKeys(keyCount);
        log.info("================== Data owner encrypted the data ================== ");

        // 2. Generate pk and sk
        log.info("Key of seller gens..");
        Map<String, Element> keyS = seller.keyGen();
        log.info("Key of buyer gens..");
        Map<String, Element> keyB = buyer.keyGen();


        // 3. encrypt n sample aes key with seller public key
        log.info("Data owner encrypt k..");
        Map<String, Object>[] EK = new Map[keyCount];
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < keyCount; i++) {
            EK[i] = seller.encrypt(keyS.get("pk1"), keyS.get("pk2"), K[i]);
        }
        long endTime = System.currentTimeMillis();
        log.info("time: " + (endTime - startTime)+ " ms");

        // 4. re-encrypt key gen
//        log.info("Data owner generates rk..");
//
//        Map<String, Object> rk = seller.reKeyGen(keyS.get("sk1"),keyS.get("sk2"),keyS.get("pk2"),keyB.get("pk2"));

        // 5. re-encrypt
//        Map<String, Object>[] REK = new Map[keyCount];
//        long startTime = System.currentTimeMillis();
//        for (int i=0; i<keyCount; i++) {
//            REK[i] = seller.reEncrypt(rk, EK[i], keyS.get("pk1"), keyS.get("pk2"), keyB.get("pk1"), keyB.get("pk2"));
//        }



        // 6. buyer get rek and decrypt with his own sk
//        log.info(("buyer get REK and decrypt with his own sk"));
//
//        for (int i=0; i<keyCount; i++) {
//            byte[] decryptEk = buyer.reDecrypt(REK[i],keyB.get("sk1"),keyB.get("sk2"), keyB.get("pk1"), keyB.get("pk2"));
//            if (!Arrays.equals(K[i],decryptEk)){
//                System.out.println("error key");
//            }
//        }
    }
}
