import java.util.Arrays;

public class SortDemo {

    public static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public static void heapSortHelp(int[] nums, int i, int lastIndex) {
        if (i > nums.length/2-1 || i >lastIndex) { // 不是非叶子节点, 或者小于可以交换的，直接返回，无需比较
            return;
        }

        int j = i;

        if (i*2+1 <= lastIndex && nums[i*2+1] > nums[j]) { // 不一定有左孩子，比较左孩子
            j = i * 2 + 1;
        }

        if (i*2+2 <= lastIndex && nums[i*2+2] > nums[j]) { // 不一定有右孩子，比较右孩子
            j = i * 2 + 2;
        }

        if (i != j) { // 判断是否需要交换
            swap(nums,i,j);
            if (j == i * 2 + 1) { // 如果交换的是左孩子
                heapSortHelp(nums, i * 2 + 1, lastIndex); // 维护左孩子
            }

            if (j == i * 2 + 2) { // 如果交换的是右孩子
                heapSortHelp(nums, i * 2 + 2, lastIndex); // 维护右孩子
            }
        }
    }

    // 堆排序
    public static void heapSort(int[] nums) {
        // 1. 建堆(大顶堆),从第一个非叶子节点开始向上维护堆，这么做把最大值传送到了堆顶
        int firstNotLeaveNodeIndex = nums.length / 2 - 1;
        for (int i = firstNotLeaveNodeIndex; i>=0; i--) {
            heapSortHelp(nums, i, nums.length-1);
        }

        // 2. 每次用堆顶（第一个）和最后一个元素交换，那么就将最大值交换到后面了，然后进行维护
        int lastIndex = nums.length - 1;
        while (lastIndex > 0) {
            swap(nums,0,lastIndex--);
            heapSortHelp(nums,0, lastIndex);
        }
    }

    public static void main(String[] args) {
        int[] arr = {1,0,2,7,6,3,1,0,2,7,6,3};
        heapSort(arr);
        System.out.println(Arrays.toString(arr));

    }
}
