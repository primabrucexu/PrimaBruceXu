package cn.pbx.part3;

/**
 * @author BruceXu
 * @date 2020/11/15
 */
public class _34 {

    public static void main(String[] args) {
        int[] nums = new int[]{5, 7, 7, 8, 8, 10};
        int target = 8;
        int[] ints = searchRange(nums, target);
        System.out.println(ints[0]);
        System.out.println(ints[1]);
    }

    public static int[] searchRange(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return new int[]{-1, -1};
        }
        if (nums.length <= 1) {
            return nums[0] == target ? new int[]{0, 0} : new int[]{-1, -1};
        }

        int[] res = new int[]{-1, -1};
        int left = 0;
        int right = nums.length - 1;

        // 找到第一次出现的位置
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] >= target) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        res[0] = nums[right] != target ? -1 : right;
        if (res[0] == -1) {
            return new int[]{-1, -1};
        }

        // 找最后一次出现的位置
        left = right;
        right = nums.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] > target) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        res[1] = nums[left] == target ? left : left - 1;

        return res;
    }
}
