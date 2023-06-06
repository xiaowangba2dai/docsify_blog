import java.math.BigInteger;
import java.security.spec.ECFieldFp;
import java.security.spec.EllipticCurve;

// 本程序做secp256k1这条椭圆曲线上的【乘法】
// ECC的困难在于，已知两点P、Q，使得Q=[n]P,求n
public class Main {
    static EllipticCurve E;
    static BigInteger a = BigInteger.ZERO;
    static BigInteger b = BigInteger.valueOf(7);
    static ECFieldFp fp;

}
