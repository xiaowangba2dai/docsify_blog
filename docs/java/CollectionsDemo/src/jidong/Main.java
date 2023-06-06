package jidong;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        while (in.hasNextInt()) {
            int n = in.nextInt();
            int k = in.nextInt();
            int d = in.nextInt();
            int[] a = new int[n];
            int[] b = new int[n];
            int[] c = new int[n];

            for (int i=0; i<n; i++) {
                a[i] = in.nextInt();
            }

            for (int i=0; i<n; i++) {
                b[i] = in.nextInt();
            }

            for (int i=0; i<n; i++) {
                c[i] = in.nextInt();
            }

            // 喜爱值排名
            int[] a_copy = Arrays.copyOf(a,a.length);
            int[] love_rank = new int[n];
            Arrays.sort(a_copy);

            for (int i=0; i<n; i++) {
                for (int j=0; j<n; j++) {
                    if (a[i] == a_copy[j]) {
                        love_rank[i] = j+1;
                        break;
                    }
                }
            }

            // 喂养预算
            int[] weiyangyusuan = new int[n];
            for (int i=0; i<n; i++) {
                weiyangyusuan[i] = d/n * love_rank[i];
            }

            // 领养预算能够支持的喜爱值
            boolean find = false;
            int cur_love = -1;
            for (int i=n-1; i>=0; i--) {
                if (k >= b[i]) { // 可以领养
                    if (weiyangyusuan[i] >= c[i]) {
                        if (a[i] > cur_love) {
                            cur_love = a[i];
                        }
//                        System.out.println((i + 1));
                        find = true;
//                        break;
                    }
                }
            }

            if (find == false) {
                System.out.println((-1));
            }
            else {
                System.out.println(cur_love);
            }
        }
    }
}
