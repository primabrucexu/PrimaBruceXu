package cn.pbx.offer.version1;

/**
 * 常规做法要注意注意，当n取到Integer.MIN_VALUE后，取绝对值会溢出，从而导致错误
 * <p>
 * 位运算：已知左移1位是乘2，右移1位数除2。
 *
 * @author BruceXu
 * @date 2022/2/11
 */
public class offer_16 {
    public static void main(String[] args) {
        offer_16 demo = new offer_16();
        int n = -2;
        double x = 3;
        System.out.println(Math.pow(x, n));
        System.out.println(demo.myPow(x, n));
    }

    public double myPow(double x, int n) {
        if (n == 0 || x == 1) {
            return 1;
        }
        if (x == 0) {
            return 0;
        }
        String s = Long.toBinaryString(n > 0 ? n : -n);
        double res = 1;
        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) == '0') {
                res *= 1;
            } else {
                res *= x;
            }
            x *= x;
        }
        return n > 0 ? res : 1 / res;
    }

    public double myPowV1(double x, int n) {
        if (n == 0 || x == 1) {
            return 1;
        }
        if (x == 0) {
            return 0;
        }
        long times = n < 0 ? -n : n;
        double res = x;
        for (int i = 1; i < times; i++) {
            res *= x;
        }
        return n > 0 ? res : 1 / res;
    }
}
