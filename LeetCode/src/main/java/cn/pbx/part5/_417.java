package cn.pbx.part5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author BruceXu
 * @date 2020/12/13
 */
public class _417 {
    public static void main(String[] args) {
        int[][] matrix = new int[][]{
                {1, 2, 2, 3, 5},
                {3, 2, 3, 4, 4},
                {2, 4, 5, 3, 1},
                {6, 7, 1, 4, 5},
                {5, 1, 1, 2, 4}
        };
        System.out.println(pacificAtlantic(matrix));
    }


    public static List<List<Integer>> pacificAtlantic(int[][] matrix) {
        if (matrix.length == 0) {
            return null;
        }
        List<List<Integer>> res = new ArrayList<>();
        int n = matrix.length;
        int m = matrix[0].length;
        boolean[][] toPacific = new boolean[n][m];
        boolean[][] toAtlantic = new boolean[n][m];

        // 从两个海洋出发，进行搜索，
        for (int i = 0; i < n; i++) {
            dfs(matrix, toPacific, i, 0);
            dfs(matrix, toAtlantic, i, m - 1);
        }
        for (int i = 0; i < m; i++) {
            dfs(matrix, toPacific, 0, i);
            dfs(matrix, toAtlantic, n - 1, i);
        }

        // 取交集
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (toAtlantic[i][j] && toPacific[i][j]) {
                    res.add(Arrays.asList(i, j));
                }
            }
        }
        return res;
    }

    /**
     * @param matrix 矩阵
     * @param flag   状态表
     * @param i      纵坐标
     * @param j      横坐标
     */
    private static void dfs(int[][] matrix, boolean[][] flag, int i, int j) {
        if (i < 0 || j < 0 || i == matrix.length || j == matrix[0].length || flag[i][j]) {
            return;
        }
        flag[i][j] = true;

        // 因为我们倒过来进行搜索，要能流过滤，也就是说要满足这个点的高度小于等于某个方向上的高度，这样才能流过来
        if (i > 0 && matrix[i][j] <= matrix[i - 1][j]) {
            dfs(matrix, flag, i - 1, j);
        }
        if (i < matrix.length - 1 && matrix[i][j] <= matrix[i + 1][j]) {
            dfs(matrix, flag, i + 1, j);
        }
        if (j > 0 && matrix[i][j] <= matrix[i][j - 1]) {
            dfs(matrix, flag, i, j - 1);
        }
        if (j < matrix[0].length - 1 && matrix[i][j] <= matrix[i][j + 1]) {
            dfs(matrix, flag, i, j + 1);
        }
    }


}
