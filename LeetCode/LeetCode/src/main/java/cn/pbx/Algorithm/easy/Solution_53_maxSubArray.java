package cn.pbx.Algorithm.easy;

/**
 * https://leetcode-cn.com/problems/maximum-subarray/
 *
 * @author BruceXu
 * @date 2020/9/26
 */
public class Solution_53_maxSubArray {
    public static void main(String[] args) {
        int[] array = new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.println(maxSubArray(array));
    }

    /**
     * sum先加上当前元素，如果sum小于0，则之前的所有元素全部丢弃
     * 若sum大于0，则比较sum和当前元素的大小，取最大的
     * 最后比较之前遍历过程中大于0的sum，然后取最大值
     * <p>
     * 需要考虑到数组中全为负数的情况
     */
    public static int maxSubArray(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }
        int sum = nums[0];
        int maxSum = nums[0];
        for (int i = 1; i < nums.length; i++) {
            sum += nums[i];
            if (sum <= 0) {
                sum = nums[i];
            }
            sum = Math.max(nums[i], sum);
            maxSum = Math.max(sum, maxSum);
        }
        return maxSum;
    }
}
