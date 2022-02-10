package cn.pbx.Algorithm.middle;

/**
 * 动态规划经典例子
 *
 * @author BruceXu
 * @date 2020/10/3
 */
public class Solution_718_findLength {

    public static void main(String[] args) {
        int[] A = new int[]{1, 2, 3, 2, 1};
        int[] B = new int[]{3, 2, 1, 4, 7};
        int r = findLength(A, B);
        System.out.println(r);
    }

    // 未优化空间复杂度
    public static int findLength(int[] A, int[] B) {
        int[][] dpTable = new int[A.length + 1][B.length + 1];
        int result = 0;
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < B.length; j++) {
                if (A[i] == B[j]) {
                    dpTable[i + 1][j + 1] = dpTable[i][j] + 1;
                    result = Math.max(dpTable[i + 1][j + 1], result);
                } else {
                    dpTable[i + 1][j + 1] = 0;
                }
            }
        }
        return result;
    }
}
