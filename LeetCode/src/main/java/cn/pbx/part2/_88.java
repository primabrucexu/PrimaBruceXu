package cn.pbx.part2;

/**
 * @author BruceXu
 * @date 2020/11/7
 */
public class _88 {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int[] backup = new int[m];
        System.arraycopy(nums1, 0, backup, 0, m);
        int p1 = 0;
        int p2 = 0;
        int i = 0;
        while (p1 < m && p2 < n) {
            if (backup[p1] <= nums2[p2]) {
                nums1[i] = backup[p1];
                p1++;
            } else {
                nums1[i] = nums2[p2];
                p2++;
            }
            i++;
        }
        // 假设其中一个已经完成复制，剩下的数组中还有一些数据。
        // nums1中已经有了p1+p2个数据，一共要复制m+n个数据，则剩下的数据量为m+n-p1-p2
        if (p1 < m) {
            System.arraycopy(backup, p1, nums1, p1 + p2, m + n - p1 - p2);
        }
        if (p2 < n) {
            System.arraycopy(nums2, p2, nums1, p1 + p2, m + n - p1 - p2);
        }
    }

    public void merge2(int[] nums1, int m, int[] nums2, int n) {
        int p1 = m - 1;
        int p2 = n - 1;
        int p = m + n - 1;
        while (p1 >= 0 && p2 >= 0) {
            if (nums1[p1] > nums2[p2]) {
                nums1[p] = nums1[p1];
                p1--;
            } else {
                nums1[p] = nums2[p2];
                p2--;
            }
            p--;
        }
        // 注意点：循环结束之后是什么情况？
        // 如果nums1用完了，剩下nums2。只需要把nums2中剩下的复制过去即可
        // 如果nums2用完了，剩下nums1。因为我们要用nums1作为新容器，所以不需要额外的复制操作
        if (p2 >= 0) {
            System.arraycopy(nums2, 0, nums1, 0, p2 + 1);
        }

    }
}
