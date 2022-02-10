package cn.pbx.part2;

import java.util.Arrays;
import java.util.List;

/**
 * @author BruceXu
 * @date 2020/11/12
 */
public class _524 {

    public static void main(String[] args) {
        String s = "abpcplea";
        List<String> d = Arrays.asList("ale", "apple", "monkey", "plea");
        String word = findLongestWord(s, d);
        System.out.println(word);
    }

    public static String findLongestWord(String s, List<String> d) {
        String res = "";
        // 依次从字典d中取出字符串进行匹配比较
        for (String t : d) {
            int i = 0;
            int j = 0;
            while (i < t.length() && j < s.length()) {
                if (t.charAt(i) == s.charAt(j)) {
                    i++;
                    j++;
                } else {
                    j++;
                }
            }
            // 如果t符合条件退出时，i = t.length(), j < s.length()
            if (i == t.length() && j < s.length()) {
                if (t.length() > res.length()) {
                    res = t;
                } else if (t.length() == res.length()) {
                    String[] array = {res, t};
                    Arrays.sort(array);
                    res = array[0];
                }
            }
        }
        return res;
    }
}
