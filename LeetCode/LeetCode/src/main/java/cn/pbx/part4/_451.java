package cn.pbx.part4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author BruceXu
 * @date 2020/11/30
 */
public class _451 {
    public static void main(String[] args) {
        System.out.println(frequencySort("tree"));
    }

    public static String frequencySort(String s) {
        Map<Character, Integer> map = new HashMap<>();
        // 对字母进行桶排序，得到每个字母出现的频率
        int max = 0;
        for (int i = 0; i < s.length(); i++) {
            map.put(s.charAt(i), map.getOrDefault(s.charAt(i), 0) + 1);
            max = Math.max(max, map.get(s.charAt(i)));
        }
        // 再对频率进行一次桶排序
        ArrayList<Character>[] bucket = new ArrayList[max + 1];
        for (char c : map.keySet()) {
            int f = map.get(c);
            if (bucket[f] == null) {
                bucket[f] = new ArrayList<>();
            }
            bucket[f].add(c);
        }
        int p = 0;
        char[] chars = s.toCharArray();
        for (int i = max; i >= 0; i--) {
            if (bucket[i] != null) {
                for (char c : bucket[i]) {
                    Arrays.fill(chars, p, p + i, c);
                    p += i;
                }
            }
        }
        return new String(chars);
    }
}
