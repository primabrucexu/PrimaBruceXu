package cn.pbx.offer.version1;

import cn.pbx.util.ListNode;

/**
 * @author BruceXu
 * @date 2022/3/2
 */
public class offer_25 {
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }

        ListNode newHead;
        if (l1.val < l2.val) {
            newHead = l1;
            l1 = l1.next;
        } else {
            newHead = l2;
            l2 = l2.next;
        }

        ListNode node = newHead;
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                node.next = l1;
                l1 = l1.next;
            } else {
                node.next = l2;
                l2 = l2.next;
            }
            node = node.next;
        }
        if (l1 == null) {
            node.next = l2;
        }
        if (l2 == null) {
            node.next = l1;
        }

        return newHead;
    }
}
