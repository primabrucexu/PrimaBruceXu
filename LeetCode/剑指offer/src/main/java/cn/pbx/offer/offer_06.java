package cn.pbx.offer;

import cn.pbx.util.ListNode;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 因为要返回的是数组而不是链表，所以最少要扫描链表两次，一次翻转链表，一次填充数组
 *
 * @author BruceXu
 * @date 2022-01-15
 */
public class offer_06 {
    public int[] reversePrint(ListNode head) {
        if (head == null) {
            return new int[0];
        }
        Deque<Integer> list = new LinkedList<>();
        while (head != null) {
            list.addLast(head.val);
            head = head.next;
        }
        int[] res = new int[list.size()];
        for (int i = 0; i < res.length; i++) {
            res[i] = list.removeLast();
        }
        return res;
    }
}
