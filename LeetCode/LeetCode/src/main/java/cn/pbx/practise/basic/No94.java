package cn.pbx.practise.basic;

import cn.pbx.Algorithm.utils.TreeNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * @author Bruce Xu
 * @date 2022/7/8
 */
public class No94 {
    public static void main(String[] args) {
        No94 demo = new No94();
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.left = new TreeNode(6);
        System.out.println(demo.inorderTraversal(root));

    }

    /**
     * 二叉树的中序遍历
     *
     * @param root
     * @return
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        Deque<TreeNode> deque = new ArrayDeque<>();
        TreeNode node = root;
        while (!deque.isEmpty() || node != null) {
            while (node != null) {
                deque.addLast(node);
                node = node.left;
            }
            node = deque.removeLast();
            list.add(node.val);
            node = node.right;
        }

        return list;
    }
}
