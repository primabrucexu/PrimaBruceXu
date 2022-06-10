package cn.pbx.offer;

import cn.pbx.util.TreeNode;

import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author BruceXu
 * @date 2022/6/10
 */
public class offer_34 {
    public static void main(String[] args) {
        offer_34 demo = new offer_34();
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(-2);
        root.right = new TreeNode(-3);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(3);
        root.right.left = new TreeNode(-2);
        root.left.left.left = new TreeNode(-1);

        int target = -1;
        System.out.println(demo.pathSum(root, target));
    }

    public List<List<Integer>> pathSum(TreeNode root, int target) {
        if (root == null) {
            return Collections.emptyList();
        }
        List<List<Integer>> res = new LinkedList<>();
        int current = root.val;
        Deque<TreeNode> deque = new LinkedList<>();
        deque.addLast(root);
        dfs(deque, res, current, target);

        return res;
    }

    private void dfs(Deque<TreeNode> deque, List<List<Integer>> res, int current, int target) {
        TreeNode node = deque.getLast();
        if (current == target && node.right == null && node.left == null) {
            res.add(deque.stream().map(o -> o.val).collect(Collectors.toList()));
            return;
        }

        if (node.left == null && node.right == null) {
            return;
        }
        if (node.left != null) {
            deque.addLast(node.left);
            dfs(deque, res, current + node.left.val, target);
            deque.removeLast();
        }
        if (node.right != null) {
            deque.addLast(node.right);
            dfs(deque, res, current + node.right.val, target);
            deque.removeLast();
        }
    }
}
