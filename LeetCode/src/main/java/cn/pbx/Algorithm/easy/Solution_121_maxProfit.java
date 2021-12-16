package cn.pbx.Algorithm.easy;

/**
 * https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock/
 *
 * @author BruceXu
 * @date 2020/9/26
 */
public class Solution_121_maxProfit {

    public static void main(String[] args) {
        int[] prices = new int[]{7, 1, 5, 3, 6, 4};
        System.out.println(maxProfit(prices));
    }

    /**
     * 假设在第i天买入，在第j天卖出，那么收益则为prices[j]-prices[i]，
     * 假设可以允许如下操作：可以在第i天买，第k天卖出，然后又在第k天买入，最后在第j天卖出不再买入，(假设i<k<j)
     * 根据上述逻辑，即可求出数组中相隔一天买卖的收益，从而求出连续那几天买卖的收益最高
     * 类似于求数组中最大和的子数组
     */
    public static int maxProfit(int[] prices) {
        int get = 0;
        int maxGet = 0;
        for (int i = 1; i < prices.length; i++) {
            get += prices[i] - prices[i - 1];
            if (get < 0) {
                get = 0;
            }
            get = Math.max(get, prices[i] - prices[i - 1]);
            maxGet = Math.max(get, maxGet);
        }
        return maxGet;
    }
}
