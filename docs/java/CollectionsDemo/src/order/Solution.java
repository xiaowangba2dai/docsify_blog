package order;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;


public class Solution {
    public double pow(double x, int n) {
        double res = 1;
        if (n == 0) {
            return res;
        }

        double ban = pow(x, n/2);
        if (n % 2 == 0) {
            return ban * ban;
        }

        if (n > 0) {
            return ban * ban * x;
        }

        return ban * ban / x;
    }

    public String pow(String x, int n) {
        // 获取精度
        int jindu = 0;
        int i = 0;
        for (i=0; i<x.length(); i++) {
            if (x.charAt(i) == '.') {
                i++;
                break;
            }
        }
        while (i < x.length()) {
            jindu++;
            i++;
        }

//        System.out.println(jindu);
        double newx = Double.valueOf(x);
        double res = pow(newx,n);
        if (res == Double.MAX_VALUE || res == Double.MIN_VALUE) {
            return String.format("%."+jindu+"f", 0);
        }
//        NumberFormat nf = NumberFormat.getNumberInstance();
//        nf.setMaximumFractionDigits(5);
//        nf.setRoundingMode(RoundingMode.UP);
        String ret = String.format("%."+jindu+"f",res);
//        DecimalFormat df = new DecimalFormat("#.00000");
//        String ret = df.format(res);
//        System.out.println(newx);
        return ret;
    }

    public static void main(String[] args) {
        int[] arrs = {6,4,5,1};
        Solution s = new Solution();
        System.out.println(s.pow("2.00000", -3));
    }
}
