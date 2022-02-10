package cn.pbx.Algorithm.middle;

//import com.pbx.Algorithm.utils.TreeNode;

/**
 * @author BruceXu
 * @date 2020/9/29
 */
public class Solution_102_levelOrder {
//    public List<List<Integer>> levelOrder(TreeNode root) {
//        List<List<Integer>> lists = new ArrayList<>();
//        if (root == null) {
//            return lists;
//        }
//        TreeNode p = root;
//        Queue<TreeNode> queue = new LinkedList<>();
//        List<Integer> list = new ArrayList<>();
//        queue.offer(p);
//        int levelSize = 1;
//        while (!queue.isEmpty()) {
//            TreeNode curr = queue.poll();
//            if (levelSize!=0) {
//                list.add(curr.val);
//                levelSize--;
//            }
//            if (curr.left != null) queue.offer(curr.left);
//            if (curr.right != null) queue.offer(curr.right);
//            if (levelSize==0) {
//                lists.add(list);
//                list = new ArrayList<>();
//                levelSize = queue.size();
//            }
//        }
//        return lists;
//    }
}
