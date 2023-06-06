import java.util.ArrayList;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    // 能走到第几个城市
    public static int far(int n, int x, int[] h) {
        // 先爬上第一个城市
        if (x < h[0]) {
            return 0;
        }
        int i = 1;
        for (i=1; i<n; i++) {
            // 每一天
            if (h[i] > h[i-1]+x) {
                return i-1;
            }
        }
        return i - 1;
    }
    /**
     *
     * @param n
     * @param m
     * @param x
     * @param h
     * @param xi
     * @return
     */
    public static ArrayList<Integer> method(int n, int m, int x, int[] h, int[] xi) {
        ArrayList<Integer> res = new ArrayList<>();
        int xiaoqi = far(n,x,h);
//        System.out.println(xiaoqi);
        // 离下一个城市有多远

        for (int i=0; i<m; i++) {
//            System.out.println(far(n, xi[i], h));
            if (far(n,xi[i],h) == xiaoqi) {
                res.add(i+1);
            }
        }
        return res;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {// 注意，如果输入是多个测试用例，请通过while循环处理多个测试用例
            int n = in.nextInt(); // 城市的个数
            int m = in.nextInt(); // 朋友的个数
            int x = in.nextInt(); // 小七的体力值
            int[] h = new int[n]; // 城市的高度
            int[] xi = new int[m]; // 朋友的体力值

            for (int i=0; i<n; i++) {
                h[i] = in.nextInt();
            }

            for (int i=0; i<m; i++) {
                xi[i] = in.nextInt();
            }

            ArrayList<Integer> res = method(n,m,x,h,xi);
            for (int i=0; i<res.size(); i++) {
                System.out.print((res.get(i))+ " ");
            }
            System.out.println((""));
        }
    }
}