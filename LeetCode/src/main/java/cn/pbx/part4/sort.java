package cn.pbx.part4;

import java.util.Arrays;


/**
 * @author BruceXu
 * @date 2020/11/21
 */
public class sort {

    public static void main(String[] args) {
        int[] array = new int[]{46, 55, 32, 0, 16, 8, 83, 43, 37, 83};
//        quickSort(array, 0, 9);
//        array = mergeSort(array);
//        insertSort(array);
//        bubbleSort(array);
//        selectSort(array);
//        heapSort(array);
        countingSort(array);
        System.out.println("====================");
        System.out.println(Arrays.toString(array));
    }

    /**
     * 快速排序
     *
     * @param array 需要排序区间所在的数组
     * @param left  区间的起始下标
     * @param right 区间的结束下标
     */
    public static void quickSort(int[] array, int left, int right) {
        if (left < right) {
            int i = left;
            int j = right;
            int key = array[left];
            while (i < j) {
                // 从j开始向左寻找到第一个比 key 小的数
                while (i < j && array[j] >= key) {
                    j--;
                }
                if (i < j) {
                    array[i] = array[j];
                    i++;
                }
                // 从i开始向右寻找第一个大于等于 key 的数
                while (i < j && array[i] < key) {
                    i++;
                }
                if (i < j) {
                    array[j] = array[i];
                    j--;
                }
            }
            array[i] = key;
            quickSort(array, left, i - 1);
            quickSort(array, i + 1, right);
        }
    }

    /**
     * 归并排序
     *
     * @param array 需要排序的数组
     * @return 排好序的数组
     */
    public static int[] mergeSort(int[] array) {
        // 创建额外的空间
        int[] res = Arrays.copyOf(array, array.length);
        if (res.length < 2) {
            return res;
        }
        int mid = array.length / 2;
        int[] left = Arrays.copyOfRange(array, 0, mid);
        int[] right = Arrays.copyOfRange(array, mid, array.length);
        return merge(mergeSort(left), mergeSort(right));
    }

    /**
     * 归并两个有序数组
     *
     * @param array1 有序数组1
     * @param array2 有序数组2
     * @return 归并后的新数组
     */
    private static int[] merge(int[] array1, int[] array2) {
        int[] res = new int[array1.length + array2.length];
        int p = 0;
        int i = 0;
        int j = 0;
        while (i < array1.length && j < array2.length) {
            if (array1[i] <= array2[j]) {
                res[p++] = array1[i++];
            } else {
                res[p++] = array2[j++];
            }
        }
        // 剩下了left
        while (i < array1.length) {
            res[p++] = array1[i++];
        }
        // 剩下的是right
        while (j < array2.length) {
            res[p++] = array2[j++];
        }
        return res;
    }

    /**
     * 插入排序
     *
     * @param array 待排序的数组
     */
    public static void insertSort(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = i + 1; j > 0; j--) {
                if (array[j] < array[j - 1]) {
                    int temp = array[j - 1];
                    array[j - 1] = array[j];
                    array[j] = temp;
                } else {
                    break;
                }
            }
        }
    }

    /**
     * 冒泡排序
     *
     * @param array 待排序的数组
     */
    public static void bubbleSort(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = array.length - 1; j > i; j--) {
                if (array[j] < array[j - 1]) {
                    int temp = array[j];
                    array[j] = array[j - 1];
                    array[j - 1] = temp;
                }
            }
        }
    }

    /**
     * 选择排序
     *
     * @param array 待排序的数组
     */
    public static void selectSort(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[j] < array[minIndex]) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                int temp = array[i];
                array[i] = array[minIndex];
                array[minIndex] = temp;
            }
        }
    }

    /**
     * 堆排序
     *
     * @param array 待排序的数组
     */
    public static void heapSort(int[] array) {
        // len表示的是未进行排序的长度
        int len = array.length;
        for (int i = 0; i < array.length; i++) {
            // 从最后一个非叶子节点开始调整，使其满足大顶堆的性质
            int last = len / 2 - 1;
            for (int j = last; j >= 0; j--) {
                int left = 2 * j + 1;
                int right = left + 1;
                if (array[left] > array[j]) {
                    swap(array, left, j);
                }
                if (right < len && array[right] > array[j]) {
                    swap(array, right, j);
                }
            }
            len--;
            // 将堆顶元素和放到正确的地方
            swap(array, 0, array.length - 1 - i);
        }
    }

    /**
     * 交换数组中的两个元素
     *
     * @param array 数组
     * @param i1    元素1
     * @param i2    元素2
     */
    private static void swap(int[] array, int i1, int i2) {
        int temp = array[i1];
        array[i1] = array[i2];
        array[i2] = temp;
    }

    /**
     * 计数排序
     *
     * @param array 待排序的数组
     */
    public static void countingSort(int[] array) {
        int min = array[0];
        int max = array[0];
        // 找最大值和最小值
        for (int i : array) {
            min = Math.min(i, min);
            max = Math.max(i, max);
        }

        // 申请额外的空间，大小为最值之间的范围
        int[] temp = new int[max - min + 1];

        // 填充新数组
        for (int i : array) {
            temp[i - min]++;
        }

        // 遍历新数组，然后填充原数组
        int index = 0;
        for (int i = 0; i < temp.length; i++) {
            if (temp[i] != 0) {
                Arrays.fill(array, index, index + temp[i], i + min);
                index += temp[i];
            }
        }
    }


}
