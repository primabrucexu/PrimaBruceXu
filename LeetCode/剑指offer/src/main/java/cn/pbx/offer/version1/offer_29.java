package cn.pbx.offer.version1;

import lombok.ToString;

import java.util.Arrays;

/**
 * @author BruceXu
 * @date 2022/6/9
 */
public class offer_29 {

    public static void main(String[] args) {
        offer_29 demo = new offer_29();
        int[][] matrix = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}};

        System.out.println(Arrays.toString(demo.spiralOrder(matrix)));
    }

    public int[] spiralOrder(int[][] matrix) {
        if (matrix.length == 0) {
            return new int[0];
        }

        int rows = matrix.length;
        int columns = matrix[0].length;
        int total = rows * columns;
        int[] res = new int[total];

        // 是否被读取过
        boolean[][] flag = new boolean[rows][columns];
        Index index = new Index();
        index.row = 0;
        index.column = 0;
        index.direction = Direction.right;

        for (int i = 0; i < total; i++) {
            System.out.println(i + " --> " + index);
            res[i] = matrix[index.row][index.column];
            flag[index.row][index.column] = true;
            index = getNextIndex(flag, matrix, index);
        }

        return res;
    }

    private Index getNextIndex(boolean[][] flag, int[][] matrix, Index oldIndex) {
        Index newIndex = new Index();
        int column = oldIndex.column;
        int row = oldIndex.row;
        switch (oldIndex.direction) {
            case right:
                if (column + 1 < matrix[0].length && !flag[row][column + 1]) {
                    newIndex.column = column + 1;
                    newIndex.row = row;
                    newIndex.direction = Direction.right;
                }
                if (newIndex.isLegal()) {
                    break;
                }
            case down:
                if (row + 1 < matrix.length && !flag[row + 1][column]) {
                    newIndex.column = column;
                    newIndex.row = row + 1;
                    newIndex.direction = Direction.down;
                }
                if (newIndex.isLegal()) {
                    break;
                }
            case left:
                if (column - 1 >= 0 && !flag[row][column - 1]) {
                    newIndex.column = column - 1;
                    newIndex.row = row;
                    newIndex.direction = Direction.left;
                }
                if (newIndex.isLegal()) {
                    break;
                }
            case up:
                if (row - 1 >= 0 && !flag[row - 1][column]) {
                    newIndex.column = column;
                    newIndex.row = row - 1;
                    newIndex.direction = Direction.up;
                }
                if (newIndex.isLegal()) {
                    break;
                }
            default:
                // 向上走到尽头时，则向右走
                newIndex.column = column + 1;
                newIndex.row = row;
                newIndex.direction = Direction.right;
                break;
        }
        return newIndex;
    }

    enum Direction {
        right,
        left,
        up,
        down
    }

    @ToString
    static class Index {
        int row = -1;
        int column = -1;
        Direction direction;

        public boolean isLegal() {
            return row != -1 && column != -1;
        }

    }

}
