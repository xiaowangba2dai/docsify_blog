package tickets;

import java.util.Scanner;

public class Main1 {

    public static Character getChar(char c, int count) {
        if (c == '2') {
            if (count % 3 == 1) {
                return 'a';
            }
            else if (count % 3 == 2) {
                return 'b';
            }
            else {
                return 'c';
            }
        }
        else if (c == '3'){
            if (count % 3 == 1) {
                return 'd';
            }
            else if (count % 3 == 2) {
                return 'e';
            }
            else {
                return 'f';
            }
        }
        else if (c == '4'){
            if (count % 3 == 1) {
                return 'g';
            }
            else if (count % 3 == 2) {
                return 'h';
            }
            else {
                return 'i';
            }
        }
        else if (c == '5'){
            if (count % 3 == 1) {
                return 'j';
            }
            else if (count % 3 == 2) {
                return 'k';
            }
            else {
                return 'l';
            }
        }
        else if (c == '6'){
            if (count % 3 == 1) {
                return 'm';
            }
            else if (count % 3 == 2) {
                return 'n';
            }
            else {
                return 'o';
            }
        }
        else if (c == '7'){
            if (count % 4 == 1) {
                return 'p';
            }
            else if (count % 4 == 2) {
                return 'q';
            }
            else if (count % 4 == 3){
                return 'r';
            }
            else {
                return 's';
            }
        }
        else if (c == '8'){
            if (count % 3 == 1) {
                return 't';
            }
            else if (count % 3 == 2) {
                return 'u';
            }
            else {
                return 'v';
            }
        }
        else if (c == '9'){
            if (count % 4 == 1) {
                return 'w';
            }
            else if (count % 4 == 2) {
                return 'x';
            }
            else if (count % 4 == 3){
                return 'y';
            }
            else {
                return 'z';
            }
        }

        return null;
    }

    public static String help(String s) {
        String res = "";
        // 遍历s
        for (int i=0; i<s.length(); i++) {
            char c = s.charAt(i); // 得到当前字符
            if (c == '0') {
                res += '-';
            } else if (c == '-'){
                continue;
            } else { // 1 - 9
                // 判断后面有多少个相同的字符
                int count = 1;
                int gongke_count = 0;
                for (int j=i+1; j<s.length(); j++) {
                    char c1 = s.charAt(j); // 得到字符

                    if (c1 == c) { // 字符与原字符相等
                        count++;
                        i++;
                        gongke_count = 0;
                    }
                    else if (c1 == '-') { // 字符是空格
                        gongke_count++;
                        i++;
                        if (gongke_count == 2) { // 如果宫格达到两个，刷新键盘
//                            count = 1;
                            break;
                        }
                    }
                    else { // 都不是，退出
                        break;
                    }
                }

                // 退出循环后判断当前应该得到的字符
                char getc = getChar(c,count);
                res += getc;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextInt()) {// 注意，如果输入是多个测试用例，请通过while循环处理多个测试用例
            int num = in.nextInt();
            // 找到第一个比Num大
            for (int i=num; i>=1; i--) {
                if (Math.pow(2,i) > num && Math.pow(2,i-1) < num) {
                    int newNum = (int) (Math.pow(2,i) - num);

                    break;
                }
            }
        }
    }
}
