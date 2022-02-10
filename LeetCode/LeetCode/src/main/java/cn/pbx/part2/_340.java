package cn.pbx.part2;

import java.util.HashMap;
import java.util.Map;

/**
 * @author BruceXu
 * @date 2020/11/12
 */
public class _340 {
    public static void main(String[] args) {
        String s = "aa";
        int k = 1;
        int i = lengthOfLongestSubstringKDistinct(s, k);
        System.out.println(i);
    }

    public static int lengthOfLongestSubstringKDistinct(String s, int k) {
        if (k == 0) {
            return 0;
        }
        if (k >= s.length()) {
            return s.length();
        }

        Map<Character, Integer> map = new HashMap<>();
        int maxLen = 0;
        int start = 0;
        int end = 0;
        while (end < s.length()) {
            char c = s.charAt(end);
            // 尝试将c放入map中
            map.put(c, end);

            if (map.size() <= k) {
                // c放入之后满足条件，继续扩大窗口
                end++;
            } else {
                maxLen = Math.max(end - start, maxLen);
                // c放入之后不满足条件了，需要缩小窗口
                while (start <= end) {
                    char t = s.charAt(start);
                    if (map.get(t) == start) {
                        map.remove(t);
                        break;
                    }
                    start++;
                }
                maxLen = Math.max(end - start, maxLen);
                start++;
            }
        }
        if (map.size() <= k) {
            maxLen = Math.max(end - start, maxLen);
        }
        return maxLen;
    }
}
