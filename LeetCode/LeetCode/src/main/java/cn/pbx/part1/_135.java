package cn.pbx.part1;

import java.util.Arrays;

/**
 * @author BruceXu
 * @date 2020/11/3
 */
public class _135 {

    public static void main(String[] args) {
        int[] ratings = new int[]{1, 2, 2};
        int sum = candy(ratings);
        System.out.println(sum);
    }

    public static int candy(int[] ratings) {
        int[] array = new int[ratings.length];
        Arrays.fill(array, 1);
        for (int i = 1; i < ratings.length; i++) {
            // 右边大于左边，右边=左边+1
            if (ratings[i] > ratings[i - 1]) {
                array[i] = array[i - 1] + 1;
            }
        }
        int res = array[ratings.length - 1];
        for (int i = ratings.length - 2; i >= 0; i--) {
            // 左边大于右边，如果左边<右边，则左边=右边+1，不然左边不变
            if (ratings[i] > ratings[i + 1]) {
                array[i] = array[i] < array[i + 1] ? array[i + 1] + 1 : array[i];
            }
            res += array[i];
        }
        return res;
    }
}
