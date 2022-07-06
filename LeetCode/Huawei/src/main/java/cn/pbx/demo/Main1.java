package cn.pbx.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * @author Bruce Xu
 * @date 2022/7/5
 */
public class Main1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int s = scanner.nextInt();
        int n = scanner.nextInt();
        List<Integer> list = new ArrayList<>(n);

        if (s < 0 || n < 0) {
            System.out.println(-1);
            System.exit(0);
        }

        int avg = s / n;
        int start = avg - (n - 1) / 2;

        if (start < 1) {
            System.out.println(-1);
            System.exit(0);
        }

        for (int i = 0; i < n; i++) {
            list.add(start + i);
        }

        if (avg < 1) {
            System.out.println(-1);
            System.exit(0);
        }

        Collections.sort(list);
        int sum = 0;
        for (Integer i : list) {
            sum += i;
        }

        if (sum != s) {
            System.out.println(-1);
            System.exit(0);
        }

        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (Integer integer : list) {
            if (first) {
                sb.append(integer);
                first = false;
            } else {
                sb.append(" ").append(integer);
            }
        }
        System.out.println(sb);
    }
}
