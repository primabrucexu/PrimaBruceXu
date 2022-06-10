package cn.pbx.offer.version1;

import cn.pbx.util.TreeNode;

import java.util.*;

/**
 * @author BruceXu
 * @date 2022/3/2
 */
public class offer_26 {

    public static void main(String[] args) {
        offer_26 demo = new offer_26();
        TreeNode T1 = new TreeNode(1);
        TreeNode n1 = new TreeNode(2);
        TreeNode n2 = new TreeNode(3);
        TreeNode n3 = new TreeNode(4);

        T1.left = n1;
        T1.right = n2;
        n1.left = n3;

        TreeNode T2 = new TreeNode(3);

        System.out.println(demo.isSubStructure(T1, T2));

    }

    /**
     * 对A进行先序遍历时，判断B是否为A中某个子树的一部分
     */
    public boolean isSubStructure(TreeNode A, TreeNode B) {
        if (A == null || B == null) {
            return false;
        }
        return recur(A, B) || isSubStructure(A.left, B) || isSubStructure(A.right, B);
    }

    boolean recur(TreeNode A, TreeNode B) {
        if (B == null) {
            return true;
        }
        if (A == null || A.val != B.val) {
            // 如果树A的根节点不等于B的根节点，则表明B不是A的子结构
            return false;
        }
        // 根节点相同的情况下，分别判断二者的左子树和右子树
        return recur(A.left, B.left) && recur(A.right, B.right);
    }

    private List<TreeNode> preOrder(TreeNode root) {
        if (root == null) {
            return Collections.emptyList();
        }
        List<TreeNode> list = new ArrayList<>();
        Deque<TreeNode> deque = new ArrayDeque<>();
        deque.addFirst(root);
        while (!deque.isEmpty()) {
            TreeNode node = deque.removeFirst();
            list.add(node);
            if (node.right != null) {
                deque.addFirst(node.right);
            }
            if (node.left != null) {
                deque.addFirst(node.left);
            }
        }
        return list;
    }

    private List<TreeNode> inOrder(TreeNode root) {
        if (root == null) {
            return Collections.emptyList();
        }
        List<TreeNode> list = new ArrayList<>();
        Deque<TreeNode> deque = new ArrayDeque<>();
        TreeNode node = root;
        while (node != null || !deque.isEmpty()) {
            while (node != null) {
                // 找到最左的
                deque.addFirst(node);
                node = node.left;
            }
            // 最左的根
            node = deque.removeFirst();
            list.add(node);
            node = node.right;
        }
        return list;
    }

    /**
     * 后序遍历的顺序为 左->右->根。反过来看的话，就是 根 -> 右 -> 左，这就是先遍历右子树的先序遍历
     *
     * @param root
     * @return
     */
    private List<TreeNode> postOrder(TreeNode root) {
        if (root == null) {
            return Collections.emptyList();
        }
        List<TreeNode> list = new ArrayList<>();
        Deque<TreeNode> deque = new ArrayDeque<>();
        deque.addFirst(root);
        while (!deque.isEmpty()) {
            TreeNode node = deque.removeFirst();
            list.add(node);
            // 要先访问右子树，那么右子树就要后进
            if (node.left != null) {
                deque.addFirst(node.left);
            }
            if (node.right != null) {
                deque.addFirst(node.right);
            }
        }
        // 逆序一下列表
        Collections.reverse(list);
        return list;
    }

}
