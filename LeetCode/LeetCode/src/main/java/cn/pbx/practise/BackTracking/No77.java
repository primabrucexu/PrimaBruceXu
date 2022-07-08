package cn.pbx.practise.BackTracking;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Bruce Xu
 * @date 2022/6/27
 */
public class No77 {

    public static void main(String[] args) {
        No77 demo = new No77();
        List<List<Integer>> combine = demo.combine(4, 2);
        System.out.println(combine);
    }

    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> res = new ArrayList<>();
        if (k > n) {
            return res;
        }

        Set<Integer> set = new HashSet<>();
        int[] nums = new int[n];
        for (int i = 1; i <= n; i++) {
            nums[i - 1] = i;
        }

        backTrack(res, set, nums, k, 0);

        return res;
    }

    private void backTrack(List<List<Integer>> res, Set<Integer> set, int[] nums, int k, int curr) {
        if (set.size() == k) {
            res.add(new ArrayList<>(set));
            return;
        }

        for (int i = curr; i < nums.length; i++) {
            int num = nums[curr];
            if (!set.contains(num)) {
                set.add(num);
                curr += 1;
                backTrack(res, set, nums, k, curr);
                set.remove(num);
            }
        }
    }

}
