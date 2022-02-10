package cn.pbx.offer;

import cn.pbx.offer.util.TreeNode;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 方法一：递归，但是递归铁超时，所以要把递归转换成用迭代
 * <p>
 * todo 未解决
 *
 * 二叉树可视化
 * http://520it.com/binarytrees/
 *
 * @author BruceXu
 * @date 2022-01-15
 */
public class offer_07 {

    public static void main(String[] args) {
        offer_07 demo = new offer_07();
        int[] preorder = new int[]{53, 45, 6, 13, 68, 69, 94, 72, 84};
        int[] inorder = new int[]{6, 13, 45, 53, 68, 69, 72, 84, 94};
        demo.buildTree(preorder, inorder);
    }

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        List<TreeNode> preorderList = Arrays.stream(preorder).boxed().map(TreeNode::new).collect(Collectors.toList());
        List<Integer> inorderList = Arrays.stream(inorder).boxed().collect(Collectors.toList());
        return buildTreeV2(preorderList, inorderList);
    }

    private TreeNode buildTreeV2(List<TreeNode> preorder, List<Integer> inorder) {
        if (preorder.isEmpty()) {
            return null;
        }

        // 前序遍历的第一个必是根节点
        TreeNode root = preorder.get(0);

        if (preorder.size() == 1) {
            return root;
        }



        return root;
    }

    private TreeNode buildTreeV1(List<Integer> preorder, List<Integer> inorder) {
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

        TreeNode left = buildTreeV1(leftPreorder, leftInorder);
        TreeNode right = buildTreeV1(rightPreorder, rightInorder);

        root.left = left;
        root.right = right;

        return root;
    }

}
