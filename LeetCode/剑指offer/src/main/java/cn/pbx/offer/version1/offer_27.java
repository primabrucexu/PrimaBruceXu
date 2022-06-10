package cn.pbx.offer.version1;

import cn.pbx.util.TreeNode;

/**
 * @author BruceXu
 * @date 2022/6/9
 */
public class offer_27 {

    public TreeNode mirrorTree(TreeNode root) {
        exchangeLeftAndRight(root);
        return root;
    }

    private void exchangeLeftAndRight(TreeNode node) {
        if (node == null) {
            return;
        }
        // 先处理子树，再处理子树的根
        exchangeLeftAndRight(node.left);
        exchangeLeftAndRight(node.right);
        TreeNode temp = node.left;
        node.left = node.right;
        node.right = temp;
    }

}
