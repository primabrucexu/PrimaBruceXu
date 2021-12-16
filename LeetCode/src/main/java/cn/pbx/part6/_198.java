package cn.pbx.part6;

/**
 * @author BruceXu
 * @date 2020-12-22
 */

public class _198 {

    public int rob(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }

        if (nums.length == 1) {
            return nums[1];
        }

        if (nums.length == 2) {
            return Math.max(nums[0], nums[1]);
        }

        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0], nums[1]);
        for (int i = 2; i < nums.length; i++) {
            dp[i] = Math.max(dp[i - 2] + nums[i], dp[i - 1]);
        }

        return dp[nums.length - 1];
    }

    public int rob2(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }

        if (nums.length == 1) {
            return nums[0];
        }

        if (nums.length == 2) {
            return Math.max(nums[0], nums[1]);
        }

        int pre2 = nums[0];
        int pre1 = Math.max(nums[0], nums[1]);
        int max = 0;

        for (int i = 2; i < nums.length; i++) {
            max = Math.max(pre2 + nums[i], pre1);
            pre2 = pre1;
            pre1 = max;
        }

        return max;
    }

}
