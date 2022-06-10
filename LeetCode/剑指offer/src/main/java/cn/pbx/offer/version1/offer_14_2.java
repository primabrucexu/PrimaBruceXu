package cn.pbx.offer.version1;

import java.math.BigDecimal;

/**
 * 最终结果取模 1e9+7（1000000007），直接用BigDecimal来处理溢出
 *
 * @author BruceXu
 * @date 2022/2/11
 */
public class offer_14_2 {
    public int cuttingRope(int n) {
        if (n == 2) {
            return 1;
        }
        if (n == 3) {
            return 2;
        }
        BigDecimal mod = new BigDecimal(1000000007);
        BigDecimal multiplicand = new BigDecimal(2);
        BigDecimal multiplicand1 = new BigDecimal(3);
        BigDecimal[] sum = new BigDecimal[n + 1];
        for (int i = 1; i <= n; i++) {
            if (i <= 3) {
                sum[i] = new BigDecimal(i);
            } else {
                BigDecimal v1 = sum[i - 2].multiply(multiplicand);
                BigDecimal v2 = sum[i - 3].multiply(multiplicand1);
                sum[i] = v1.max(v2);
            }
        }
        return sum[n].remainder(mod).intValue();
    }
}
