package cn.pbx.part2;

import java.util.HashMap;
import java.util.Map;

/**
 * @author BruceXu
 * @date 2020/11/9
 */
public class _76 {
    public static void main(String[] args) {
        String s = "ADOBECODEBANC";
        String t = "ABC";
        String s1 = minWindow(s, t);
        System.out.println(s1);
    }

    public static String minWindow(String s, String t) {
        int length = 1000000;
        Map<Character, Integer> tmap = new HashMap<>();
        Map<Character, Integer> smap = new HashMap<>();

        for (int i = 0; i < t.length(); i++) {
            char c = t.charAt(i);
            if (!tmap.containsKey(t.charAt(i))) {
                tmap.put(c, 1);
                smap.put(c, 0);
            } else {
                tmap.replace(c, tmap.get(c) + 1);
            }
        }
        String res = "";
        int left = 0, right = -1;
        while (right < s.length()) {
            right++;
            if (right == s.length()) {
                break;
            }
            char cr = s.charAt(right);
            if (smap.containsKey(cr)) {
                smap.replace(cr, smap.get(cr) + 1);
            }
            boolean flag = check(tmap, smap);
            while (flag && left <= right) {
                char cl = s.charAt(left);
                if (right - left + 1 < length) {
                    length = right - left + 1;
                    res = s.substring(left, right + 1);
                }
                if (smap.containsKey(cl)) {
                    smap.replace(cl, smap.get(cl) - 1);
                }
                // 简单优化一下：如果我们缩小窗口时，删除的不是t中的字符，那么再次check肯定没问题，不然就要重新check了
                flag = !tmap.containsKey(cl) || check(tmap, smap);
                left++;
            }
        }
        return res;
    }

    public static boolean check(Map<Character, Integer> tmap, Map<Character, Integer> wmap) {
        for (Character character : tmap.keySet()) {
            if (!wmap.containsKey(character) || wmap.get(character) < tmap.get(character)) {
                return false;
            }
        }
        return true;
    }

}
