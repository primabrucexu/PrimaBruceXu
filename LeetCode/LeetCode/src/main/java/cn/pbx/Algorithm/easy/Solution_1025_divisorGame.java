package cn.pbx.Algorithm.easy;

/**
 * @author BruceXu
 * @date 2020/10/4
 */
public class Solution_1025_divisorGame {
    public static void main(String[] args) {

    }

    /**
     * N = 1时，Alice lose
     * N = 2时，alice拿1，N=1，Alice win
     * N = 3时，Alice拿1，N=2，Bob拿1，alice lose
     * N = 4时，alice拿1的话，N=3，alice win。alice拿2的话，N=2，alice lose
     * N = 5时，alice拿1，N=4，alice lose
     * N = 6时，alice拿1，N=5，alice win；alice拿2的话，N=4，alice lose
     * <p>
     * N为奇数的时候，后手必胜，N为偶数的时候，先手必胜
     */
    public static boolean divisorGame(int N) {
        // true表示Alice，false表示Bob
        return N % 2 == 0;
    }
}
