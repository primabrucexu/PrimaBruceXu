package cn.pbx.week.two.week19;

/**
 * @author Bruce Xu
 * @date 2022/7/4
 */
public class No1342 {
    public static void main(String[] args) {
        No1342 demo = new No1342();
        int num = 123;
        System.out.println(demo.numberOfSteps(num));
    }

    public int numberOfSteps(int num) {
        int times = 0;
        while (num != 0) {
            if (num % 2 == 0) {
                num = num >> 1;
            } else {
                num -= 1;
            }
            times++;
        }
        return times;
    }

}
