package cn.pbx.part1;

/**
 * @author BruceXu
 * @date 2020/11/3
 */
public class _605 {

    public static void main(String[] args) {
        int[] p = new int[]{1, 0, 1, 0, 1, 0, 1};
        int[] p1 = new int[]{1, 0, 1};
        boolean b = canPlaceFlowers(p1, 1);
        System.out.println(b);
    }

    public static boolean canPlaceFlowers(int[] flowerbed, int n) {
        if (n == 0) {
            return true;
        }
        int len = flowerbed.length;
        if (len == 1) {
            return flowerbed[0] == 0 && n == 1;
        }

        if (flowerbed[0] == 0 && flowerbed[1] == 0) {
            flowerbed[0] = 1;
            n--;
        }
        if (flowerbed[len - 1] == 0 && flowerbed[len - 2] == 0) {
            flowerbed[len - 1] = 1;
            n--;
        }
        for (int i = 1; i < len - 1; i++) {
            if (flowerbed[i - 1] == 0 && flowerbed[i + 1] == 0 && flowerbed[i] == 0) {
                flowerbed[i] = 1;
                n--;
            }
        }
        return n <= 0;
    }
}
