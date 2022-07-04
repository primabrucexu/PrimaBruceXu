package cn.pbx.week.single.week300;

/**
 * @author Bruce Xu
 * @date 2022/7/3
 */
public class No6109 {
    public static void main(String[] args) {
        No6109 demo = new No6109();
        int n = 6;
        int delay = 2;
        int forget = 4;
        System.out.println(demo.peopleAwareOfSecret1(n, delay, forget));
    }

    /**
     * dp为第i天，新知道秘密的人数
     * 那么，dp[i]要在[i+delay, i+forget-1]的范围内都需要计算。
     */

    public int peopleAwareOfSecret1(int n, int delay, int forget) {
        int mod = 100_000_000 + 7;
        int[] dp = new int[n + 1];
        dp[1] = 1;
        for (int i = 1; i <= n; i++) {
            for (int j = i + delay; j <= Math.min(i + forget - 1, n); j++) {
                dp[j] += dp[i];
                dp[j] %= mod;
            }
        }
        int res = 0;
        for (int i = n - forget + 1; i <= n; i++) {
            res += dp[i];
            res %= mod;
        }
        return res;
    }

    public int peopleAwareOfSecret2(int n, int delay, int forget) {
        int MOD = (int) (1e9 + 7);
        // dp[i]表示第i天新增的人数
        int[] dp = new int[n + 1];
        dp[1] = 1;
        // dp[i]能影响到dp[i+delay]到dp[i+forget]的人数
        for (int i = 1; i < n; i++) {
            for (int j = i + delay; j <= Math.min(n, i + forget - 1); j++) {
                dp[j] += dp[i];
                dp[j] %= MOD;
            }
        }
        // 计算能对第n天产生影响的人数
        int res = 0;
        for (int i = 1; i <= n; i++) {
            if (n < i + forget) {
                res += dp[i];
                res %= MOD;
            }
        }
        return res;
    }
}
