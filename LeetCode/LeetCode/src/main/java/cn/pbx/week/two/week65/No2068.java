package cn.pbx.week.two.week65;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Bruce Xu
 * @date 2022/6/29
 */
public class No2068 {

    public static void main(String[] args) {
        No2068 demo = new No2068();
        String w1 = "aaaa";
        String w2 = "bccb";
        System.out.println(demo.checkAlmostEquivalent(w1, w2));

    }

    public boolean checkAlmostEquivalent(String word1, String word2) {
        Map<Character, AtomicInteger> map = new HashMap<>();
        int n = word1.length();
        for (int i = 0; i < n; i++) {
            char c1 = word1.charAt(i);
            map.computeIfAbsent(c1, o -> new AtomicInteger()).getAndIncrement();
            char c2 = word2.charAt(i);
            map.computeIfAbsent(c2, o -> new AtomicInteger()).getAndDecrement();
        }

        for (AtomicInteger diff : map.values()) {
            if (diff.get() > 3 || diff.get() < -3) {
                return false;
            }
        }

        return true;
    }
}
