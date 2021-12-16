package cn.pbx.part2;

import cn.pbx.Algorithm.utils.ListNode;

/**
 * @author BruceXu
 * @date 2020/11/8
 */
public class _141 {
    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        ListNode fast = head.next.next;
        ListNode slow = head.next;
        while (fast != slow && fast != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return fast == slow;
    }
}
