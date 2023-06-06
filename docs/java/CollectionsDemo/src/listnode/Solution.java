package listnode;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;

/**
 * Definition for singly-linked list.
 *
 **/
class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}
class Solution {
    public ListNode partition(ListNode head, int x) {
        ListNode res = new ListNode();
        ListNode q = res;
        ListNode r = null; // 小于x的最后一个节点
        ListNode p = head;

        while (p != null) {
            if (p.val < x) { // 如果当前节点的值小于x
                if (r == null) {
                    r = p;
                    p = p.next;
                    r.next = null;
                    if (q == res || res.next.val >= x) { // 如果是链表第一个小于x的节点
                        ListNode t = res.next;
                        res.next = r;
                        r.next = t;
                        if (q == res) {
                            q = r;
                        }
                    }
                }
                else { // 插入
                    ListNode t = r.next;
                    ListNode p_next = p.next;
                    r.next = p;
                    p.next = t;
                    r = r.next;
                    p = p_next;
                }
            }
            else {
                q.next = p;
                p = p.next;
                q = q.next;
                q.next = null;
            }
        }
        return res.next;
    }

    public static void main(String[] args) {
        ListNode a = new ListNode(1);
        ListNode b = new ListNode(4);
        ListNode c = new ListNode(3);
        ListNode d = new ListNode(0);
        ListNode e = new ListNode(2);
        ListNode f = new ListNode(5);
        ListNode g = new ListNode(2);

        a.next= b;
        b.next = c;
        c.next = d;
        d.next = e;
        e.next = f;
        f.next = g;

        Solution s = new Solution();
        s.partition(a,3);
    }
}
