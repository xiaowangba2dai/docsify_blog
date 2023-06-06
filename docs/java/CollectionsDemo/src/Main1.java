import java.util.ArrayList;
import java.util.Scanner;

public class Main1 {

//    public static ArrayList<Integer> toArrayList(int n) {
//        ArrayList<Integer> res = new ArrayList<>();
//
//        while (n / 10 != 0) {
//            res.add(0, n % 10);
//            n = n / 10;
//        }
//
//        res.add(0, n);
//        return res;
//    }

    public static ArrayList<Integer> toArrayList1(String n) {
        ArrayList<Integer> res = new ArrayList<>();

        for (int i=0; i<n.length(); i++) {
            res.add((int) n.charAt(i) - 48);
        }
        return res;
    }

    public static ArrayList<Integer> method(ArrayList<Integer> list) {
        ArrayList<Integer> res = new ArrayList<>();
        if (list.size() == 0) {
            return res;
        }

        int k = list.get(0); // 总共要删除的个数
        int i = 0;
        for (i=0; i<list.size()-1; i++) {
            if (list.get(i) >= list.get(i+1)) {
                res.add(list.get(i));
            }
            else {
                k--;
                if (k == 0) {
                    i++;
                    break;
                }
            }
        }

        while (i < list.size()) {
            res.add(list.get(i));
            i++;
        }

        return res;
    }

//    public static int toNum(ArrayList<Integer> list) {
//        int res = 0;
//        if (list.size() == 0) {
//            return res;
//        }
//
//        for (int i=0; i<list.size(); i++) {
//            res = (res * 10 + list.get(i));
//        }
//
//        return res;
//    }

    public static String toNum1(ArrayList<Integer> list) {
        String res = "";
        for (int i=0; i<list.size(); i++) {
            res += list.get(i);
        }
        return res;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {//
            String N = in.next();
            // 将数转换为数组
            ArrayList<Integer> arr = toArrayList1(N);
//            System.out.println(arr);
            ArrayList<Integer> newArr = method(arr);
//            System.out.println(newArr);
            System.out.println(toNum1(newArr));
        }
    }
}
