package tickets;

import java.util.Scanner;

public class Main3 {
    // 正递归
    public static int digui(int num) {
        int res = -1; // -1代表无解

        if (num == 0) {
            return 0;
        }

        for (int i=num; i>=0; i--) {
            int cur = 0;
            if (Math.pow(2,i) > num) {
                continue;
            }
            else {
                int next = digui((int) (num-Math.pow(2,i)));
                if (next != -1) {
                    cur = 1 + next;
                }
            }

            if (res == -1) {
               if (cur != 0) {
                   res = cur;
                   break;
               }
            }
            else {
                res = cur < res ? cur : res;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextInt()) {// 注意，如果输入是多个测试用例，请通过while循环处理多个测试用例
            int num = in.nextInt();
            int res1 = digui(num);
            int res2 = Integer.MAX_VALUE;
            // 找到第一个比Num大
            for (int i=num; i>=1; i--) {
                if (Math.pow(2,i) > num && Math.pow(2,i-1) < num) {
                    int newNum = (int) (Math.pow(2,i) - num);
                    res2 = digui(newNum) + 1;
                    break;
                }
            }

            int res = Math.min(res1,res2);
            System.out.println(res);
        }
    }
}
