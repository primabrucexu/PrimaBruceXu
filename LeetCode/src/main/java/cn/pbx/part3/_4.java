package cn.pbx.part3;

/**
 * @author BruceXu
 * @date 2020/11/17
 */
public class _4 {

    public static void main(String[] args) {
        int[] nums1 = new int[]{1};
        int[] nums2 = new int[]{2, 3, 4};
        double medianSortedArrays = findMedianSortedArrays(nums1, nums2);
        System.out.println(medianSortedArrays);

    }

    /**
     * 核心思想：找到第k个小的数字
     * 在两个数组中依次比较第 k / 2 个数字，谁小，就说明该数组的前 k / 2 个数字都不是第 k 个小的数字，然后缩减该数组，最后求的第k个小的数字
     */
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int len = nums1.length + nums2.length;
        int k = len / 2;
        if (len % 2 == 0) {
            int r1 = findKth(nums1, nums2, k);
            int r2 = findKth(nums1, nums2, k + 1);
            return (r1 + r2) / 2.0;
        } else {
            return findKth(nums1, nums2, k + 1);
        }
    }

    private static int findKth(int[] nums1, int[] nums2, int k) {
        int length1 = nums1.length;
        int length2 = nums2.length;

        // index表示的是排除元素之后，“新数组” 的开始位置
        int index1 = 0;
        int index2 = 0;

        while (true) {
            // 如果其中一个数组为空，则返回另外一个数组的中位数
            if (index1 == length1) {
                return nums2[index2 + k - 1];
            }
            if (index2 == length2) {
                return nums1[index1 + k - 1];
            }
            // 当 k=1 时退出循环
            if (k == 1) {
                return Math.min(nums1[index1], nums2[index2]);
            }

            int half = k / 2;
            // 这步操作保证了newIndex不会超出数组长度
            int newIndex1 = Math.min(index1 + half, length1) - 1;
            int newIndex2 = Math.min(index2 + half, length2) - 1;
            int pivot1 = nums1[newIndex1], pivot2 = nums2[newIndex2];

            // k要减去每次排除的元素个数
            if (pivot1 <= pivot2) {
                k -= (newIndex1 - index1 + 1);
                index1 = newIndex1 + 1;
            } else {
                k -= (newIndex2 - index2 + 1);
                index2 = newIndex2 + 1;
            }
        }
    }
}
