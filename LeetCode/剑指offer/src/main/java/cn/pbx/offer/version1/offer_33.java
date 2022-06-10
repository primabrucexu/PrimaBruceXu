package cn.pbx.offer.version1;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author BruceXu
 * @date 2022/6/10
 */
public class offer_33 {

    private static final Log log = LogFactory.get();

    public static void main(String[] args) {
        offer_33 demo = new offer_33();
        int[] postorder = {
                179, 437, 1405, 5227, 8060, 8764, 8248, 4687, 3297, 13038,
                12691, 15744, 16195, 15642, 19813, 17128, 21051, 20707, 22177, 21944,
                23644, 23281, 19970, 23652, 26471, 31467, 33810, 32300, 33880, 27334,
                25987, 35643, 35103, 36489, 42534, 42990, 42942, 37090, 36075, 34516,
                16624, 11335, 10737, 44641, 45754, 47096, 46021, 49150, 48013, 49814,
                51545, 52555, 50701, 47875, 56783, 57558, 53812, 62008, 61737, 63052,
                63478, 62799, 59246, 64765, 64066, 63862, 65384, 67449, 66552, 57741,
                45618, 44412, 667, 69718, 75519, 76819, 72971, 79319, 78145, 80615,
                84280, 80984, 86598, 85903, 84334, 80867, 87993, 92361, 88465, 87738,
                80364, 94380, 94446, 96785, 93694, 76847, 99655, 98675, 97001, 72112};
        int[] p1 = {
                179, 437, 1405, 5227, 8060, 8764, 8248, 4687, 3297, 13038, 12691, 15744, 16195, 15642, 19813, 17128, 21051, 20707, 22177, 21944, 23644, 23281, 19970, 23652, 26471, 31467, 33810, 32300, 33880, 27334, 25987, 35643, 35103, 36489, 42534, 42990, 42942, 37090, 36075, 34516, 16624, 11335, 10737, 44641, 45754, 47096, 46021, 49150, 48013, 49814, 51545, 52555, 50701, 47875, 56783, 57558, 53812, 62008, 61737, 63052, 63478, 62799, 59246, 64765, 64066, 63862, 65384, 67449, 66552, 57741, 45618, 44412, 667, 69718
        };
        List<Info> list = new ArrayList<>();
        System.out.println(demo.verifyPostorder(p1, list));
        List<Info> infoList = list.stream().filter(o -> !o.leftResult || !o.rightResult).collect(Collectors.toList());
        List<Integer> collect = infoList.stream().map(list::indexOf).collect(Collectors.toList());
        System.out.println(collect);
    }

    /**
     * 根据二叉搜索树的定义，其节点的顺序一定满足 左子树 < 根 < 右子树。
     * 也就是说在后序遍历中，所有子树，都要满足这个要求
     * 后续遍历的顺序(左 -> 右 -> 根)
     * 由于根永远是最后一个，所以找到第一个大于根的元素postorder[i]就能划分出左右子树，左子树的为[0,i-1]，右子树为[i+1,-2]
     * 同时要满足左子树的每个元素都小于根，右子树的每个元素都大于根
     * <p>
     * 难点：如何判断链表形式的二叉搜索树？
     * 当剩下三个元素的时候，无论如果都可以构造出符合条件的
     */
    public boolean verifyPostorder(int[] postorder, List<Info> list) {
        if (postorder.length <= 3) {
            return true;
        }

        int rightStart = 0;
        int root = postorder[postorder.length - 1];
        while (rightStart < postorder.length && postorder[rightStart] < root) {
            rightStart++;
        }

        int[] left = Arrays.copyOfRange(postorder, 0, rightStart);
        int[] right = Arrays.copyOfRange(postorder, rightStart, postorder.length - 1);
        // 左子树的每个元素都小于根，这点在找rightStart的时候可以保证
//        for (int i : left) {
//            if (i > root) {
//                return false;
//            }
//        }

        // 右子树的每个元素都大于根
        if (!isAllInArraySmallerThanX(root, right)) {
            return false;
        }

        return verifyPostorder(left, list) && verifyPostorder(right, list);
    }

    private boolean isAllInArraySmallerThanX(int x, int[] array) {
        for (int i : array) {
            if (i < x) {
                return false;
            }
        }
        return true;
    }


    @Data
    @AllArgsConstructor
    static class Info {
        private boolean leftResult;
        private int[] left;
        private boolean rightResult;
        private int[] right;
        private int[] original;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Info info = (Info) o;
            return Arrays.equals(original, info.original);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(original);
        }
    }

}
