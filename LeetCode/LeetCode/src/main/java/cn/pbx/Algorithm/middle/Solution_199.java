package cn.pbx.Algorithm.middle;

//import com.pbx.Algorithm.utils.TreeNode;

/**
 * @author BruceXu
 * @date 2020/10/9
 */
public class Solution_199 {
//    public List<Integer> rightSideView(TreeNode root) {
//
//        List<Integer> list = new ArrayList<>();
//        if (root == null) {
//            return list;
//        }
//        Queue<TreeNode> queue = new LinkedList<>();
//        queue.offer(root);
//        int levelSize = 1;
//        int num = 0;
//        while (! queue.isEmpty()) {
//            TreeNode node = queue.poll();
//            num++;
//            if (node.left != null) queue.offer(node.left);
//            if (node.right != null) queue.offer(node.right);
//            if (num == levelSize) {
//                list.add(node.val);
//                levelSize = queue.size();
//                num = 0;
//            }
//        }
//        return list;
//    }
}
