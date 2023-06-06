import java.util.*;

/**
 * Arrays工具类得使用
 */

public class ArraysDemo {
    public static void main(String[] args) {
        int[] arr = {3, 4, 6, 7, 2, 1};

        // 1. 将数组转化为字符串
        String res = Arrays.toString(arr);
        System.out.println(res);

        // 2.1 升序排序（默认）
        Arrays.sort(arr);
        System.out.println(Arrays.toString(arr));

        // 2，2 降序排序
        Integer[] arr3 = {3, 4, 6, 7, 2, 1};
        Arrays.sort(arr3, new Comparator<Integer>(){
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });
        System.out.println(Arrays.toString(arr3));

        // 3. fill 填充
        int[] arr1 = new int[10];
        Arrays.fill(arr1, 1);
        System.out.println(Arrays.toString(arr1));

        // 4. 复制
        // (1). 完整复制，可以规定新数组的长度
        int[] arr2 = Arrays.copyOf(arr1, 5);
        System.out.println(Arrays.toString(arr2));

        // （2） 原数组范围复制
        int[] arr4 = Arrays.copyOfRange(arr1, 1, 3);
        System.out.println(Arrays.toString(arr4));

        // （3）原数组新数组都有范围
        int[] arr5 = new int[10];
        arr5[0] = 9;
        System.arraycopy(arr1, 1,arr5, 2, 2);
        System.out.println(Arrays.toString(arr5));

        // 5. 变成List
        List<Integer> list = Arrays.asList(arr3);
        ArrayList<Integer> list1 = new ArrayList<>(list);
        list1.add(8);
        System.out.println(list1);
    }
}
