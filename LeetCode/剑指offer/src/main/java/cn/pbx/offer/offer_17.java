package cn.pbx.offer;

/**
 * @author BruceXu
 * @date 2022/2/14
 */
public class offer_17 {
    public int[] printNumbers(int n) {
        int length = 1;
        for (int i = 0; i < n; i++) {
            length *= 10;
        }
        int[] res = new int[length - 1];
        for (int i = 0; i < res.length; i++) {
            res[i] = i + 1;
        }

        return res;
    }
}
