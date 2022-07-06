package cn.pbx.practise.bt;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bruce Xu
 * @date 2022/7/5
 */
public class No37 {

    public void solveSudoku(char[][] board) {
        // 构建限制条件
        // row[2][8] = true，表示第3行中不可以填9
        boolean[][] row = new boolean[9][9];
        // column[2][8] = true，表示第3列中不可以填9
        boolean[][] column = new boolean[9][9];
        // block[0][0][8] = true，表示第1个方块中不可以填8
        boolean[][][] block = new boolean[3][3][9];
        // 待填充的位置
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
        bt(board, pos, row, column, block, 0, flag);
    }

    private void bt(char[][] board, List<int[]> pos, boolean[][] row, boolean[][] column, boolean[][][] block, int n, boolean[] flag) {
        if (n == pos.size()) {
            // 当找到第一个符合条件的解的时候，及时停止递归
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
                board[r][c] = (char) ('1' + i);
                bt(board, pos, row, column, block, n + 1, flag);
                row[r][i] = false;
                column[c][i] = false;
                block[r / 3][c / 3][i] = false;
                board[r][c] = '.';
            }
        }
    }


}
