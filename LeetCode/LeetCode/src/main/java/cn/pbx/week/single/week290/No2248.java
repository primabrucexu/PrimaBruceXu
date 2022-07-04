package cn.pbx.week.single.week290;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Bruce Xu
 * @date 2022/7/4
 */
public class No2248 {
    public List<Integer> intersection(int[][] nums) {
        List<Set<Integer>> total = new ArrayList<>();
        for (int[] num : nums) {
            Set<Integer> set = Arrays.stream(num).boxed().collect(Collectors.toSet());
            total.add(set);
        }

        Set<Integer> res = new HashSet<>();
        boolean first = true;
        for (Set<Integer> set : total) {
            if (first) {
                res = set;
                first = false;
            }
            res.retainAll(set);
        }

        return res.stream().sorted().collect(Collectors.toList());
    }
}
