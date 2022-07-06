package cn.pbx.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * @author Bruce Xu
 * @date 2022/7/5
 */
public class Main3 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        List<String> list = Arrays.stream(s.split(" ")).collect(Collectors.toList());
        String rule = scanner.nextLine();
        List<Integer> res = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            if (match(list.get(i), rule)) {
                res.add(i);
            }
        }

        if (res.isEmpty()) {
            System.out.println("-1");
            System.exit(0);
        }
        StringBuilder out = new StringBuilder();
        boolean first = true;
        for (Integer i : res) {
            if (first) {
                first = false;
                out.append(i);
            } else {
                out.append(",").append(i);
            }
        }
        System.out.println(out);
    }

    static boolean match(String s, String rule) {
        return rule.equals("*");
    }

}
