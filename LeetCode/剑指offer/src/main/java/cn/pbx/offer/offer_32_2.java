package cn.pbx.offer;

import cn.pbx.util.TreeNode;

import java.util.*;

/**
 * @author BruceXu
 * @date 2022/6/9
 */
public class offer_32_2 {

    public List<List<Integer>> levelOrder(TreeNode root) {
        if (root == null) {
            return Collections.emptyList();
        }
        Deque<TreeNode> deque = new ArrayDeque<>();
        List<List<Integer>> res = new ArrayList<>();

        List<Integer> level = new ArrayList<>();
        int levelNums = 1;
        deque.addLast(root);

        while (!deque.isEmpty()) {
            levelNums--;
            TreeNode node = deque.removeFirst();
            level.add(node.val);
            if (node.left != null) {
                deque.addLast(node.left);
            }
            if (node.right != null) {
                deque.addLast(node.right);
            }
            if (levelNums == 0) {
                levelNums = deque.size();
                res.add(level);
                level = new ArrayList<>();
            }
        }

        return res;
    }

}
