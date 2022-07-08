package cn.pbx.practise.DFS;

/**
 * @author Bruce Xu
 * @date 2022/7/8
 */
public class No695 {
    public int maxAreaOfIsland(int[][] grid) {
        if (grid.length == 0) {
            return 0;
        }
        int maxArea = 0;
        boolean[][] flag = new boolean[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1 && !flag[i][j]) {
                    int area = dfs(grid, flag, i, j);
                    maxArea = Math.max(area, maxArea);
                } else {
                    flag[i][j] = true;
                }
            }
        }
        return maxArea;
    }

    private int dfs(int[][] grid, boolean[][] flag, int i, int j) {
        // 超出边界
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length) {
            return 0;
        }
        // 已经计算过了，不能重复计算
        if (grid[i][j] == 0 || flag[i][j]) {
            return 0;
        }
        flag[i][j] = true;
        return 1 + dfs(grid, flag, i - 1, j) + dfs(grid, flag, i + 1, j) + dfs(grid, flag, i, j - 1) + dfs(grid, flag, i, j + 1);
    }


}
