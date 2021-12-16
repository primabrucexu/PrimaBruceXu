package cn.pbx.part3;

/**
 * @author BruceXu
 * @date 2020/11/15
 */
public class _81 {

    public static void main(String[] args) {
        int[] nums = new int[]{1, 5, 3};
        int target = 3;
        boolean b = search2(nums, target);
        System.out.println(b);
    }

    public static boolean search(int[] nums, int target) {
        if (nums.length == 0) {
            return false;
        }
        if (nums.length == 1) {
            return nums[0] == target;
        }
        int flag = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] > nums[i + 1]) {
                flag = i;
            }
        }

        return binarySearch(0, flag, nums, target) || binarySearch(flag + 1, nums.length - 1, nums, target);
    }

    public static boolean binarySearch(int left, int right, int[] nums, int target) {
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] > target) {
                right = mid - 1;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                return true;
            }
        }
        return false;
    }

    public static boolean search2(int[] nums, int target) {
        if (nums.length == 0) {
            return false;
        }
        if (nums.length == 1) {
            return nums[0] == target;
        }
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return true;
            }
            if (nums[left] == nums[mid]) {
                left++;
            } else if (nums[mid] <= nums[right]) {
                // 右边是有序的
                if (target > nums[mid] && target <= nums[right]) {
                    // target落在有序区间内
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            } else {
                // 左边是有序的
                if (target >= nums[left] && target < nums[mid]) {
                    // target落在有序区间内
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
        }

        return false;
    }


}
