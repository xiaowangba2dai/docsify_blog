import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

public class BLS {
    public static void main(String[] args) {
        Pairing bp = PairingFactory.getPairing("a.properties");
        Field G1 = bp.getG1();
        Field Zr = bp.getZr();
        Element g = G1.newRandomElement();
        System.out.println(g);
        Element x = Zr.newRandomElement(); // 作为私钥
        Element g_x = g.duplicate().powZn(x); // 所谓公钥

        // Signing
        String m = "message";
        byte[] m_hash = Integer.toString(m.hashCode()).getBytes(); // m的哈希值
        Element h = G1.newElementFromHash(m_hash,0,m_hash.length); // 哈希值映射为一个G1上的群元素h
        Element sig = h.duplicate().powZn(x); // 计算签名

        // Verification
        Element pl = bp.pairing(sig, g);
        Element pr = bp.pairing(h,g_x);
        if (pl.isEqual(pr)) {
            System.out.println(("yes"));
        }
    }
}
