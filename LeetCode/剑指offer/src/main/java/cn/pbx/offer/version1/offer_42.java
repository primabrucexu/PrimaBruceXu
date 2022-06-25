package cn.pbx.offer.version1;

import java.util.Arrays;

/**
 * @author BruceXu
 * @date 2022/6/13
 */
public class offer_42 {
    public static void main(String[] args) {
        offer_42 demo = new offer_42();
        int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.println(demo.maxSubArray(nums));
    }


    /**
     * dp[i]是包含nums[i]的最大子数组的和。
     * 当dp[i-1] < 0时，dp[i] = nums[i]；当dp[i-1] >= 0 时，dp[i] = dp[i-1] + nums[i]
     * <p>
     * 要在nums中找连续子数组的最大和，只需要遍历dp，找到最大值返回即可
     */
    public int maxSubArray(int[] nums) {
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (dp[i - 1] < 0) {
                dp[i] = nums[i];
            } else {
                dp[i] = dp[i - 1] + nums[i];
            }
        }
        Arrays.sort(dp);
        return dp[nums.length - 1];
    }


}
