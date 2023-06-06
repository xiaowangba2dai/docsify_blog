import java.util.ArrayList;

public class Test1 {

    /**
     * 打印链表
     * @param head
     */
    public static void printListNode(ListNode head) {
        while (head != null) {
            System.out.print((head.val + "->"));
            head = head.next;
        }
        System.out.println("null");
    }

    /**
     * 将链表转换为数组
     * @param head
     * @return
     */
    public static int[] changeListToArr(ListNode head) {
        // 1. 计算链表长度
        int length = 0;
        ListNode p = head;

        while (p != null) {
            length++;
            p = p.next;
        }

        // 2. 定义数组
        int[] res = new int[length];
        p = head;
        int i = 0;
        while (p != null) {
            res[i++] = p.val;
            p = p.next;
        }

        return res;
    }

    /**
     * 两个数组相加
     * @param arr1
     * @param arr2
     * @return
     */
    public static ArrayList<Integer> add(int[] arr1, int[] arr2) {
        ArrayList<Integer> res = new ArrayList<>();

        int jinwei = 0; // 进位标志
        int i = arr1.length - 1; // 数组1的下标
        int j = arr2.length - 1; // 数组2的下标

        while (i >=0 && j >= 0) {
            // 两个对应位置的数相加,再加上当前进位
            int sum = arr1[i] + arr2[j] + jinwei;
            if (sum < 9) { // 如果相加的结果不需要进位
                res.add(sum);
                jinwei = 0;
            }
            else { // 否则
                res.add(sum % 10);
                jinwei = 1;
            }
            i--;
            j--;
        }

        // 当数组1还没走完
        while (i >= 0) {
            int sum = arr1[i] + jinwei;
            if (sum < 9) { // 如果相加的结果不需要进位
                res.add(sum);
                jinwei = 0;
            }
            else { // 否则
                res.add(sum % 10);
                jinwei = 1;
            }
            i--;
        }

        // 当数组2还没走完
        while (j >= 0) {
            int sum = arr2[j] + jinwei;
            if (sum < 9) { // 如果相加的结果不需要进位
                res.add(sum);
                jinwei = 0;
            }
            else { // 否则
                res.add(sum % 10);
                jinwei = 1;
            }
            j--;
        }

        return res;
    }
    /**
     * 对链表的相加
     * @param head1
     * @param head2
     * @return
     */
    public static ListNode addListNode(ListNode head1, ListNode head2) {
        // 1. 将两个链表转换为数组
        int[] arr1 = changeListToArr(head1);
        int[] arr2 = changeListToArr(head2);

        // 2. 两个数组相加
        ArrayList<Integer> res = add(arr1,arr2);

//        System.out.println(res);
        // 3. 转化为链表
        ListNode resListNode = new ListNode();
        ListNode p = resListNode;
        for (int i=res.size()-1; i>=0; i--) {
            ListNode newNode = new ListNode(res.get(i));
            p.next = newNode;
            p = p.next;
        }
//        System.out.println(res);
        return resListNode.next;
    }

    public static void main(String[] args) {
        // 1. 创建两个链表
        ListNode head1 = new ListNode(1); // 链表1头
        ListNode node1 = new ListNode(2);
        ListNode node2 = new ListNode(7);

        head1.next = node1;
        node1.next = node2;
        printListNode(head1); // 打印链表1


        ListNode head2 = new ListNode(1); // 链表2头
        ListNode node4 = new ListNode(2);
        ListNode node5 = new ListNode(3);
        ListNode node6 = new ListNode(4);

        head2.next = node4;
        node4.next = node5;
        node5.next = node6;

        printListNode(head2); // 打印链表2

        // 调用相加函数
        ListNode res = addListNode(head1,head2);

        printListNode(res);

    }


}
class ListNode {
    int val;
    ListNode next;

    public ListNode() {}

    public ListNode(int val) {
        this.val = val;
    }
}
