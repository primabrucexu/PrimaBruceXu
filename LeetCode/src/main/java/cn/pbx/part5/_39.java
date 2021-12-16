package cn.pbx.part5;

import java.util.*;

/**
 * @author BruceXu
 * @date 2020-12-19
 */

public class _39 {

    public static void main(String[] args) {
        int[] array = {2, 3, 5};
        List<List<Integer>> list = combinationSum(array, 8);
        for (List<Integer> l : list) {
            System.out.println(l);
        }
    }

    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();
        Deque<Integer> stack = new ArrayDeque<>();
        Arrays.sort(candidates);
        dfs(res, stack, candidates, 0, target);
        return res;
    }

    private static void dfs(List<List<Integer>> res, Deque<Integer> stack, int[] nums, int current, int target) {
        if (current == target) {
            List<Integer> list = new ArrayList<>(stack);
            Collections.sort(list);
            for (List<Integer> l : res) {
                if (list.equals(l)) {
                    return;
                }
            }
            res.add(list);
            return;
        }
        for (int i : nums) {
            if (current + i > target) {
                return;
            }
            stack.addLast(i);
            dfs(res, stack, nums, current + i, target);
            stack.removeLast();
        }
    }
}
