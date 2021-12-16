package cn.pbx.Algorithm.utils;

/**
 * @author BruceXu
 * @date 2020/9/26
 */
public class ListNode {
    public int val;
    public ListNode next;

    public ListNode(int x, ListNode node) {
        val = x;
        next = node;
    }

    public ListNode(int x) {
        val = x;
        next = null;
    }

    public void add(int val) {
        ListNode p = this;
        while (p.next != null) {
            p = p.next;
        }
        p.next = new ListNode(val);
    }

    public void add(ListNode node) {
        ListNode p = this;
        while (p.next != null) {
            p = p.next;
        }
        p.next = node;
    }

    public void setNext(ListNode node) {
        this.next = node;
    }

    @Override
    public String toString() {
        return val + "";
    }
}
