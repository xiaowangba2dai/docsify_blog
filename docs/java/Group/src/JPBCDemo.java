import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

public class JPBCDemo {
    public static void main(String[] args) {
        Pairing bp = PairingFactory.getPairing("a.properties");

        // 生成群
        Field G1 = bp.getG1();
        Field Zr = bp.getZr();

        // 群上选择随机元素
        Element g = G1.newRandomElement();
        Element a = Zr.newRandomElement();
        Element b = Zr.newRandomElement();

        // 验证双线性 e(g^a, g^b) = e(g,g)^ab
        Element g_a = g.duplicate().powZn(a);
        Element g_b = g.duplicate().powZn(b);
        Element egg_ab = bp.pairing(g_a,g_b);  // 左边

        g_a.duplicate().mul(g_b);

        Element egg = bp.pairing(g,g);
        Element ab = a.duplicate().mul(b);
        Element egg_ab_p = egg.duplicate().powZn(ab); // 右边

        // 对比左边右边是否相等
        if (egg_ab.isEqual(egg_ab_p)) {
            System.out.println(("yes"));
        }
        else {
            System.out.println(("no"));
        }
    }
}
