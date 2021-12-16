package cn.pbx.part5;

/**
 * @author BruceXu
 * @date 2020/12/13
 */
public class _547 {

    public static void main(String[] args) {
        int[][] M = new int[][]{{1, 1, 0},
                {1, 1, 0},
                {0, 0, 1}};
        System.out.println(findCircleNum(M));
    }

    public static int findCircleNum(int[][] M) {
        if (M.length == 0) {
            return 0;
        }
        int n = 0;
        boolean[] flag = new boolean[M.length];
        for (int i = 0; i < M.length; i++) {
            // 如果同学i没有搜索过，则搜索同学i
            if (!flag[i]) {
                // 标记同学i已经被搜索
                flag[i] = true;
                dfs(i, M, flag);
                n++;
            }
        }
        return n;
    }

    private static void dfs(int i, int[][] M, boolean[] flag) {
        // 搜索同学i的朋友圈
        for (int k = 0; k < M.length; k++) {
            if (!flag[k] && M[i][k] == 1) {
                flag[k] = true;
                dfs(k, M, flag);
            }
        }
    }

}
