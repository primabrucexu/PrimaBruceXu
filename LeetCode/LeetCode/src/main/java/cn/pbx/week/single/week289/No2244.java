package cn.pbx.week.single.week289;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author Bruce Xu
 * @date 2022/6/29
 */
public class No2244 {

    int[] result = new int[10 * 10 * 10 * 10 * 10 + 1];

    public static void main(String[] args) {
        No2244 demo = new No2244();
        int[] tasks = {2, 2, 3, 3, 2, 4, 4, 4, 4, 4};
        System.out.println(demo.minimumRounds(tasks));
    }

    public int minimumRounds(int[] tasks) {
        Map<Integer, AtomicInteger> map = new HashMap<>();
        Arrays.stream(tasks).forEach(value -> map.computeIfAbsent(value, o -> new AtomicInteger()).getAndIncrement());
        List<Integer> list = map.values().stream().map(AtomicInteger::get).sorted().collect(Collectors.toList());
        build(list.get(list.size() - 1));
        int sum = 0;
        for (Integer i : list) {
            if (i == 1) {
                return -1;
            }
            sum += result[i];
        }

        return sum;
    }

    private void build(int x) {
        result[1] = -1;
        result[2] = 1;
        result[3] = 1;
        result[4] = 2;
        for (int i = 5; i <= x; i++) {
            result[i] = Math.min(result[i - 2] + 1, result[i - 3] + 1);
        }
    }


}
