package cn.pbx.week.single.week300;

import cn.pbx.Algorithm.utils.ListNode;

import java.util.Arrays;

/**
 * @author Bruce Xu
 * @date 2022/7/3
 */
public class No6111 {
    public int[][] spiralMatrix(int m, int n, ListNode head) {
        int[][] matrix = new int[m][n];
        for (int[] nums : matrix) {
            Arrays.fill(nums, -1);
        }
        ListNode node = head;
        boolean[][] flag = new boolean[m][n];
        Pos pos = new Pos();
        while (node != null) {
            fill(node.val, matrix, pos, flag);
            node = node.next;
        }
        return matrix;
    }

    private void fill(int val, int[][] matrix, Pos pos, boolean[][] flag) {
        matrix[pos.m][pos.n] = val;
        pos.next(flag);
    }

//    public static void main(String[] args) {
//        No6111 demo = new No6111();
//        int m = 5;
//        int n = 4;
//        boolean[][] flag = new boolean[m][n];
//        Pos pos = new Pos();
//        for (int i = 0; i < m * n; i++) {
//            System.out.println(pos);
//            pos.next(flag);
//        }
//    }

    enum Direction {
        right,
        down,
        left,
        up
    }

    static class Pos {
        int m = 0;
        int n = 0;
        Direction direction = Direction.right;

        @Override
        public String toString() {
            return "Pos{" +
                    "m=" + m +
                    ", n=" + n +
                    ", direction=" + direction +
                    '}';
        }

        public void next(boolean[][] flag) {
            flag[m][n] = true;
            int nextM = m;
            int nextN = n;
            switch (direction) {
                case right:
                    nextN = n + 1;
                    if (nextN >= flag[0].length || flag[nextM][nextN]) {
                        direction = Direction.down;
                        nextM = m + 1;
                        nextN = n;
                    }
                    break;
                case down:
                    nextM = m + 1;
                    if (nextM >= flag.length || flag[nextM][nextN]) {
                        direction = Direction.left;
                        nextN = n - 1;
                        nextM = m;
                    }
                    break;
                case left:
                    nextN = n - 1;
                    if (nextN < 0 || flag[nextM][nextN]) {
                        direction = Direction.up;
                        nextM = m - 1;
                        nextN = n;
                    }
                    break;
                case up:
                    nextM = m - 1;
                    if (nextM < 0 || flag[nextM][nextN]) {
                        direction = Direction.right;
                        nextN = n + 1;
                        nextM = m;
                    }
                    break;
                default:
                    break;
            }
            this.m = nextM;
            this.n = nextN;
        }

    }

}
