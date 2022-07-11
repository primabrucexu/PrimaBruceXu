package cn.pbx;

import java.util.HashMap;
import java.util.Map;

/**
 * @author BruceXu
 * @date 2022/2/8
 */
public class Main {

    private String s;

    public static void main(String[] args) {
        System.out.println(1);
        Map<String, String> map = new HashMap<>(20);
        map.put("1", "1");
    }

    static class A {
        private String x;

        public A(String x) {
            this.x = x;
        }

        public void t() {
            System.out.println(x);

        }

    }


}
