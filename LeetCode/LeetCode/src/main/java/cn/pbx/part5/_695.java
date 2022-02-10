package cn.pbx.part5;

/**
 * @author BruceXu
 * @date 2020/12/12
 */
public class _695 {
    public static void main(String[] args) {
        int[][] map = new int[][]{
                {0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
                {0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0},
                {0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0}};
        System.out.println(maxAreaOfIsland(map));

    }


    /**
     * 先遍历地图，找到一个为1的地方，从这个地方开始，进行dfs搜索，
     * 为了防止重复计算面积，在计算面积的时候，记录已经搜索过的地方
     */
    public static int maxAreaOfIsland(int[][] grid) {
        if (grid.length == 0) {
            return 0;
        }
        int maxArea = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                int area = dfs(i, j, grid);
                maxArea = Math.max(maxArea, area);
            }
        }

        return maxArea;
    }

    private static int dfs(int i, int j, int[][] grid) {
        if (i < 0 || j < 0 || i == grid.length || j == grid[0].length || grid[i][j] == 0) {
            return 0;
        }
        int size = 1;
        grid[i][j] = 0;
        // 这个点的面积 S =  s上 + s下 + s左 + s右
        size += dfs(i, j - 1, grid) + dfs(i, j + 1, grid) + dfs(i - 1, j, grid) + dfs(i + 1, j, grid);

        return size;
    }

}

