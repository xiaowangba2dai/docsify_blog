import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class baidu {
    public static void buffer(int[] arr) {
        for (int i=0; i<arr.length; i++) {
            for (int j=0; j<arr.length-i-1; j++) {
                if (arr[j] > arr[j+1]) {
                    int t = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = t;
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = {3, 2, 1, 4, 5};
        System.out.println(Arrays.toString(arr));
        buffer(arr);
        System.out.println(Arrays.toString(arr));
    }
}
