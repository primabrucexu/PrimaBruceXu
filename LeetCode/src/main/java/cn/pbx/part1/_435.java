package cn.pbx.part1;

import java.util.Arrays;

/**
 * @author BruceXu
 * @date 2020/11/3
 */
public class _435 {
    public static void main(String[] args) {
        int[][] arrays = new int[][]{{1, 100}, {11, 22}, {1, 11}, {2, 12}};
        int i = eraseOverlapIntervals(arrays);
        System.out.println(i);
    }

    public static int eraseOverlapIntervals(int[][] intervals) {
        if (intervals.length <= 1)
            return 0;
        // 先排序
        Arrays.sort(intervals);
        for (int[] interval : intervals) {
            System.out.println(interval[0] + "-" + interval[1]);
        }
        int res = 0;
        int[] prev = intervals[0];
        // 如果当前区间和prev区间相交，那么则删除。
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] < prev[1]) {
                res++;
            } else {
                prev = intervals[i];
            }
        }
        return res;
    }
}
