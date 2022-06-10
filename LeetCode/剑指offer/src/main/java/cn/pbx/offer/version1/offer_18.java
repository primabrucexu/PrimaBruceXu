package cn.pbx.offer.version1;

import cn.pbx.util.ListNode;

/**
 * @author BruceXu
 * @date 2022/2/14
 */
public class offer_18 {
    public ListNode deleteNode(ListNode head, int val) {
        if (head.val == val) {
            return head.next;
        }
        ListNode prev = head;
        ListNode curr = head;
        while (curr.val != val) {
            prev = curr;
            curr = curr.next;
        }

        prev.next = curr.next;

        return head;
    }
}
