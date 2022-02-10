package cn.pbx.util;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author BruceXu
 * @date 2022-01-15
 */
public class TreeNode {

    public int val;
    public TreeNode left;
    public TreeNode right;

    public TreeNode(int x) {
        val = x;
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "val=" + val +
                '}';
    }

    public String levelPreview() {
        StringBuilder sb = new StringBuilder();
        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.addLast(this);
        int levelSize = queue.size();
        while (!queue.isEmpty()) {
            TreeNode node = queue.removeFirst();
            levelSize--;
            sb.append(node.val);
            if (node.left != null) {
                queue.addLast(node.left);
            }
            if (node.right != null) {
                queue.addLast(node.right);
            }
            if (levelSize == 0) {
                sb.append("\n");
                levelSize = queue.size();
            }
        }
        return sb.toString();
    }
}
