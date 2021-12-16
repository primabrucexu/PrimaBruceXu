package cn.pbx.Algorithm.easy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author BruceXu
 * @date 2020/9/26
 */
public class Solution_70_climbStairs {
    public static void main(String[] args) {

    }


    public int climbStairs(int n) {
        // 若最后爬1层，则前面爬楼的方法总数为爬n-1层楼的方法
        // 若最后爬2层，则前面爬楼的方法总数为爬n-2层楼的方法
        // 方法总数为爬n-1层的方法+爬n-2层的方法之和
        if (n <= 2) {
            return n;
        }
        int x1 = 1;
        int x2 = 2;
        int result = 0;
        for (int i = 3; i <= n; i++) {
            result = x1 + x2;
            x1 = x2;
            x2 = result;
        }
        return result;
    }
}
