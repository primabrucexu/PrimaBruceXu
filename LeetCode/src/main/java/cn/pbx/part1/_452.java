package cn.pbx.part1;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author BruceXu
 * @date 2020/11/3
 */
public class _452 {

    public static void main(String[] args) {
        int[][] array = new int[][]{{10, 16}, {2, 8}, {1, 6}, {7, 12}};
        int[][] array2 = new int[][]{{-2147483646, -2147483645}, {2147483646, 2147483647}};
        int minArrowShots = findMinArrowShots(array2);

        System.out.println(minArrowShots);
    }

    public static int findMinArrowShots(int[][] points) {
        if (points.length <= 1) {
            return points.length;
        }
        Arrays.sort(points, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[0] > o2[0])
                    return 1;
                else if (o1[0] < o2[0])
                    return -1;
                else {
                    if (o1[1] > o2[1])
                        return 1;
                    else if (o1[1] < o2[1])
                        return -1;
                    else
                        return 0;
                }
            }
        });
        int[] temp = points[0];
        int res = 1;
        for (int i = 1; i < points.length; i++) {
            // 相交的情况下
            if (temp[1] >= points[i][0]) {
                temp[0] = Math.max(temp[0], points[i][0]);
                temp[1] = Math.min(temp[1], points[i][1]);
            } else {
                temp = points[i];
                res++;
            }
        }
        return res;
    }
}
