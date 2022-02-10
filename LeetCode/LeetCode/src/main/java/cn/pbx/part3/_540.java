package cn.pbx.part3;

/**
 * @author BruceXu
 * @date 2020/11/17
 */
public class _540 {
    public int singleNonDuplicate(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        if (nums.length == 1 || nums[0] != nums[1]) {
            return nums[0];
        }
        if (nums[nums.length - 1] != nums[nums.length - 2]) {
            return nums[nums.length - 1];
        }
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] != nums[mid - 1] && nums[mid] != nums[mid + 1]) {
                return nums[mid];
            }
            if (mid % 2 != 0) {
                // mid是奇数
                if (nums[mid] == nums[mid + 1]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            } else {
                // mid是偶数
                if (nums[mid] == nums[mid + 1]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }

        return 0;
    }
}

