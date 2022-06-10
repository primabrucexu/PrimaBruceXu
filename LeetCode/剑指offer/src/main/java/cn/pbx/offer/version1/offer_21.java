package cn.pbx.offer.version1;

import java.util.Arrays;

/**
 * @author BruceXu
 * @date 2022/3/2
 */
public class offer_21 {

    public static void main(String[] args) {
        offer_21 demo = new offer_21();
        int[] nums = {1, 2, 3, 4};
        System.out.println(Arrays.toString(demo.exchange(nums)));
    }

    public int[] exchange(int[] nums) {
        int front = 0;
        int back = nums.length - 1;
        while (back - front > 1) {
            if (nums[front] % 2 != 0) {
                front++;
            }
            if (nums[back] % 2 == 0) {
                back--;
            }
            if (nums[front] % 2 == 0 && nums[back] % 2 != 0) {
                int mid = nums[front];
                nums[front] = nums[back];
                nums[back] = mid;
            }
        }
        return nums;
    }
}
