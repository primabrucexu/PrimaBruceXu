package cn.pbx.Algorithm.middle;

/**
 * 经典动态规划
 *
 * @author BruceXu
 * @date 2020/10/3
 */
public class Solution_1143_longestCommonSubsequence {
    public static void main(String[] args) {

    }

    public int longestCommonSubsequence(String text1, String text2) {
        int[][] dpTable = new int[text1.length() + 1][text2.length() + 1];
        for (int i = 0; i < text1.length(); i++) {
            for (int j = 0; j < text2.length(); j++) {
                if (text1.charAt(i) == text2.charAt(j)) {
                    dpTable[i + 1][j + 1] = dpTable[i][j] + 1;
                } else {
                    dpTable[i + 1][j + 1] = Math.max(dpTable[i + 1][j], dpTable[i][j + 1]);
                }
            }
        }

        return dpTable[text1.length() - 1][text2.length() - 1];
    }

}
