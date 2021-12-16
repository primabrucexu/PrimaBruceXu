package cn.pbx.Algorithm.easy;

import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode-cn.com/problems/two-sum/
 *
 * @author BruceXu
 * @date 2020/9/26
 */
public class Solution_01_twoSum {
    public int[] twoSum(int[] nums, int target) {
        // 使用HashMap，用空间换时间，在遍历数组的时候，同时查找是否存在相加等于target的值
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int component = target - nums[i];
            if (map.containsKey(component)) {
                int index = map.get(component);
                return new int[]{Math.min(index, i), Math.max(index, i)};
            }
            map.put(nums[i], i);
        }
        return null;
    }
}
