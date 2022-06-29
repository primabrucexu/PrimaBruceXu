package cn.pbx.week.two.week65;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Bruce Xu
 * @date 2022/6/29
 */
public class No2070 {
    // fixme 超时，33 / 35 个通过测试用例
    public int[] maximumBeauty(int[][] items, int[] queries) {
        Map<Integer, AtomicInteger> map = new HashMap<>();
        for (int[] item : items) {
            AtomicInteger beauty = map.computeIfAbsent(item[0], o -> new AtomicInteger());
            beauty.set(Math.max(beauty.get(), item[1]));
        }
        for (int i = 0; i < queries.length; i++) {
            int value = queries[i];
            Optional<Map.Entry<Integer, AtomicInteger>> optional = map.entrySet().stream()
                    .filter(entry -> entry.getKey() <= value)
                    .max(Comparator.comparingInt(o -> o.getValue().get()));

            queries[i] = optional.map(entry -> entry.getValue().get()).orElse(0);
        }
        return queries;
    }
}
