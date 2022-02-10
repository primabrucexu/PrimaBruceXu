package cn.pbx.part5;

/**
 * @author BruceXu
 * @Date 2020/12/13
 */
public class _79 {
    public static void main(String[] args) {
        char[][] array = new char[][]{
                {'A', 'B', 'C', 'E'},
                {'S', 'F', 'C', 'S'},
                {'A', 'D', 'E', 'E'}
        };
        long currentTimeMillis = System.currentTimeMillis();
        System.out.println(exist(array, "ABCB"));
        System.out.println(System.currentTimeMillis() - currentTimeMillis);

    }

    public static boolean exist(char[][] board, String word) {
        if (board.length == 0) {
            return false;
        }
        boolean[][] flag = new boolean[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (dfs(board, word, flag, 0, i, j)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param board 字母表
     * @param word  单次
     * @param flag  标记数组
     * @param k     单词搜索到的位置
     * @param i     横坐标
     * @param j     纵坐标
     * @return 查找结果
     */
    private static boolean dfs(char[][] board, String word, boolean[][] flag, int k, int i, int j) {
        if (word.charAt(k) != board[i][j]) {
            return false;
        }
        if (k == word.length() - 1) {
            return true;
        }
        // 向上下左右四个方向去搜索
        flag[i][j] = true;
        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        boolean result = false;
        for (int[] dir : directions) {
            int newi = i + dir[0];
            int newj = j + dir[1];
            if (newi >= 0 && newi < board.length && newj >= 0 && newj < board[0].length) {
                if (!flag[newi][newj]) {
                    boolean p = dfs(board, word, flag, k + 1, newi, newj);
                    if (p) {
                        result = true;
                        break;
                    }
                }
            }
        }
        flag[i][j] = false;
        return result;
    }
}
