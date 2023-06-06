//import java.util.Arrays;
//
//public class Test {
//
//    /**
//     * 合并两个链表
//     * @param head1
//     * @param head2
//     * 返回链表头节点
//     */
//    public static ListNode merge(ListNode head1, ListNode head2) {
//        // 1. 定义一个头节点
//        ListNode head = new ListNode();
//        ListNode p = head;
//
//        // 2. 进行遍历，将整个链表串起来
//        while (head1 != null && head2 != null) {
//            if (head1.val <= head2.val) { // 如果链表1的val< 链表2的val
//                p.next = head1;
//                head1 = head1.next;
//            }
//            else { // 否则
//                p.next = head2;
//                head2 = head2.next;
//            }
//            p = p.next; // 更新当前指针
//        }
//
//        // 3. 链表1 或 2 为空的情况
//        if (head1 != null) {
//            p.next = head1;
//        }
//
//        if (head2 != null) {
//            p.next = head2;
//        }
//        return head.next;
//    }
//
//    /**
//     * 排序链表
//     * @param head
//     * @return
//     */
//    public static ListNode sortListNode(ListNode head) {
//        if (head == null) {
//            return null;
//        }
//
//        // 0. 定义返回头节点
//        ListNode res = null;
//
//        // 1. 先获取链表长度
//        int length = 0;
//        ListNode p = head;
//        while (p != null) {
//            length++;
//            p = p.next;
//        }
//
//        // 2. 新建一个数组
//        int[] arr = new int[length];
//        p = head;
//        int i = 0;
//        while (p != null) {
//            arr[i++] = p.val;
//            p = p.next;
//        }
//
//        // 3. 数组进行排序
//        Arrays.sort(arr);
//
//        // 4. 建立排序后的链表
//        res = new ListNode(arr[0]);
//        p = res;
//        for (int j=1; j<arr.length; j++) {
//            ListNode newNode = new ListNode(arr[j]);
//            p.next = newNode;
//            p = p.next;
//        }
//
//        return res;
//
//    }
//
//    /**
//     * 打印链表
//     * @param head
//     */
//    public static void printListNode(ListNode head) {
//        System.out.println(head);
//        while (head != null) {
//            System.out.print((head.val + "->"));
//            head = head.next;
//        }
//        System.out.println("null");
//    }
//
//    public static void main(String[] args) {
//        // 1. 创建两个链表
//        ListNode head1 = new ListNode(3); // 链表1头
//        ListNode node1 = new ListNode(1);
//        ListNode node2 = new ListNode(2);
//        ListNode node3 = new ListNode(7);
//
//        head1.next = node1;
//        node1.next = node2;
//        node2.next = node3;
//        System.out.println(head1);
//        printListNode(head1); // 打印链表1
//        System.out.println(head1);
//        ListNode head11 = sortListNode(head1);
//        printListNode(head11); // 打印排序后的链表1
//
//
//        ListNode head2 = new ListNode(2); // 链表2头
//        ListNode node4 = new ListNode(1);
//        ListNode node5 = new ListNode(3);
//        ListNode node6 = new ListNode(8);
//
//        head2.next = node4;
//        node4.next = node5;
//        node5.next = node6;
//
//        printListNode(head2); // 打印链表2
//        ListNode head22 = sortListNode(head2);
//        printListNode(head22); // 打印链排序后的链表2
//
//        ListNode res = merge(head11,head22);
//        printListNode(res); // 打印合并的链表
//
//    }
//}
//
//class ListNode {
//    int val;
//    ListNode next;
//
//    public ListNode() {}
//
//    public ListNode(int val) {
//        this.val = val;
//    }
//}