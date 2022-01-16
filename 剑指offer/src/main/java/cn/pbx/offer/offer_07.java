package cn.pbx.offer;

import cn.pbx.offer.util.TreeNode;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 方法一：递归，但是递归铁超时，所以要把递归转换成用迭代
 * <p>
 * todo 未解决
 *
 * @author BruceXu
 * @date 2022-01-15
 */
public class offer_07 {

    public static void main(String[] args) {
        offer_07 demo = new offer_07();
        int[] preorder = new int[]{3, 9, 20, 15, 7};
        int[] inorder = new int[]{9, 3, 15, 20, 7};
        demo.buildTree(preorder, inorder);
    }

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        List<TreeNode> preorderList = Arrays.stream(preorder).boxed().map(TreeNode::new).collect(Collectors.toList());
        List<Integer> inorderList = Arrays.stream(inorder).boxed().collect(Collectors.toList());
        return buildTree(preorderList, inorderList);
    }

    private TreeNode buildTree(List<TreeNode> preorder, List<Integer> inorder) {
        if (preorder.isEmpty()) {
            return null;
        }

        // 前序遍历的第一个必是根节点
        TreeNode root = preorder.get(0);

        if (preorder.size() == 1) {
            return root;
        }

        Deque<TreeNode> deque = new LinkedList<>();
        deque.addLast(root);

        while (!deque.isEmpty()) {
            TreeNode node = deque.removeFirst();
            int i = inorder.indexOf(node.val);
            preorder.remove(node);

            boolean left = true;
            boolean right = true;

            Set<Integer> leftInorder = new HashSet<>(inorder.subList(0, i));
            Set<Integer> rightInorder = new HashSet<>(inorder.subList(i + 1, inorder.size()));

            for (TreeNode treeNode : preorder) {
                if (left && leftInorder.contains(treeNode.val)) {
                    node.left = treeNode;
                    deque.addLast(treeNode);
                    left = false;
                    continue;
                }
                if (right && rightInorder.contains(treeNode.val)) {
                    node.right = treeNode;
                    deque.addLast(treeNode);
                    right = false;
                    continue;
                }
            }
        }

        return root;
    }

    private TreeNode buildTree1(List<Integer> preorder, List<Integer> inorder) {
        if (preorder.isEmpty()) {
            return null;
        }

        // 前序遍历的第一个节点一定是二叉树的根
        TreeNode root = new TreeNode(preorder.get(0));

        // 递归出口
        if (preorder.size() == 1) {
            return root;
        }

        // 找到根之后，到中序遍历的里面找根左子树和右子树
        int rootPos = 0;
        for (int i = 0; i < inorder.size(); i++) {
            if (inorder.get(i) == root.val) {
                rootPos = i;
                break;
            }
        }

        List<Integer> leftInorder = inorder.subList(0, rootPos);
        List<Integer> rightInorder = inorder.subList(rootPos + 1, inorder.size());

        List<Integer> leftPreorder = preorder.stream().filter(leftInorder::contains).collect(Collectors.toList());
        List<Integer> rightPreorder = preorder.stream().filter(rightInorder::contains).collect(Collectors.toList());

        TreeNode left = buildTree1(leftPreorder, leftInorder);
        TreeNode right = buildTree1(rightPreorder, rightInorder);

        root.left = left;
        root.right = right;

        return root;
    }

}
