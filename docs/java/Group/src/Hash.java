import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.jpbc.PairingParameters;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import it.unisa.dia.gas.plaf.jpbc.pairing.a.TypeACurveGenerator;
import it.unisa.dia.gas.plaf.jpbc.pairing.a1.TypeA1CurveGenerator;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {
    // rbit: the number of primes rBit代表Zp中阶数p的比特长度
    // qbit: the bit length of each prime qBit代表G中阶数的比特长度
//    static TypeACurveGenerator pg2 = new TypeACurveGenerator(128, 128);
//    static PairingParameters typeAParams2 = pg2.generate();
//    static Pairing bp = PairingFactory.getPairing(typeAParams2);
    public static Pairing bp = PairingFactory.getPairing("b.properties");


    // H0: {0, 1} * -> Zp
    public static Element hashFromStringToZp(String str) {
        return bp.getZr().newElement().setFromHash(str.getBytes(), 0, str.length()).getImmutable();
    }

    public static Element hashFromBytesToZp(byte[] bytes) {
        return bp.getZr().newElement().setFromHash(bytes,0,bytes.length).getImmutable();
    }

    // H1: {0, 1} * -> G1
    public static Element hashFromStringToG1(String str) {
        return bp.getG1().newElement().setFromHash(str.getBytes(),0,str.length()).getImmutable();
    }

    public static Element hashFromBytesToG1(byte[] bytes) {
        return bp.getG1().newElement().setFromHash(bytes,0,bytes.length).getImmutable();
    }

    // H2: G1 -> Zp
    public static Element hashFromG1ToZp(Element g1_element) throws NoSuchAlgorithmException {
        byte[] g1_bytes = g1_element.getImmutable().toCanonicalRepresentation();
        byte[] zp_bytes = g1_bytes;
        MessageDigest hasher = MessageDigest.getInstance("SHA-512");
        zp_bytes = hasher.digest(g1_bytes); // 吧G1元素hash成512bits

        Element hash_result = bp.getZr().newElementFromHash(zp_bytes,0,zp_bytes.length).getImmutable(); // 再把hash后的bits映射到Zp
        return hash_result;
    }

    public static void main(String[] args) {
//        System.out.println(typeAParams2);
//        System.out.println(bp.getG1().newElement());
        Element g1 = bp.getG1().newRandomElement().getImmutable(); // 乘法循环群,上的点是z值为0的椭圆曲线上的点
        System.out.println("g1:\n" + g1);
        Element g2 = bp.getG2().newRandomElement().getImmutable();
        System.out.println("g2:\n" + g2);
        Element gt = bp.getGT().newRandomElement().getImmutable();
//        System.out.println(gt);
        Element zp = bp.getZr().newRandomElement().getImmutable(); // 整数域上的加法循环群, 整数循环群上的点是数
//        System.out.println(zp);


        // 相关运算
        Element a = bp.getZr().newRandomElement();
        Element b = bp.getZr().newRandomElement();

        System.out.println("a: " + a);

        // 1. g1 ^ a
        Element g1_pow_a = g1.duplicate().powZn(a); // 结果也是一个点
        System.out.println(("(1) g1^a"));
        System.out.println(g1_pow_a);

        // 2. a * g1
        Element g1_mul_a = g1.duplicate().mulZn(a);
        System.out.println(("(2) a * g1"));
        System.out.println(g1_mul_a);

        // 3. g1 + g2
        Element g1_add_g2 = g1.duplicate().add(g2);
        System.out.println(("(3) g1 + g2"));
        System.out.println(g1_add_g2);

        // 4. gt ^ b
        Element gt_pow_b = gt.duplicate().powZn(b);
        System.out.println(("(4) gt ^ b"));
        System.out.println(gt_pow_b);

        // 5. b * gt
        Element gt_mul_b = gt.duplicate().mulZn(b);
        System.out.println(("(5) b * gt"));
        System.out.println(gt_mul_b);

        // 6. gt + gt
        Element gt_add_gt = gt.duplicate().add(gt);
        System.out.println(("(6) gt + gt"));
        System.out.println(gt_add_gt);

        // 7. a + b
        Element a_add_b = a.duplicate().add(b);
        System.out.println(("(7) a + b"));
        System.out.println((a_add_b));

        // 8. a * b
        Element a_mul_b = a.duplicate().mulZn(b);
        System.out.println(("(8) a * b"));
        System.out.println(a_mul_b);

        // Type A 曲线初始化过程中需要提供两个参数，rBit 代表 Zp 中阶数 p 的比特长度，qBit 代表 G 中阶数的比特长度
        int rbit = 16;
        int qbit = 32;
        TypeACurveGenerator pg = new TypeACurveGenerator(rbit,qbit);
        PairingParameters typeAParams = pg.generate();
        Pairing bp1 = PairingFactory.getPairing(typeAParams);

        // Type A1 曲线需要提供两个参数，numPrime是阶数n中包含质数因子的数量，qbit是每个质数因子的比特长度。
        int numPrime = 16;
        TypeA1CurveGenerator pg1 = new TypeA1CurveGenerator(numPrime, qbit);
        PairingParameters typeA1Params = pg1.generate();
        Pairing bp2 = PairingFactory.getPairing(typeA1Params);
    }
}
