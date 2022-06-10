package cn.pbx.offer;

import cn.pbx.util.Node;

import java.util.HashMap;
import java.util.Map;

/**
 * @author BruceXu
 * @date 2022/6/10
 */
public class offer_35 {

    public static void main(String[] args) {
        Map<Integer, Node> map = new HashMap<>();
        Node s = map.get(0);
        System.out.println(s);
        map.put(0, new Node(2));
        System.out.println(s);
    }


    public Node copyRandomList(Node head) {
        if (head == null) {
            return null;
        }
        Map<Node, Node> map = new HashMap<>();

        int index = 0;
        Node old = head;
        Node newHead = new Node(10001);
        Node node = newHead;
        while (old != null) {
            // 新链表填充值
            node.val = old.val;

            // 建立旧链表和新链表之间元素的对应关系
            map.put(old, node);

            // 后移
            old = old.next;

            // 创建新链表的下一个节点，这里可能会产生一个多余的占位节点，所以在移动到末尾的时候，不要加上
            if (old != null) {
                node.next = new Node(10001);
                node = node.next;
            }
        }

        // 重置两个游标
        old = head;
        node = newHead;

        // 遍历旧链表，填充新链表的random
        while (old != null) {
            if (old.random != null) {
                node.random = map.get(old.random);
            }
            old = old.next;
            node = node.next;
        }

        return newHead;
    }

}
