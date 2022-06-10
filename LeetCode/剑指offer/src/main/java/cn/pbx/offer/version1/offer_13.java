package cn.pbx.offer.version1;

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
 * f(i,j,k) = [f(i,j-1,k) or f(i-1,j,k)] and canVisit(i,j,k), 当i>=1且j>=1时成立
 * f(i,0,k) = f(i-1,0,k) and canVisit(i,0,k), 当j=0时成立
 * f(0,j,k) = f(0,j-1,k) and canVisit(0,j,k), 当j=0时成立
 *
 * @author BruceXu
 * @date 2022/2/10
 */
public class offer_13 {

    private final Log log = LogFactory.get();

    public static void main(String[] args) {
        offer_13 demo = new offer_13();
        int m = 4;
        int n = 6;
        int k = 15;
        int i = demo.movingCount(m, n, k);
        System.out.println(i);
    }

    public int movingCount(int m, int n, int k) {
        int sum = 1;
        boolean[][] table = new boolean[m][n];
        // 需要先把第0行和第0列填充起来
        for (int i = 1; i < m; i++) {
            if (i < 10) {
                table[i][0] = i <= k;
            } else {
                table[i][0] = table[i - 1][0] && canVisit(i, 0, k);
            }
            if (table[i][0]) {
                sum++;
            }
        }
        for (int i = 1; i < n; i++) {
            if (i < 10) {
                table[0][i] = i <= k;
            } else {
                table[0][i] = table[0][i - 1] && canVisit(i, 0, k);
            }
            if (table[0][i]) {
                sum++;
            }
        }


        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                table[i][j] = (table[i - 1][j] || table[i][j - 1]) && canVisit(i, j, k);
                if (table[i][j]) {
                    sum++;
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
        return k >= sum;
    }

}
