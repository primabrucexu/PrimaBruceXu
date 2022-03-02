package cn.pbx.offer;

import cn.pbx.util.ListNode;

/**
 * @author BruceXu
 * @date 2022/3/2
 */
public class offer_22 {
    public ListNode getKthFromEnd(ListNode head, int k) {
        ListNode p1 = head;
        ListNode p2 = head;
        // 两个指针的间隔。当p1移动到末尾时，p2所在的位置就是倒数第k个元素
        int gap = k - 1;
        while (p1.next != null) {
            p1 = p1.next;
            if (gap > 0) {
                gap--;
            } else {
                p2 = p2.next;
            }
        }
        return p2;
    }
}
