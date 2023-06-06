以Arrays.sort(int[]) 为例，看源码如何选择排序算法 (jdk 1.8 版本)

首先，进入第一个函数

```java

/**
* @param a the array to be sorted
*/
public static void sort(int[] a) {
        DualPivotQuicksort.sort(a, 0, a.length - 1, null, 0, 0);
}
```
这个函数调用了`DualPivotQuicksort.sort()`，点进去看看。

```java
/**
* Sorts the specified range of the array using the given
* workspace array slice if possible for merging
*
* @param a : 待排序数组
* @param left : 待排序数组的left
* @param right : 待排序数组的right
* @param work a workspace array (slice)
* @param workBase origin of usable space in work array
* @param workLen usable size of work array
*/
static void sort(int[] a, int left, int right,
                 int[] work, int workBase, int workLen) {
    // Use Quicksort on small arrays 使用快速排序对小数组排序
    if (right - left < QUICKSORT_THRESHOLD) { // QUICKSORT_THRESHOLD = 286;
        sort(a, left, right, true);
        return;
    }

    /**
     * 评估数组的无序程度
     * 将数组分割成若干个递增或递减的子数组
     * run数组存储每一个子数组的开始下标
     * Index run[i] is the start of i-th run
     * (ascending or descending sequence).
     */
    int[] run = new int[MAX_RUN_COUNT + 1];
    int count = 0; run[0] = left;

    // Check if the array is nearly sorted
    for (int k = left; k < right; run[count] = k) {
        if (a[k] < a[k + 1]) { // ascending
            while (++k <= right && a[k - 1] <= a[k]);
        } else if (a[k] > a[k + 1]) { // descending
            while (++k <= right && a[k - 1] >= a[k]);
            for (int lo = run[count] - 1, hi = k; ++lo < --hi; ) {
                int t = a[lo]; a[lo] = a[hi]; a[hi] = t;
            }
        } else { // equal
            for (int m = MAX_RUN_LENGTH; ++k <= right && a[k - 1] == a[k]; ) {
                if (--m == 0) {
                    sort(a, left, right, true);
                    return;
                }
            }
        }

        /*
         * 如果run数组元素个数大于常量MAX_RUN_COUNT,认为整个数组基本上是无序的，调用改进的快速排序，否则利用归并排序
         * The array is not highly structured,
         * use Quicksort instead of merge sort.
         */
        if (++count == MAX_RUN_COUNT) {
            sort(a, left, right, true);
            return;
        }
    }

    // Check special cases
    // Implementation note: variable "right" is increased by 1.
    if (run[count] == right++) { // The last run contains one element
        run[++count] = right;
    } else if (count == 1) { // The array is already sorted
        return;
    }

    // Determine alternation base for merge
    byte odd = 0;
    for (int n = 1; (n <<= 1) < count; odd ^= 1);

    // Use or create temporary array b for merging
    int[] b;                 // temp array; alternates with a
    int ao, bo;              // array offsets from 'left'
    int blen = right - left; // space needed for b
    if (work == null || workLen < blen || workBase + blen > work.length) {
        work = new int[blen];
        workBase = 0;
    }
    if (odd == 0) {
        System.arraycopy(a, left, work, workBase, blen);
        b = a;
        bo = 0;
        a = work;
        ao = workBase - left;
    } else {
        b = work;
        ao = 0;
        bo = workBase - left;
    }

    // Merging
    for (int last; count > 1; count = last) {
        for (int k = (last = 0) + 2; k <= count; k += 2) {
            int hi = run[k], mi = run[k - 1];
            for (int i = run[k - 2], p = i, q = mi; i < hi; ++i) {
                if (q >= hi || p < mi && a[p + ao] <= a[q + ao]) {
                    b[i + bo] = a[p++ + ao];
                } else {
                    b[i + bo] = a[q++ + ao];
                }
            }
            run[++last] = hi;
        }
        if ((count & 1) != 0) {
            for (int i = right, lo = run[count - 1]; --i >= lo;
                 b[i + bo] = a[i + ao]
                );
            run[++last] = right;
        }
        int[] t = a; a = b; b = t;
        int o = ao; ao = bo; bo = o;
    }
}
```

首先检查数组的大小

（1）如果数组比较小：小于`QUICKSORT_THRESHOLD`，则直接调用改进后的快速排序完成排序。

（2）如果数组较大：评估数组的无序程度

- （2.1） 如果这个数组几乎是无序的（通过与阈值`MAX_RUN_COUNT`比较），那么同样调用改进后的快速排序算法；
- （2.2）如果数组基本有序，则采用归并排序算法对数组进行排序。