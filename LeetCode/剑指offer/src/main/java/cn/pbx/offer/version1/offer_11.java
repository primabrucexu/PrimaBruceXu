package cn.pbx.offer.version1;

import java.util.Arrays;

/**
 * @author BruceXu
 * @date 2022/2/10
 */
public class offer_11 {
    public int minArray(int[] numbers) {
        Arrays.sort(numbers);
        return numbers[0];
    }
}
