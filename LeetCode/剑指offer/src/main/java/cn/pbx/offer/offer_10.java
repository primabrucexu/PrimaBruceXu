package cn.pbx.offer;

/**
 * 注意int可能会溢出就好
 *
 * @author BruceXu
 * @date 2022/2/10
 */
public class offer_10 {
    public int fib(int n) {
        if (n <= 1) {
            return n;
        }
        int x = 0;
        int y = 1;
        int sum = 0;
        for (int i = 2; i <= n; i++) {
            sum = (x + y) % 1000000007;
            x = y;
            y = sum;
        }
        return sum;
    }
}
