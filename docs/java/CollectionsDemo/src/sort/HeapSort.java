package sort;

import java.util.*;

public class HeapSort {

    public static int quickSort(int[] arr, int left, int right) {
//        int random_index = random.nextInt(right-left+1);
//        swap(arr, left, random_index);

        int temp = arr[left];
        while (left < right) {
            while (left < right && arr[right] >= temp) {
                right--;
            }
            if (left >= right) {
                break;
            }
            arr[left++] = arr[right];

            while (left < right && arr[left] <= temp) {
                left++;
            }

            if (left >= right) {
                break;
            }
            arr[right--] = arr[left];
        }
        arr[left] = temp;

        return left;
    }

    public static int[] quickSearch(int[] arr, int left, int right, int k) {
        int res = quickSort(arr, left, right);
        if (res == k-1) {
            return Arrays.copyOf(arr, k);
        }
        else if (res > k-1){
            return quickSearch(arr, left, res-1, k);
        }
        else {
            return quickSearch(arr, res+1, right, k);
        }
    }
    public static int[] getLeastNumbers(int[] arr, int k) {
        return quickSearch(arr, 0, arr.length-1, k);
    }

    public static void main(String[] args) {
        int[] nums = {0,0,1,2,4,2,2,3,1,4};
        int[] res = getLeastNumbers(nums,8);
        System.out.println();
    }
}
