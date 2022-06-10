package cn.pbx.offer;

import java.util.*;

/**
 * @author BruceXu
 * @date 2022/6/10
 */
public class offer_36 {

    public static void main(String[] args) {
        offer_36 demo = new offer_36();
        Node root = new Node(4);
        root.left = new Node(2);
        root.right = new Node(5);
        root.left.left = new Node(1);
        root.left.right = new Node(3);

        demo.treeToDoublyList(root);
    }

    public Node treeToDoublyList(Node root) {
        if (root == null) {
            return null;
        }
        List<Node> inorder = inorder(root);
        int size = inorder.size();
        for (int i = 0; i < size; i++) {
            Node current = inorder.get(i);
            Node predecessor = i == 0 ? inorder.get(size - 1) : inorder.get(i - 1);
            Node successor = i == size - 1 ? inorder.get(0) : inorder.get(i + 1);
            current.left = predecessor;
            current.right = successor;
        }

        return inorder.get(0);
    }

    private List<Node> inorder(Node root) {
        if (root == null) {
            return Collections.emptyList();
        }
        List<Node> list = new ArrayList<>();
        Deque<Node> deque = new LinkedList<>();
        Node node = root;
        while (!deque.isEmpty() || node != null) {
            while (node != null) {
                deque.addLast(node);
                node = node.left;
            }
            node = deque.removeLast();
            list.add(node);
            node = node.right;
        }
        return list;
    }

    static class Node {
        public int val;
        public Node left;
        public Node right;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _left, Node _right) {
            val = _val;
            left = _left;
            right = _right;
        }
    }

}
