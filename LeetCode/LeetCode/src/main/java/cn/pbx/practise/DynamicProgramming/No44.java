package cn.pbx.practise.DynamicProgramming;

/**
 * @author Bruce Xu
 * @date 2022/7/8
 */
public class No44 {
    /**
     * 在给出的规则中，只会出现一下三种情况
     * 1. a-z，匹配对应的字符
     * 2. ？，匹配1个任意字符
     * 3. *，匹配0个或多个任意字符
     * <p>
     * 用dp[i][j]来表示字符串s的第i个字符和规则rule的第j个字符的匹配情况，那么就有
     * 1. rule[i]为小写字母时，s[i]必须要为对应的小写字母。即 dp[i][j] = rule[i] == s[i] && dp[i-1][j-1]
     * 2. rule[i]为？时，s[i]没有要求，但是必须存在。即 dp[i][j] = dp[i-1][j-1] && i < s.length
     * 3. rule[i]为*时，s[i]同样没有要求，但是可以匹配0个或多个任意字符。
     * - 匹配0个字符时，dp[i][j] = dp[i][j-1]
     * - 匹配多个字符时，dp[i][j] = dp[i-1][j]
     */
    public boolean isMatch(String s, String p) {
        if ("*".equals(p)) {
            return true;
        }
        boolean[][] dp = new boolean[s.length()][p.length()];
        int i = 0;
        int j = 0;
        while (i < s.length() && j < p.length()) {
            char rule = p.charAt(j);
            char c = s.charAt(i);
            switch (rule) {
                case '?':
                    dp[i][j] = dp[i - 1][j - 1];
                    i++;
                    j++;
                    break;
                case '*':
                    break;
                default:
                    dp[i][j] = rule == c && dp[i - 1][j - 1];
                    i++;
                    j++;
                    break;
            }
        }

        return false;
    }
}
