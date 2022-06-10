package cn.pbx.offer.version1;

/**
 * @author BruceXu
 * @date 2022-01-15
 */
public class offer_04 {

    public boolean findNumberIn2DArray(int[][] matrix, int target) {
        if (matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }

        int n = matrix.length;
        int m = matrix[0].length;

        // 先判断在不在矩阵里
        if (matrix[0][0] > target || matrix[n - 1][m - 1] < target) {
            return false;
        }

        // 先判断每行和最后一个是不是包含target
        for (int[] ints : matrix) {
            if (ints[0] <= target && ints[m - 1] >= target) {
                // 遍历这行，尝试寻找target
                for (int j = 0; j < m; j++) {
                    if (ints[j] == target) {
                        return true;
                    } else if (ints[j] > target) {
                        break;
                    }
                }
            }
        }

        return false;
    }

}
