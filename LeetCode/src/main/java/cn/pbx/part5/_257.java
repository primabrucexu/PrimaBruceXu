package cn.pbx.part5;

import cn.pbx.Algorithm.utils.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author BruceXu
 * @date 2020-12-19
 */

public class _257 {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.right = new TreeNode(4);
        List<String> list = binaryTreePaths(root);
        for (String s : list) {
            System.out.println(s);
        }
    }

    public static List<String> binaryTreePaths(TreeNode root) {
        List<String> res = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        dfs(res, stack, root);
        return res;
    }

    private static void dfs(List<String> res, Stack<TreeNode> stack, TreeNode node) {
        if (node.left == null && node.right == null) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < stack.size(); i++) {
                if (i != 0) {
                    sb.append("->");
                }
                sb.append(stack.get(i).val);
            }
            res.add(sb.toString());
        }
        if (node.left != null) {
            stack.push(node.left);
            dfs(res, stack, node.left);
            stack.pop();
        }
        if (node.right != null) {
            stack.push(node.right);
            dfs(res, stack, node.right);
            stack.pop();
        }
    }
}
