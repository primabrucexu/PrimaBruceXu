package cn.pbx.part2;

/**
 * @author BruceXu
 * @date 2020/11/11
 */
public class _367 {

    public static void main(String[] args) {
        int num = 808201;
        boolean perfectSquare = isPerfectSquare(num);
        System.out.println(perfectSquare);
    }

    public static boolean isPerfectSquare(int num) {
        if (num == 1) {
            return true;
        }
        long left = 2;
        long right = num / 2;
        while (left <= right) {
            long mid = left + (right - left) / 2;
            long t = mid * mid;
            if (t > num) {
                right = mid - 1;
            } else if (t < num) {
                left = mid + 1;
            } else {
                return true;
            }
        }
        return false;
    }
}
