package cn.pbx.Algorithm.middle;

//import com.pbx.Algorithm.utils.ListNode;

/**
 * @author BruceXu
 * @date 2020/9/26
 */
public class Solution_02_addTwoNumbers {
//    public static void main(String[] args) {
//        ListNode l1 = new ListNode(1);
//        l1.add(2);
//        l1.add(2);
//        ListNode l2 = new ListNode(2);
//        l2.add(3);
//        l2.add(3);
//        System.out.println(addTwoNumbers(l1, l2));
//    }
//
//    /**
//     * 情况1: l1 = 1, l2 = 9->9, result = 0->0->1 （l1，l2不一样长）
//     * 情况2: l1 = 1->1->1, l2 = 8->9->9, result = 0->0->0->1 （l1，l2一样长，但最后加完还要进位）
//     * 情况3：l1 = 2->4->3, l2 = 5->6->4, result = 7->0->8
//     */
//    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
//        ListNode newHead = new ListNode(0);
//        int flag = 0;
//        ListNode curr = newHead;
//        while (l1 != null || l2 != null) {
//            int x = l1 != null ? l1.val : 0;
//            int y = l2 != null ? l2.val : 0;
//            int add = x + y + flag;
//            if (add >= 10) {
//                curr.next = new ListNode(add - 10);
//                flag = 1;
//            } else {
//                curr.next = new ListNode(add);
//                flag = 0;
//            }
//            if (l1!=null) l1 = l1.next;
//            if (l2!=null) l2 = l2.next;
//            curr = curr.next;
//        }
//        if (flag == 1) {
//            curr.next = new ListNode(flag);
//        }
//        return newHead.next;
//    }
}
