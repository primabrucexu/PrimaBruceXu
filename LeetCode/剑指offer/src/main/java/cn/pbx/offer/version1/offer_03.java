package cn.pbx.offer.version1;

import java.util.HashSet;
import java.util.Set;

/**
 * 方法1：
 * 额外空间来去重
 * 方法2：
 * 原地排序，因为一个长度为 n 的数组 nums 里的所有数字都在 0～n-1 的范围内。
 * 所以每个元素都可以按照其值放置在数组之中。如果说这个位置已经有值，则说明这个元素重复了
 *
 * @author BruceXu
 * @date 2022-01-15
 */
public class offer_03 {

    public int findRepeatNumber(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            if (set.contains(num)) {
                return num;
            }
            set.add(num);
        }
        return 0;
    }

}
