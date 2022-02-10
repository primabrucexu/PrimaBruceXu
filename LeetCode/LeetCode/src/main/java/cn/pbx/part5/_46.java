package cn.pbx.part5;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * @author BruceXu
 * @date 2020/12/13
 */
public class _46 {

    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 3};
        System.out.println(permute(nums));
    }

    public static List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        if (nums.length == 0) {
            return res;
        }
        int n = nums.length;
        boolean[] flag = new boolean[n];
        Deque<Integer> stack = new ArrayDeque<>(3);
        dfs(nums, flag, stack, res, n);
        return res;
    }

    /**
     * @param nums  需要全排列的数组
     * @param flag  状态数组，记录是否访问过该元素
     * @param stack 记录单次排列结果
     * @param res   记录所有的排列结果
     * @param n     元素个数
     */
    private static void dfs(int[] nums, boolean[] flag, Deque<Integer> stack, List<List<Integer>> res, int n) {
        if (stack.size() == n) {
            res.add(new ArrayList<>(stack));
            return;
        }
        for (int i = 0; i < n; i++) {
            if (!flag[i]) {
                flag[i] = true;
                stack.addLast(nums[i]);
                dfs(nums, flag, stack, res, n);
                flag[i] = false;
                stack.removeLast();
            }
        }
    }
}
