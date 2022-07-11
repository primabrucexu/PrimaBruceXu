package cn.pbx.practise.DynamicProgramming;

/**
 * @author Bruce Xu
 * @date 2022/7/8
 */
public class No44 {
    public static void main(String[] args) {
        No44 demo = new No44();
        String s = "";
        String p = "******";
        System.out.println(demo.isMatch(s, p));
    }

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
     * <p>
     * 归纳总结之后，可以得出dp[i][j]为如下情况
     */
    public boolean isMatch(String s, String p) {
        if ("*".equals(p)) {
            return true;
        }
        int pos = -1;
        for (int i = 0; i < p.length(); i++) {
            if (p.charAt(i) == '*' || p.charAt(i) == '?') {
                pos = i;
                break;
            }
        }
        if (pos == -1) {
            return s.equals(p);
        }

        int m = s.length();
        int n = p.length();
        boolean[][] dp = new boolean[m + 1][n + 1];
        dp[0][0] = true;
        // 用于处理全是*号但是s为空串的情况
        for (int i = 0; i < n; i++) {
            if (p.charAt(i) == '*') {
                dp[0][i + 1] = true;
            } else {
                break;
            }
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                char c = p.charAt(j - 1);
                if (c == '*') {
                    dp[i][j] = dp[i - 1][j] || dp[i][j - 1];
                } else if (c == '?') {
                    dp[i][j] = dp[i - 1][j - 1];
                } else if (s.charAt(i - 1) == c) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = false;
                }
            }
        }

        return dp[m][n];
    }

}
