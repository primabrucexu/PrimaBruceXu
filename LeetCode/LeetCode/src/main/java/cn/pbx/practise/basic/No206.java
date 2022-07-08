package cn.pbx.practise.basic;

import cn.pbx.Algorithm.utils.ListNode;

/**
 * @author Bruce Xu
 * @date 2022/7/8
 */
public class No206 {
    /**
     * 原地反转链表
     *
     * @param head
     * @return
     */
    public ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode current = head;
        ListNode next = null;
        while (current != null) {
            next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }
        return prev;
    }
}
