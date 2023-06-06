package tickets;

import java.util.Scanner;
public class Main2 {

    // 不使用魔法键
    public static int noMagic(String str, int startIndex) {
        int res = 1;
        // 如果是最后一位了，直接返回1， 确定键
        if (startIndex >= str.length()-1) {
            return res;
        }

        // 计算到下一步的距离
        for (int i=startIndex; i<str.length()-1; i++) {
            // 如果下一个字符和当前的字符一样，则直接按确定键
            int min1 = str.charAt(i+1) - str.charAt(i); // 正着走
            if (min1 == 0) {
                res++;
                continue;
            }
            if (min1 < 0) {
                min1 = -min1;
            }
            int min2 = str.charAt(i) - 'A' + 'Z' - str.charAt(i+1) + 1;
            if (min2 < 0) {
                min2 = -min2;
            }
            // 比较两种走法，移动最少步数
            res += Math.min(min1, min2);

            // 按确定键
            res += 1;
        }

        return res;
    }

    // 按下魔法键
    public static int startMagic(String str, int m, int startIndex) {

        // 如果已经用了。不能再用
//        if (isuse == true) {
//            return Integer.MAX_VALUE;
//        }
        // 如果是最后一个字符，无需按
        if (startIndex >= str.length()-1) {
            return 0;
        }
        int tmp_m = m;

        int index = startIndex + 1; // 新的位置
        int res = 2; // 按下魔法键需要+1, 按确定键需要+1
        tmp_m--; // 魔法键的使用次数--

        // 接下来必须连续按魔法键
        while (tmp_m > 0 && index < str.length()-1) {
            res += 2;
            tmp_m--;
            index++;
        }

        // 如果当前已经是最后一位，前面已经按了确定键了，直接返回
        if (index >= str.length()-1) {
            return res;
        }
        else {
            // 如果不是最后一位，需要继续走
            res += noMagic(str,index);
        }

        // 魔法键用完了，如果还没结束，需要继续走
        return res;
    }

    // 这一步，不按下魔法键
    public static int startNoMagic(String str, int m, int startIndex) {
//        if (isuse == true) {
//            return Integer.MAX_VALUE;
//        }
        int res = 1; // 点确认

        // 如果当前是最后一个字符,直接确认
        if (startIndex >= str.length()-1) {
            return res;
        }

        // 当前不走magic，需要找到移动到下一步的最小路径
        int min1 = str.charAt(startIndex+1) - str.charAt(startIndex); // 正着走
        if (min1 < 0) {
            min1 = -min1;
        }
        int min2 = str.charAt(startIndex) - 'A' + 'Z' - str.charAt(startIndex+1) + 1;
        if (min2 < 0) {
            min2 = -min2;
        }

        res += Math.min(min1,min2); // 移动到下一步

        // 下一步有两种走法
        // 不走魔法路
        int res2 = startMagic(str,m,startIndex+1);
        int res1 = startNoMagic(str, m, startIndex+1);
        return res + Math.min(res1, res2);
    }

    public static void main(String[] args) {
        int res;
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {// 注意，如果输入是多个测试用例，请通过while循环处理多个测试用例
            String str = in.next();
            int m = in.nextInt();

            // 有两种走法，一种是开始走魔法，一种是不走
            int noMagic = startNoMagic(str, m,0);
            int magic = startMagic(str, m, 0);

            res = Math.min(noMagic,magic);
            System.out.println(res);
//            System.out.println(noMagic(str, 0));
//            System.out.println(startMagic(str, m, 0));
        }
    }
}