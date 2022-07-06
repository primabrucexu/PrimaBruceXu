package cn.pbx.practise.bt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Bruce Xu
 * @date 2022/7/5
 */
public class No51 {
    public static void main(String[] args) {
        No51 demo = new No51();
        List<List<String>> lists = demo.solveNQueens(4);
        for (List<String> list : lists) {
            for (String s : list) {
                System.out.println(s);
            }
            System.out.println("=============");
        }
    }

    public List<List<String>> solveNQueens(int n) {
        List<List<String>> res = new ArrayList<>();
        boolean[][] flag = new boolean[n][n];
        char[][] map = new char[n][n];
        for (char[] chars : map) {
            Arrays.fill(chars, '.');
        }
        bt(res, flag, map, n, 0);
        return res;
    }

    private void bt(List<List<String>> res, boolean[][] flag, char[][] map, int n, int level) {
        if (level == n) {
            List<String> list = new ArrayList<>();
            for (char[] chars : map) {
                list.add(new String(chars));
            }
            res.add(list);
            return;
        }
        // 对位于level层的皇后进行安排
        for (int i = 0; i < n; i++) {
            if (flag[level][i]) {
                continue;
            }
            map[level][i] = 'Q';
            List<int[]> list = placeQueue(flag, level, i, n);
            bt(res, flag, map, n, level + 1);
            for (int[] ints : list) {
                flag[ints[0]][ints[1]] = false;
            }
            map[level][i] = '.';
        }
    }

    /**
     * 注意点：
     * 1.  修改的时候，要记录修改了哪些节点。不然可能会修改到上一个皇后限制的区域
     * 2.  由于我们逐行放置，所以不需要对行进行限制，只需要对列和斜线进行限制
     *
     * @param flag
     * @param row
     * @param column
     * @param n
     * @return
     */
    private List<int[]> placeQueue(boolean[][] flag, int row, int column, int n) {
        List<int[]> res = new ArrayList<>();
        for (int i = row + 1; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (flag[i][j]) {
                    continue;
                }
                if (j == column || i - j == row - column || i + j == column + row) {
                    flag[i][j] = true;
                    res.add(new int[]{i, j});
                }
            }
        }
        return res;
    }


}
