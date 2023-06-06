import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.field.curve.CurveField;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PRE {
    private static final int L0 = 32; // 最大可以加密的长度
    private static final int L1 = 16; // customized length
    public static Pairing bp = PairingFactory.getPairing("b.properties");
    public static Element g = bp.getG1().newRandomElement().getImmutable(); // 公共参数 g


    public static byte[] byteMerger(byte[] byte_1, byte[] byte_2) {
        byte[] byte_3 = new byte[byte_1.length + byte_2.length];
        System.arraycopy(byte_1, 0, byte_3, 0, byte_1.length);
        System.arraycopy(byte_2, 0, byte_3, byte_1.length, byte_2.length);
        return byte_3;
    }

    // {0,1}^lo and {0,1}^l1 -> Zp
    public static Element h1(byte[] e1, byte[] e2) throws NoSuchAlgorithmException {
        byte[] e = byteMerger(e1,e2);
        MessageDigest hasher = MessageDigest.getInstance("SHA-512");
        byte[] zp_bytes = hasher.digest(e);
        Element hash_result = bp.getZr().newElementFromHash(zp_bytes,0,zp_bytes.length).getImmutable(); // 再把hash后的bits映射到Zp
        return hash_result;
    }

    // G1 -> {0,1}^{l0+l1}
    public static byte[] h2(Element g1_element) throws NoSuchAlgorithmException {
        byte[] g1_bytes = g1_element.getImmutable().toCanonicalRepresentation();
        byte[] zp_bytes = g1_bytes;
        MessageDigest hasher = MessageDigest.getInstance("SHA-512");
        zp_bytes = hasher.digest(g1_bytes); // 吧G1元素hash成512bits
        byte[] res = new byte[L0+L1];
        System.arraycopy(zp_bytes,0,res,0,L0+L1);
        return res;
    }

    // {0,1}* -> Zp
    public static Element h3(Element D, Element E, byte[] f) throws NoSuchAlgorithmException {
        byte[] e = byteMerger(byteMerger(D.toBytes(),E.toBytes()),f);
        MessageDigest hasher = MessageDigest.getInstance("SHA-512");
        byte[] zp_bytes = hasher.digest(e);
        Element hash_result = bp.getZr().newElementFromHash(zp_bytes,0,zp_bytes.length).getImmutable(); // 再把hash后的bits映射到Zp
        return hash_result;
    }

    // H4: G1 -> Zp
    public static Element h4(Element g1_element) throws NoSuchAlgorithmException {
        byte[] g1_bytes = g1_element.getImmutable().toCanonicalRepresentation();
        byte[] zp_bytes = g1_bytes;
        MessageDigest hasher = MessageDigest.getInstance("SHA-512");
        zp_bytes = hasher.digest(g1_bytes); // 吧G1元素hash成512bits

        Element hash_result = bp.getZr().newElementFromHash(zp_bytes,0,zp_bytes.length).getImmutable(); // 再把hash后的bits映射到Zp
        return hash_result;
    }

    public static Map<String, Element> keyGen() {
        Map<String, Element> key = new HashMap<>();
        Element sk1 = bp.getZr().newRandomElement().getImmutable();
        Element sk2 = bp.getZr().newRandomElement().getImmutable();
        Element pk1 = g.duplicate().powZn(sk1);
        Element pk2 = g.duplicate().powZn(sk2);
        key.put("sk1",sk1);
        key.put("sk2",sk2);
        key.put("pk1",pk1);
        key.put("pk2",pk2);
        return key;
    }


    public static Map<String, Object> encrypt(Element pk1, Element pk2, byte[] m) throws NoSuchAlgorithmException {
        Element u = bp.getZr().newRandomElement().getImmutable();
        Element h4pk2 = h4(pk2);
        Element pk1_h4pk2_pk2 = pk1.duplicate().powZn(h4pk2).duplicate().mul(pk2);
        Element D = pk1_h4pk2_pk2.duplicate().powZn(u);

        byte[] w = new byte[L1];
        new SecureRandom().nextBytes(w);
        Element r = h1(m,w);

        Element E = pk1_h4pk2_pk2.duplicate().powZn(r);
        Element gr = g.duplicate().powZn(r);
        byte[] h2gr = h2(gr);
        byte[] m_w = byteMerger(m,w);

//        System.out.println(Arrays.toString(m_w));

        byte[] F = new byte[L0+L1];
        for (int i=0; i<F.length; i++) {
            F[i] = (byte)(h2gr[i] ^ m_w[i]);
        }

        Element s = u.duplicate().add(r.duplicate().mulZn(h3(D,E,F)));
        Map<String,Object> map = new HashMap<>();
        map.put("D", D);
        map.put("E", E);
        map.put("F", F);
        map.put("s", s);
        return map;
    }

    public static byte[] decrypt(Element pk1, Element pk2, Element sk1, Element sk2, Map<String, Object> c) throws NoSuchAlgorithmException {
        Element h4pk2 = h4(pk2);
        Element pk1_h4pk2_pk2 = pk1.duplicate().powZn(h4pk2).duplicate().mul(pk2);
        Element s = (Element) c.get("s");
        Element left = pk1_h4pk2_pk2.duplicate().powZn(s);

        Element D = (Element) c.get("D");
        Element E = (Element) c.get("E");
        byte[] F = (byte[]) c.get("F");

        Element right = D.duplicate().mul(E.duplicate().powZn(h3(D,E,F)));

        if (!left.isEqual(right)) {
            return null;
        }

        Element index = sk1.duplicate().mul(h4(pk2)).duplicate().add(sk2).invert();
        byte[] h2_res = h2(E.duplicate().powZn(index));
        byte[] m_w = new byte[F.length];

        for (int i=0; i<F.length; i++) {
            m_w[i] = (byte) (F[i] ^ h2_res[i]);
        }

        byte[] m = new byte[L0];
        System.arraycopy(m_w, 0, m, 0, L0);

        byte[] w = new byte[L1];
        System.arraycopy(m_w,L0,w,0,L1);

        if (!E.isEqual(pk1_h4pk2_pk2.duplicate().powZn(h1(m,w)))) {
            return null;
        }

        return m;
    }

    public static Map<String, Object> reKeyGen(Element ski_1, Element ski_2, Element pki_2, Element pkj_2) throws NoSuchAlgorithmException {
        byte[] h = new byte[L0];
        new SecureRandom().nextBytes(h);

        byte[] pi = new byte[L1];
        new SecureRandom().nextBytes(pi);

        Element v = h1(h,pi);

        Element V = pkj_2.duplicate().powZn(v);

        byte[] h2gv = h2(g.duplicate().powZn(v));
        byte[] h_pi = byteMerger(h,pi);

        byte[] W = new byte[h2gv.length];
        for (int i=0; i<W.length; i++) {
            W[i] = (byte) (h2gv[i] ^ h_pi[i]);
        }

        Element hash_H = bp.getZr().newElementFromHash(h,0,h.length).getImmutable();
        Element rk_i_j_1 = hash_H.duplicate().div(ski_1.duplicate().mul(h4(pki_2)).duplicate().add(ski_2));

        Map<String, Object> res = new HashMap<>();
        res.put("rk_i_j_1", rk_i_j_1);
        res.put("V", V);
        res.put("W", W);

        return res;
    }

    // 只需要放入E1 = E ^ rk_i_j_1;
    public static Map<String, Object> reEncrypt(Map<String, Object> rk_i_j, Map<String, Object> c, Element pki_1, Element pki_2, Element pkj_1, Element pkj_2) throws NoSuchAlgorithmException {
        Element pki1_h4pki2_pki2 = pki_1.duplicate().powZn(h4(pki_2)).duplicate().mul(pki_2);
        Element s = (Element) c.get("s");
        Element left = pki1_h4pki2_pki2.duplicate().powZn(s);

        Element D = (Element) c.get("D");
        Element E = (Element) c.get("E");
        byte[] F = (byte[]) c.get("F");
        Element right = D.duplicate().mul(E.duplicate().powZn(h3(D,E,F)));

        if (!left.isEqual(right)) {
            return null;
        }

        Element rk_i_j_1 = (Element) rk_i_j.get("rk_i_j_1");
        Element E_1 = E.duplicate().powZn(rk_i_j_1);

        System.out.println(("--------------------------------------------------------"));
        System.out.println(E);
        System.out.println(rk_i_j_1);
        System.out.println(E_1);
        System.out.println(("--------------------------------------------------------"));

        Element V = (Element) rk_i_j.get("V");
        byte[] W = (byte[]) rk_i_j.get("W");

        Map<String, Object> res = new HashMap<>();
        res.put("E_1", E_1);
        res.put("F", F);
        res.put("V", V);
        res.put("W", W);

        return res;
    }

    public static byte[] reDecrypt(Map<String, Object> reEncryptC, Element sk_1, Element sk_2, Element pk_1, Element pk_2) throws NoSuchAlgorithmException {
        byte[] W = (byte[]) reEncryptC.get("W");
        Element V = (Element) reEncryptC.get("V");
        byte[] h2v = h2(V.duplicate().powZn(sk_2.duplicate().invert()));

        byte[] h_pi = new byte[W.length];
        for (int i=0; i<h_pi.length; i++) {
            h_pi[i] = (byte) (W[i] ^ h2v[i]);
        }

        byte[] h = new byte[L0];
        System.arraycopy(h_pi, 0, h, 0, L0);

        byte[] pi = new byte[L1];
        System.arraycopy(h_pi, L0, pi, 0, L1);


        Element E_1 = (Element) reEncryptC.get("E_1");
        Element new_H = bp.getZr().newElementFromHash(h,0,h.length).getImmutable();
        byte[] h2e = h2(E_1.duplicate().powZn(new_H.duplicate().invert()));

        byte[] F = (byte[]) reEncryptC.get("F");
        byte[] m_w = new byte[F.length];
        for (int i=0; i<m_w.length; i++) {
            m_w[i] = (byte) (F[i] ^ h2e[i]);
        }

        byte[] m = new byte[L0];
        System.arraycopy(m_w, 0, m, 0, L0);

        byte[] w = new byte[L1];
        System.arraycopy(m_w,L0,w,0,L1);

        if (!V.isEqual(pk_2.duplicate().powZn(h1(h,pi)))) {
            return null;
        }

        if (!E_1.isEqual(g.duplicate().powZn(h1(m,w).mul(new_H)))) {
            return null;
        }

        return m;
    }

    public static byte[] toByteArray(BigInteger bi, int length) {
        byte[] array = bi.toByteArray();
        // 这种情况是转换的array超过25位
        if (array[0] == 0) {
            byte[] tmp = new byte[array.length - 1];
            System.arraycopy(array, 1, tmp, 0, tmp.length);
            array = tmp;
        }
        // 假如转换的byte数组少于24位，则在前面补齐0
        if (array.length < length) {
            byte[] tmp = new byte[length];
            System.arraycopy(array, 0, tmp, length - array.length, array.length);
            array = tmp;
        }
        return array;
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {

        // 1. keygen
        Map<String, Element> keyA = keyGen();
        Map<String, Element> keyB = keyGen();

        // 2. message
        byte[] m = new byte[L0];
        new SecureRandom().nextBytes(m);
        System.out.println("(1) message: \n" + Arrays.toString(m));

        // 3. encrypt
        Map<String, Object> crypt_m = encrypt(keyA.get("pk1"),keyA.get("pk2"),m);
        System.out.println("(2) encrypt message:\n" + crypt_m);

        // 4. decrypt
        byte[] decrypt_m = decrypt(keyA.get("pk1"),keyA.get("pk2"),keyA.get("sk1"),keyA.get("sk2"),crypt_m);
        System.out.println("(3) decrypt message: \n" + Arrays.toString(decrypt_m));

        // 5. reKeyGen
        Map<String, Object> reKey = reKeyGen(keyA.get("sk1"),keyA.get("sk2"),keyA.get("pk2"),keyB.get("pk2"));
        System.out.println("(4) rekey:\n" + reKey);

        // 6. reEncrypt
        Map<String, Object> reEncrypt_m = reEncrypt(reKey,crypt_m,keyA.get("pk1"),keyA.get("pk2"),keyB.get("pk1"),keyB.get("pk2"));
        System.out.println("(5) reEncrypt message:\n" + reEncrypt_m);

        // 7. reDecrypt
        byte[] reDecrypt_m = reDecrypt(reEncrypt_m,keyB.get("sk1"),keyB.get("sk2"), keyB.get("pk1"), keyB.get("pk2"));
        System.out.println("(6) reDecrypt message: \n" + Arrays.toString(reDecrypt_m));
    }
}
