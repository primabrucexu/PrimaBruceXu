package cn.pbx.Algorithm.middle;

/**
 * https://leetcode-cn.com/problems/longest-palindromic-substring/
 *
 * @author BruceXu
 * @date 2020/9/28
 */
public class Solution_05_longestPalindrome {

    public static void main(String[] args) {
        String s = "babad";
        String result = longestPalindrome(s);
        System.out.println(result);
    }

    /**
     * 状态转移方程： dpTable[i][j] = (s.charAt(i) == s.charAt(j)) && dpTable[i+1][j-1]
     * 特殊情况：
     * if (i == j) dpTable[i][j] = true;
     * if (j - i == 1) dpTable[i][j] = s.charAt(i) == s.charAt(j);
     * if (j > i) continue;
     *
     * @param s
     * @return
     */
    public static String longestPalindrome(String s) {
        int length = s.length();
        if (length < 2) {
            return s;
        }
        boolean[][] dpTable = new boolean[length][length];
        for (int i = 0; i < length; i++) {
            dpTable[i][i] = true;
        }
        int maxLen = 1;
        int maxStart = 0;
        // i表示列，j表示行
        for (int i = 1; i < length; i++) {
            for (int j = 0; j < i; j++) {
                boolean flag = s.charAt(j) == s.charAt(i);
                dpTable[j][i] = (i - j == 1) ? flag : flag && dpTable[j + 1][i - 1];
                if (dpTable[j][i]) {
                    int len = i - j + 1;
                    maxStart = len > maxLen ? j : maxStart;
                    maxLen = Math.max(len, maxLen);
                }
            }
        }
        return s.substring(maxStart, maxStart + maxLen);
    }
}
