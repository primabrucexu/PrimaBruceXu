package cn.pbx.offer.version1;

/**
 * @author BruceXu
 * @date 2022/2/11
 */
public class offer_15 {
    public static void main(String[] args) {
        offer_15 demo = new offer_15();
        int n = 11;
        System.out.println(Integer.toBinaryString(n));
        n = 128;
        System.out.println(Integer.toBinaryString(n));

        n = -3;
        System.out.println(Integer.toBinaryString(n));

        long i = 4294967293L;
        System.out.println((int) i);
        System.out.println(Long.toBinaryString(i));
    }

    public int hammingWeight(int n) {
        String s = Integer.toBinaryString(n);
        int sum = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '1') {
                sum++;
            }
        }
        return sum;
    }
}
