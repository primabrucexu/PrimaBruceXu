package cn.pbx.Algorithm.middle;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * https://leetcode-cn.com/problems/longest-substring-without-repeating-characters/
 *
 * @author BruceXu
 * @date 2020/9/26
 */
public class Solution_03_lengthOfLongestSubstring {
    public static void main(String[] args) {
        String s = "dvdf";
        int r = lengthOfLongestSubstring(s);
        System.out.println(r);
    }

    /**
     * 如果我们依次递增地枚举子串的起始位置，那么子串的结束位置也是递增的！
     * 这里的原因在于，假设我们选择字符串中的第 k 个字符作为起始位置，并且得到了不包含重复字符的最长子串的结束位置为 r。
     * 那么当我们选择第 k+1 个字符作为起始位置时，首先从 k+1 到 r 的子串显然是不重复的，
     * 并且由于少了原本的第 k 个字符，我们可以尝试继续增大 r直到右侧出现了重复字符为止。
     */
    public static int lengthOfLongestSubstring(String s) {
        // 哈希集合，记录每个字符是否出现过
        Set<Character> set = new HashSet<Character>();
        char[] array = s.toCharArray();
        int r = 0;
        int len = 0;
        for (int i = 0; i < array.length; i++) {
            if (i != 0) {
                set.remove(array[i - 1]);
            }
            while (r < array.length && !set.contains(array[r])) {
                set.add(array[r]);
                r++;
            }
            len = Math.max(len, r - i);
        }
        return len;
    }
}
