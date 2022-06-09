package cn.pbx.offer;

import cn.pbx.util.TreeNode;

/**
 * @author BruceXu
 * @date 2022/6/9
 */
public class offer_28 {

    /**
     * 两个树互为镜像：
     * 它们的两个根结点具有相同的值
     * 每个树的右子树都与另一个树的左子树镜像对称
     * 每个树的左子树都与另一个树的右子树镜像对称
     */
    public boolean isSymmetric(TreeNode root) {
        return check(root, root);
    }

    private boolean check(TreeNode r1, TreeNode r2) {
        if (r1 == null && r2 == null) {
            return true;
        }
        if (r1 == null || r2 == null) {
            return false;
        }
        if (r1.val != r2.val) {
            return false;
        }

        return check(r1.left, r2.right) && check(r1.right, r2.left);
    }

}
