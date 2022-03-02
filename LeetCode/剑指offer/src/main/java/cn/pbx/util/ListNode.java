package cn.pbx.util;

/**
 * @author BruceXu
 * @date 2022-01-15
 */
public class ListNode {
    public int val;
    public ListNode next;

    public ListNode(int x) {
        val = x;
    }

    public void add(int x) {
        ListNode node = this;
        while (node.next != null) {
            node = node.next;
        }
        node.next = new ListNode(x);
    }

    @Override
    public String toString() {
        return next != null ? val + "->" + next.val : val + "->" + "null";
    }

    public String display() {
        StringBuilder sb = new StringBuilder();
        ListNode node = this;
        boolean first = true;
        while (node != null) {
            if (!first) {
                sb.append("->");
            }
            first = false;
            sb.append(node.val);
            node = node.next;
        }
        return sb.toString();
    }
}
