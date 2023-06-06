class Solution {

    int res = 0;

    public void merge(int[] nums, int left, int mid, int right) {
        if (left >= right) {
            return;
        }

        int[] temp = new int[right-left+1];
        int index = 0;

        int index1 = left;
        int index2 = mid + 1;

        while (index1 <= mid && index2 <= right) {
            if (nums[index1] <= nums[index2]){
                temp[index++] = nums[index1++];
            }
            else { // 如果左边的数大于右边的数，那么意味者左边右边所有的数都大于右边的这个数
                temp[index++] = nums[index2++];
                res += (mid-index1+1);
            }
        }

        while (index1 <= mid) {
            temp[index++] = nums[index1++];
        }

        while (index2 <= right) {
            temp[index++] = nums[index2++];
        }

        index = 0;
        for (int i=left; i<=right; i++) {
            nums[i] = temp[index++];
        }
    }

    public void mergeSort(int[] nums, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(nums, left, mid);
            mergeSort(nums, mid+1, right);
            merge(nums, left, mid, right);
        }
    }

    public int reversePairs(int[] nums) {
        mergeSort(nums, 0, nums.length-1);
        return res;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        int[] nums = {7, 5, 6, 4};
        s.reversePairs(nums);
        for (int i=0; i<nums.length; i++) {
            System.out.println(nums[i]);
        }
    }
}