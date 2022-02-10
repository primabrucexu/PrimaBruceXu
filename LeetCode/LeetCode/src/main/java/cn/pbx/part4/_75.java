package cn.pbx.part4;

import java.util.Arrays;

/**
 * @author BruceXu
 * @date 2020/11/30
 */
public class _75 {

    public static void main(String[] args) {

    }

    public static void sortColors(int[] nums) {
        if (nums.length <= 2) {
            return;
        }
        int[] f = new int[3];
        for (int num : nums) {
            f[num]++;
        }
        int p = 0;
        for (int i = 0; i < 3; i++) {
            Arrays.fill(nums, p, p + f[i], i);
            p += f[i];
        }
    }
}
