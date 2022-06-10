package cn.pbx.offer.version1;

import java.util.Arrays;

/**
 * @author BruceXu
 * @date 2022/6/10
 */
public class offer_40 {
    public int[] getLeastNumbers(int[] arr, int k) {
        Arrays.sort(arr);
        return Arrays.copyOfRange(arr, 0, k);
    }
}
