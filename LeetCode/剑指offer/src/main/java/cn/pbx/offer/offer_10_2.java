package cn.pbx.offer;

/**
 * 状态转移方程:
 * f(n) = f(n-1) + f(n-2)
 *
 * @author BruceXu
 * @date 2022/2/10
 */
public class offer_10_2 {
    public int numWays(int n) {
        if (n <= 1) {
            return 1;
        }

        int x = 1;
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
