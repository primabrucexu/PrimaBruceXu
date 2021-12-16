package cn.pbx.part6;

/**
 * @author BruceXu
 * @date 2020-12-22
 */

public class _70 {

    public int climbStairs(int n) {
        if (n <= 2) {
            return n;
        }
        int[] res = new int[n + 1];
        res[1] = 1;
        res[2] = 2;
        for (int i = 3; i < n + 1; i++) {
            res[i] = res[i - 1] + res[i - 2];
        }
        return res[n];
    }

}
