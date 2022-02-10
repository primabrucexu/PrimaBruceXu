package cn.pbx.part3;

/**
 * @author BruceXu
 * @date 2020/11/16
 */
public class _153 {

    public static void main(String[] args) {
        int[] array = new int[]{3, 1, 3};
        int min = findMin(array);
        System.out.println("min=" + min);
    }

    public static int findMin(int[] nums) {
        if (nums.length <= 2) {
            return nums.length == 1 ? 0 : Math.min(nums[0], nums[1]);
        }
        int left = 0;
        int right = nums.length - 1;
        int mid = 0;
        while (left < right) {
            mid = left + (right - left) / 2;
            if (nums[mid] < nums[right]) {
                right = mid;
            } else if (nums[right] == nums[mid]) {
                right--;
            } else {
                left = mid + 1;
            }
        }
        return nums[left];
    }
}
