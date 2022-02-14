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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        ListNode node = this;
        boolean first = true;
        while (node != null) {
            if (!first) {
                sb.append("->");
            }
            sb.append(node.val);
            node = node.next;
        }
        return sb.toString();
    }
}
