package cn.pbx.part5;

/**
 * @author BruceXu
 * @date 2020-12-19
 */

public class _130 {
    public static void main(String[] args) {
        char[][] board = {
                {'O', 'O', 'O'},
                {'O', 'O', 'O'},
                {'O', 'O', 'O'},
        };
        solve(board);
    }

    // 因为任何与边界相连的O都不会被填充为X，那么我们就从边界开始寻找。找到所有不会被填充的位置，然后填充除了这些位置之外的位置
    public static void solve(char[][] board) {
        if (board.length == 0) {
            return;
        }
        boolean[][] flag = new boolean[board.length][board[0].length];
        int row = board.length;
        int coloum = board[0].length;
        // 先搜索上下边界的点
        for (int i = 0; i < coloum; i++) {
            if (board[0][i] == 'O' && !flag[0][i]) {
                dfs(board, flag, 0, i);
            }
            if (board[row - 1][i] == 'O' && !flag[row - 1][i]) {
                dfs(board, flag, row - 1, i);
            }
        }
        // 再搜索左右边界的点
        for (int i = 0; i < row; i++) {
            if (board[i][0] == 'O' && !flag[i][0]) {
                dfs(board, flag, i, 0);
            }
            if (board[i][coloum - 1] == 'O' && !flag[i][coloum - 1]) {
                dfs(board, flag, i, coloum - 1);
            }
        }
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < coloum; j++) {
                if (board[i][j] != 'X' && !flag[i][j]) {
                    board[i][j] = 'X';
                }
            }
        }
    }

    private static void dfs(char[][] board, boolean[][] flag, int r, int c) {
        if (board[r][c] != 'O') {
            return;
        }
        flag[r][c] = true;
        int[][] pos = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int i = 0; i < 4; i++) {
            int nr = r + pos[i][0];
            int nc = c + pos[i][1];
            if (nr >= 0 && nc >= 0 && nr < board.length && nc < board[0].length && !flag[nr][nc]) {
                dfs(board, flag, nr, nc);
            }
        }
    }
}
