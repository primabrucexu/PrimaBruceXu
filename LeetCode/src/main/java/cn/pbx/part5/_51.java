package cn.pbx.part5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author BruceXu
 * @date 2020/12/13
 */
public class _51 {
    public static void main(String[] args) {
        List<List<String>> res = solveNQueens(5);
        for (List<String> list : res) {
            System.out.println("============");
            for (String s : list) {
                System.out.println(s);
            }
            System.out.println("============");
        }
        System.out.println(res.size());
    }

    public static List<List<String>> solveNQueens(int n) {
        List<List<String>> res = new ArrayList<>();
        if (n == 0) {
            return res;
        }
        // 创建棋盘
        List<char[]> map = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            char[] p = new char[n];
            Arrays.fill(p, '.');
            map.add(p);
        }

        // 创建访问数组
        boolean[][] flag = new boolean[n][n];
        NQueens(res, map, flag, 0, n);
        return res;
    }

    /**
     * @param res   结果集
     * @param map   棋盘
     * @param flag  位置数组
     * @param index 第几行的皇后需要安排
     * @param n     皇后的数量
     */
    private static void NQueens(List<List<String>> res, List<char[]> map, boolean[][] flag, int index, int n) {
        if (index == n) {
            List<String> list = new ArrayList<>();
            for (char[] chars : map) {
                list.add(new String(chars));
            }
            res.add(list);
            return;
        }
        // 对在index行上的皇后依次安排
        for (int i = 0; i < n; i++) {
            if (flag[index][i]) {
                continue;
            }
            map.get(index)[i] = 'Q';
            List<int[]> temp = setFlag(n, flag, index, i);
            NQueens(res, map, flag, index + 1, n);
            // 把上次修改的改回来。思考为什么不能直接复用setFlag函数？
            // 假设我们在试探第二行的皇后怎么安排时，如果简单的根据试探时的位置进行恢复，可能会修改到第一行皇后的限制，因此需要额外记录
            for (int[] p : temp) {
                flag[p[0]][p[1]] = false;
            }
            map.get(index)[i] = '.';
        }
    }

    /**
     * 记录新增皇后后修改的节点。这里要注意，原有不可放置的地方不能被记录
     * 因为恢复的时候要按照这里记录的信息进恢复
     *
     * @param n      皇后的数量
     * @param flag   状态表
     * @param row    新增皇后的行号
     * @param column 新增行号的列号
     * @return 新增皇后之后，修改的状态表节点
     */
    private static List<int[]> setFlag(int n, boolean[][] flag, int row, int column) {
        List<int[]> res = new ArrayList<>();
        for (int nr = row + 1; nr < n; nr++) {
            for (int nc = 0; nc < n; nc++) {
                if (flag[nr][nc]) {
                    continue;
                }
                if (nc == column || nc + nr == column + row || nc - nr == column - row) {
                    flag[nr][nc] = true;
                    res.add(new int[]{nr, nc});
                }
            }
        }
        return res;
    }
}
