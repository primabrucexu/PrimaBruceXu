package cn.pbx.part5;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * @author BruceXu
 * @date 2020-12-20
 */

public class _47 {

    public static void main(String[] args) {
        int[] nums = {1, 1, 2};
        List<List<Integer>> list = permuteUnique(nums);
        System.out.println(list);
    }

    public static List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();

        Deque<Integer> stack = new ArrayDeque<>(nums.length);
        boolean[] flag = new boolean[nums.length];
        dfs(res, nums, stack, flag);


        return res;
    }

    private static void dfs(List<List<Integer>> res, int[] nums, Deque<Integer> stack, boolean[] flag) {
        if (stack.size() == nums.length) {
            List<Integer> list = new ArrayList<>(stack);
            for (List<Integer> l : res) {
                if (l.equals(list)) {
                    return;
                }
            }
            res.add(list);
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (!flag[i]) {
                stack.addLast(nums[i]);
                flag[i] = true;
                dfs(res, nums, stack, flag);
                stack.removeLast();
                flag[i] = false;
            }
        }
    }


}
