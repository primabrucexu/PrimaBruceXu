package cn.pbx.week.two.week19;

/**
 * @author Bruce Xu
 * @date 2022/7/4
 */
public class No1343 {
    public static void main(String[] args) {
        No1343 demo = new No1343();
//        int[] array = {2,2,2,2,5,5,5,8};
//        int k = 3;
//        int threshold = 4;

        int[] array = {1, 1, 1, 1, 1};
        int k = 3;
        int threshold = 1;

//        int[] array = {11,13,17,23,29,31,7,5,2,3};
//        int k = 3;
//        int threshold = 5;
        System.out.println(demo.numOfSubarrays(array, k, threshold));
    }

    public int numOfSubarrays(int[] arr, int k, int threshold) {
        int num = 0;
        int total = threshold * k;
        int temp = 0;
        for (int i = 0; i < k; i++) {
            temp += arr[i];
        }

        if (temp >= total) {
            num++;
        }

        for (int i = k; i < arr.length; i++) {
            temp = temp + arr[i] - arr[i - k];
            if (temp >= total) {
                num++;
            }
        }

        return num;
    }

}
