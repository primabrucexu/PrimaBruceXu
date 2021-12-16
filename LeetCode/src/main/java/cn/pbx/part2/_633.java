package cn.pbx.part2;

/**
 * @author BruceXu
 * @date 2020/11/9
 */
public class _633 {

    public static void main(String[] args) {
        System.out.println(judgeSquareSum(10));
    }

    public static boolean judgeSquareSum(int c) {
        if (c <= 5) {
            return c != 3;
        }
        long mid = 0;
        long left = 2;
        long right = c / 2;
        while (left <= right) {
            mid = left + (right - left) / 2;
            long t = mid * mid;
            if (t > c) {
                right = mid - 1;
            } else if (t < c) {
                left = mid + 1;
            } else {
                break;
            }
        }

        left = 0;
        right = mid;
        while (left <= right) {
            long sum = left * left + right * right;
            if (sum < c) {
                left++;
            } else if (sum > c) {
                right--;
            } else {
                return true;
            }
        }
        return false;
    }
}
