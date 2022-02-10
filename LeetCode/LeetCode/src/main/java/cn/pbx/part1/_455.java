package cn.pbx.part1;

import java.util.Arrays;

/**
 * @author BruceXu
 * @date 2020/11/2
 */
public class _455 {
    public int findContentChildren(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);
        int child = 0;
        int cookies = 0;
        while (child < g.length && cookies < s.length) {
            if (g[child] <= s[cookies]) {
                child++;
            }
            cookies++;
        }
        return child;
    }
}
