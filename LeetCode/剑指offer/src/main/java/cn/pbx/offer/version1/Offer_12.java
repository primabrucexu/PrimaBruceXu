package cn.pbx.offer.version1;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;

/**
 * 回溯
 *
 * @author BruceXu
 * @date 2022/2/10
 */
public class Offer_12 {

    private static final Log log = LogFactory.get();

    public static void main(String[] args) {
        char[][] board = {
                {'A', 'B', 'C', 'E'}, {'S', 'F', 'C', 'S'}, {'A', 'D', 'E', 'E'}
        };
        String word = "ABCCED";
        Offer_12 demo = new Offer_12();
        boolean exist = demo.exist(board, word);
        System.out.println(exist);
    }

    public boolean exist(char[][] board, String word) {
        // 找到矩阵中word所有开头的位置。从这些位置开始开始尝试遍历矩阵，寻找word
        boolean flag = false;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (word.charAt(0) == board[i][j]) {
                    flag = next(board, word, i, j, 0);
                    if (flag) {
                        return true;
                    }
                }
            }
        }
        return flag;
    }

    private boolean next(char[][] board, String word, int i, int j, int pos) {
        log.info("pos in board - [{},{}], pos in word - [{}], deque - [{}]", i, j, pos);
        if (pos == word.length()) {
            return true;
        }
        if (i >= board.length || j >= board[0].length || i < 0 || j < 0) {
            return false;
        }
        if (board[i][j] != word.charAt(pos)) {
            return false;
        } else {
            board[i][j] = ' ';
            // 向上尝试
            boolean up = next(board, word, i - 1, j, pos + 1);
            if (up) {
                return true;
            }

            // 向下尝试
            boolean down = next(board, word, i + 1, j, pos + 1);
            if (down) {
                return true;
            }

            // 向左尝试
            boolean left = next(board, word, i, j - 1, pos + 1);
            if (left) {
                return true;
            }

            // 向右尝试
            boolean right = next(board, word, i, j + 1, pos + 1);
            if (right) {
                return true;
            }
            // 改了什么都要改回来
            board[i][j] = word.charAt(pos);
            return false;
        }
    }


}
