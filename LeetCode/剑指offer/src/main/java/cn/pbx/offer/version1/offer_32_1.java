package cn.pbx.offer.version1;

import cn.pbx.util.TreeNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * @author BruceXu
 * @date 2022/6/9
 */
public class offer_32_1 {

    public int[] levelOrder(TreeNode root) {
        if (root == null) {
            return new int[0];
        }
        Deque<TreeNode> deque = new ArrayDeque<>();
        List<Integer> res = new ArrayList<>();

        int levelNums = 1;
        deque.addLast(root);

        while (!deque.isEmpty()) {
            levelNums--;
            TreeNode node = deque.removeFirst();
            res.add(node.val);
            if (node.left != null) {
                deque.addLast(node.left);
            }
            if (node.right != null) {
                deque.addLast(node.right);
            }
            if (levelNums == 0) {
                levelNums = deque.size();
            }
        }

        return res.stream().mapToInt(o -> o).toArray();
    }

}
