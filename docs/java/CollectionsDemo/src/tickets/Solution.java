package tickets;

import java.util.Arrays;
import java.util.Scanner;

public class Solution {
    // 判断是不是回文串
    public static boolean isHuiwen(String s) {
        for (int i=0,j=s.length()-1; i<s.length()/2; i++,j--) {
            if (s.charAt(i) != s.charAt(j)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();

        for (int i=0; i<n; i++) {
            String s = input.next(); // 读入字符串

            // 如果只有一个字符，直接返回
            if (s.length() <= 1) {
                System.out.println("NO");
                continue;
            }

            char[] arr = new char[s.length()]; // 将字符串转换为数组
            for (int j=0; j<s.length(); j++) {
                arr[j] = s.charAt(j);
            }

            Arrays.sort(arr);

            String newStr = "";

            for (int j=0; j<arr.length; j++) {
                newStr += arr[j];
            }

            if (isHuiwen(newStr)) {
                System.out.println("NO");
            }
            else {
                System.out.println("YES");
            }
        }
    }
}
