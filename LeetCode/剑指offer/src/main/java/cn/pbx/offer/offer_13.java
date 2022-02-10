package cn.pbx.offer;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;

/**
 * 对于在同一行或列上的方格，如果第i个无法访问，那么第i+1个一定无法访问。因为机器人一次只能走一格
 * 所以，每当从新的一行开始，那么必定会比上一行少访问一个格子
 * 当在第0行时，最多可以访问k个格子
 * 当在第1行是，最多可以访问k-1个格子
 * <p>
 * 因为机器人一次只能走一格，所以不用考虑当k=6时，对于格子[10][0]这类型的访问情况，因为机器人在走到[6][0]时就不能继续往下面走了
 * 但需要考虑当k=9时，对于格子[10][10]的访问情况：当k=9是，可以访问[10][8]，但不能访问[10][9]，所以不能访问[10][10]
 * <p>
 * 状态转移方程:
 *
 * @author BruceXu
 * @date 2022/2/10
 */
public class offer_13 {

    private final Log log = LogFactory.get();

    public static void main(String[] args) {
        offer_13 demo = new offer_13();
        int i = demo.movingCount(16, 8, 4);
        System.out.println(i);
    }

    public int movingCount(int m, int n, int k) {
        int sum = 0;
        boolean[][] table = new int[m][n];
        // 需要先把第0行和第0列填充起来


        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (canVisit(i, j, k)) {
                    table[i][j] =
                }
            }
        }

        return sum;
    }

    private boolean canVisit(int i, int j, int k) {
        int sum = 0;
        String s = String.valueOf(i);
        for (int n = 0; n < s.length(); n++) {
            sum += Integer.parseInt(s.substring(n, n + 1));
        }
        s = String.valueOf(j);
        for (int n = 0; n < s.length(); n++) {
            sum += Integer.parseInt(s.substring(n, n + 1));
        }
        return k > sum;
    }

}
