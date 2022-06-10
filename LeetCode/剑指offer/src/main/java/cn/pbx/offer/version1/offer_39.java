package cn.pbx.offer.version1;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author BruceXu
 * @date 2022/6/10
 */
public class offer_39 {
    public int majorityElement(int[] nums) {
        Map<Integer, AtomicInteger> map = new HashMap<>();
        for (int num : nums) {
            int times = map.computeIfAbsent(num, integer -> new AtomicInteger()).incrementAndGet();
            if (2 * times >= nums.length) {
                return num;
            }
        }
        return 0;
    }
}
