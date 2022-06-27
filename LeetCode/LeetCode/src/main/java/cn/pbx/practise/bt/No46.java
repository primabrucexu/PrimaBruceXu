package cn.pbx.practise.bt;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Bruce Xu
 * @date 2022/6/27
 */
public class No46 {

    public List<List<Integer>> permute(int[] nums) {
        int n = nums.length;
        List<List<Integer>> res = new ArrayList<>();
        if (n == 0) {
            return res;
        }
        Set<Integer> set = new LinkedHashSet<>(n);

        backTrack(set, res, n, nums);

        return res;
    }

    private void backTrack(Set<Integer> set, List<List<Integer>> res, int n, int[] nums) {
        if (set.size() == n) {
            res.add(new ArrayList<>(set));
            return;
        }
        for (int num : nums) {
            if (!set.contains(num)) {
                set.add(num);
                backTrack(set, res, n, nums);
                set.remove(num);
            }
        }
    }


}
