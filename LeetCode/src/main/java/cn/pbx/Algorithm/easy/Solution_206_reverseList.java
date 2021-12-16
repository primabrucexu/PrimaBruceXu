package cn.pbx.Algorithm.easy;

import cn.pbx.Algorithm.utils.ListNode;

/**
 * https://leetcode-cn.com/problems/reverse-linked-list/
 *
 * @author BruceXu
 * @date 2020/9/26
 */
public class Solution_206_reverseList {

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.add(2);
        head.add(3);
        head.add(4);
        System.out.println(head);
        System.out.println(reverseList(head));
    }

    public static ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode next = head.next;
        ListNode newHead = reverseList(head.next);
        // 逆向连接head和head.next
        next.next = head;
        head.next = null;
        return newHead;
    }

    public static ListNode reverseList01(ListNode head) {
        ListNode curr = head;
        ListNode prev = null;
        while (curr != null) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }
}
