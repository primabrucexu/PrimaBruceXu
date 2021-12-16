package cn.pbx.part3;

/**
 * @author BruceXu
 * @date 2020/11/13
 */
public class _69 {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            int x = (int) (Math.random() * 100);
            System.out.println(x);
            System.out.println(Math.sqrt(x));
            System.out.println(mySqrt(x));
            System.out.println("=====================");
        }

        int i = mySqrt2(2147395599);

        System.out.println(i);
    }

    public static int mySqrt(int x) {
        if (x <= 1) {
            return x;
        }
        if (x < 4) {
            return 1;
        }
        int left = 2;
        int right = x / 2;
        int mid = 0;
        while (left <= right) {
            mid = left + (right - left) / 2;
            long temp = (long) mid * (long) mid;
            if (temp < x) {
                left = mid + 1;
            } else if (temp > x) {
                right = mid - 1;
            } else {
                return mid;
            }
        }
        return (long) mid * (long) mid > x ? mid - 1 : mid;
    }

    public static int mySqrt2(int x) {
        if (x <= 1) {
            return x;
        }
        if (x < 4) {
            return 1;
        }
        long left = 2;
        long right = x / 2;
        long mid = 0;
        while (left <= right) {
            mid = left + (right - left) / 2;
            long temp = mid * mid;
            if (temp > x) {
                right = mid - 1;
            } else if (temp < x) {
                left = mid + 1;
            } else {
                return (int) mid;
            }
        }
        if (mid * mid > x)
            return (int) (mid - 1);
        return (int) mid;
    }
}
