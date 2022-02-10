package cn.pbx.part1;

/**
 * @author BruceXu
 * @date 2020/11/5
 */
public class _665 {
    public static void main(String[] args) {
        int[] array = new int[]{-1, 4, 2, 3};
        int[] array1 = new int[]{4, 2, 3};
        int[] array2 = new int[]{4, 2, 1};
        int[] array3 = new int[]{3, 4, 2, 1};
        int[] array4 = new int[]{3, 4, 2, 3};
        System.out.println(checkPossibility(array));
        System.out.println(checkPossibility(array1));
        System.out.println(checkPossibility(array2));
        System.out.println(checkPossibility(array3));
        System.out.println(checkPossibility(array4));
    }

    public static boolean checkPossibility(int[] nums) {
        int times = 0;
        for (int i = 1; i < nums.length; i++) {
            if (times > 1) {
                return false;
            }
            if (nums[i - 1] > nums[i]) {
                times++;
                if (i == 1 || nums[i - 2] <= nums[i]) {
                    nums[i - 1] = nums[i];
                } else {
                    nums[i] = nums[i - 1];
                }
            }

        }
        return true;
    }
}
