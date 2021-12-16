package cn.pbx.Algorithm.easy;

import cn.pbx.Algorithm.utils.ListNode;

/**
 * https://leetcode-cn.com/problems/merge-two-sorted-lists/
 *
 * @author BruceXu
 * @date 2020/9/26
 */
public class Solution_21_mergeTwoLists {

    public static void main(String[] args) {
        ListNode l1 = new ListNode(1);
        l1.add(3);
        l1.add(5);
        ListNode l2 = new ListNode(2);
        l2.add(3);
        l2.add(6);

        System.out.println(mergeTwoLists(l1, l2));

    }

    public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode newHead = new ListNode(0);

        ListNode temp = newHead;
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                temp.next = l1;
                l1 = l1.next;
            } else {
                temp.next = l2;
                l2 = l2.next;
            }
            temp = temp.next;
        }
        // 拼接剩余部分
        temp.next = l1 == null ? l2 : l1;
        return newHead.next;
    }
}
