package cn.pbx.offer.version1;

/**
 * @author BruceXu
 * @date 2022/2/11
 */
public class offer_14_1 {
    public int cuttingRope(int n) {
        if (n == 2) {
            return 1;
        }
        if (n == 3) {
            return 2;
        }
        int[] sum = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            if (i <= 3) {
                sum[i] = i;
            } else {
                sum[i] = Math.max(2 * sum[i - 2], 3 * sum[i - 3]);
            }
        }
        return sum[n];
    }
}
