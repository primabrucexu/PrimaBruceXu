package cn.pbx.offer;

import cn.pbx.util.ListNode;

/**
 * @author BruceXu
 * @date 2022/3/2
 */
public class offer_24 {

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.add(2);
        head.add(3);
        head.add(4);
        head.add(5);
        System.out.println(head.display());

        offer_24 demo = new offer_24();
        ListNode newHead = demo.reverseList(head);
        System.out.println(newHead.display());
    }

    public ListNode reverseList(ListNode head) {
        ListNode curr = head;
        ListNode prev = null;
        while (curr != null) {
            // 保存当前节点的下一个节点
            ListNode next = curr.next;

            // 改变当前节点的next指向
            curr.next = prev;

            // 记录当前节点为下一个节点的前置节点
            prev = curr;

            // 当前节点往后移动
            curr = next;
        }
        return prev;
    }
}
