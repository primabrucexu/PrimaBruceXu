package cn.pbx.part5;

import java.util.*;

/**
 * @author BruceXu
 * @date 2020-12-20
 */

public class _40 {

    public static void main(String[] args) {

    }

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(candidates);
        int sum = 0;
        for (int i : candidates) {
            sum += i;
        }
        if (sum < target) {
            return res;
        }
        Deque<Integer> stack = new ArrayDeque<>();
        boolean[] visited = new boolean[candidates.length];
        dfs(res, stack, visited, candidates, 0, target);
        return res;
    }

    private void dfs(List<List<Integer>> res, Deque<Integer> stack, boolean[] flag, int[] nums, int current,
                     int target) {
        if (current == target) {
            List<Integer> list = new ArrayList<>(stack);
            Collections.sort(list);
            for (List<Integer> l : res) {
                if (l.equals(list)) {
                    return;
                }
            }
            res.add(list);
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (current + nums[i] <= target && !flag[i]) {
                stack.addLast(nums[i]);
                flag[i] = true;
                dfs(res, stack, flag, nums, current + nums[i], target);
                stack.removeLast();
                flag[i] = false;
            } else {
                return;
            }
        }
    }

}
