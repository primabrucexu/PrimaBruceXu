package cn.pbx.part2;

/**
 * @author BruceXu
 * @date 2020/11/7
 */
public class _167 {

    public static void main(String[] args) {
        int[] array = new int[]{2, 7, 11, 15};
        int target = 9;
        twoSum(array, target);
    }

    public static int[] twoSum(int[] numbers, int target) {
        int start = 0;
        int end = numbers.length - 1;
        while (start < end) {
            if (numbers[start] + numbers[end] < target) {
                start++;
            } else if (numbers[start] + numbers[end] > target) {
                end--;
            } else {
                return new int[]{start + 1, end + 1};
            }
        }

        return null;
    }
}
