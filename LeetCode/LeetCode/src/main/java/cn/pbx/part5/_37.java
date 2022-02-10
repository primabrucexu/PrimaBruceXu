package cn.pbx.part5;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author BruceXu
 * @date 2020-12-20
 */

public class _37 {

    public static void main(String[] args) throws IOException {
        char[][] p = {
                {'5', '3', '.', '.', '7', '.', '.', '.', '.'},
                {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
                {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
                {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
                {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
                {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
                {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
                {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
                {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
        };
        solveSudoku(p);
    }

    public static void solveSudoku(char[][] board) {
        boolean[][] row = new boolean[9][9];
        boolean[][] column = new boolean[9][9];
        boolean[][][] block = new boolean[3][3][9];
        List<int[]> pos = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] != '.') {
                    int p = board[i][j] - '1';
                    row[i][p] = true;
                    column[j][p] = true;
                    block[i / 3][j / 3][p] = true;
                } else {
                    pos.add(new int[]{i, j});
                }
            }
        }
        boolean[] flag = new boolean[1];
        dfs(board, pos, 0, row, column, block, flag);
    }

    private static void dfs(char[][] board, List<int[]> pos, int n, boolean[][] row, boolean[][] column, boolean[][][] block, boolean[] flag) {
        if (n == pos.size()) {
            flag[0] = true;
            return;
        }
        int[] o = pos.get(n);
        int r = o[0];
        int c = o[1];
        for (int i = 0; i < 9; i++) {
            if (!row[r][i] && !column[c][i] && !block[r / 3][c / 3][i] && !flag[0]) {
                row[r][i] = true;
                column[c][i] = true;
                block[r / 3][c / 3][i] = true;
                board[r][c] = (char) (i + '1');
                dfs(board, pos, n + 1, row, column, block, flag);
                row[r][i] = false;
                column[c][i] = false;
                block[r / 3][c / 3][i] = false;
            }
        }
    }
}