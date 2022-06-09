package cn.pbx.offer;

import cn.pbx.util.TreeNode;

import java.util.*;

/**
 * @author BruceXu
 * @date 2022/6/9
 */
public class offer_32_3 {

    public static void main(String[] args) {
        offer_32_3 demo = new offer_32_3();
        TreeNode root = new TreeNode(1);
        TreeNode n1 = new TreeNode(2);
        TreeNode n2 = new TreeNode(3);
        TreeNode n3 = new TreeNode(4);
        TreeNode n4 = new TreeNode(5);

        root.left = n1;
        root.left.left = n3;
        root.right = n2;
        root.right.right = n4;

        System.out.println(demo.levelOrder(root));
    }

    public List<List<Integer>> levelOrder(TreeNode root) {
        if (root == null) {
            return Collections.emptyList();
        }
        Deque<TreeNode> deque = new ArrayDeque<>();
        List<List<Integer>> res = new ArrayList<>();

        List<Integer> level = new ArrayList<>();
        int levelNums = 1;
        boolean order = false;
        deque.addLast(root);

        while (!deque.isEmpty()) {
            levelNums--;
            TreeNode node = deque.removeFirst();
            level.add(node.val);
            // 第一层时，入栈的就是第二层的元素

            if (node.left != null) {
                deque.addLast(node.left);
            }
            if (node.right != null) {
                deque.addLast(node.right);
            }


            if (levelNums == 0) {
                levelNums = deque.size();
                if (order) {
                    Collections.reverse(level);
                }
                res.add(level);
                level = new ArrayList<>();
                order = !order;
            }
        }

        return res;
    }

}
