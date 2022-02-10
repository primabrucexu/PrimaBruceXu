package cn.pbx.part4;

/**
 * @author BruceXu
 * @date 2020/11/27
 */
public class _215 {

    public static void main(String[] args) {
        // [1, 2, 3, 4, 5, 6]
        int[] array = new int[]{3, 2, 1, 5, 6, 4};
        // [1, 2, 2, 3, 3, 4, 5, 5, 6]
        int[] array2 = new int[]{3, 2, 3, 1, 2, 4, 5, 5, 6};
//        for (int i = 1; i <= array.length; i++) {
//            System.out.println(findKthLargest(array, i));
//        }
//        System.out.println("==============");
//        for (int i = 1; i <= array2.length; i++) {
//            System.out.println(findKthLargest(array2, i));
//        }
//        System.out.println("==============");
        System.out.println(findKthLargest(array, 1));

    }

    public static int findKthLargest(int[] nums, int k) {
        int left = 0;
        int right = nums.length - 1;
        int target = nums.length - k;
        while (left < right) {
            int p = quickSort(nums, left, right);
            if (p < target) {
                left = p + 1;
            } else if (p > target) {
                right = p - 1;
            } else {
                return nums[p];
            }
            System.out.println(left);
        }
        return nums[left];
    }

    // 快速排序函数，返回key的下标
    public static int quickSort(int[] array, int left, int right) {
        int i = left;
        int j = right;
        int key = array[left];
        while (i < j) {
            while (i < j && array[j] >= key) {
                j--;
            }
            if (i < j) {
                array[i] = array[j];
                i++;
            }
            while (i < j && array[i] < key) {
                i++;
            }
            if (i < j) {
                array[j] = array[i];
                j--;
            }
        }
        array[i] = key;
        return i;
    }


}
