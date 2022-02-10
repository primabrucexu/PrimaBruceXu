package cn.pbx.part2;

import java.util.HashSet;
import java.util.Set;

/**
 * @author BruceXu
 * @date 2020/11/9
 */
public class _3 {
    public static void main(String[] args) {
        String s = "abcabcbb";
        int i = lengthOfLongestSubstring(s);
        System.out.println(i);
    }

    public static int lengthOfLongestSubstring(String s) {
        Set<Character> set = new HashSet<>();
        int len = 0;

        // 窗口终点
        int right = 0;
        for (int i = 0; i < s.length(); i++) {
            if (i != 0) {
                set.remove(s.charAt(i));
            }
            while (right < s.length() && !set.contains(s.charAt(right))) {
                set.add(s.charAt(right));
                right++;
            }
            // 子串长度为 right - 1 - i + 1 = right - i
            len = Math.max(len, set.size());
        }
        return len;
    }
}
