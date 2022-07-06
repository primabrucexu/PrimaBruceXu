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
public class Main2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        String error = "invalid IP";
        List<String> s = Arrays.stream(input.split("#")).collect(Collectors.toList());
        if (s.size() != 4) {
            System.out.println(error);
            System.exit(0);
        }

        List<Long> ipList = new ArrayList<>();
        for (String str : s) {
            try {
                ipList.add(Long.valueOf(str));
            } catch (Exception e) {
                System.out.println(error);
                System.exit(0);
            }
        }
        if (ipList.size() != 4) {
            System.out.println(error);
            System.exit(0);
        }


        boolean first = true;
        for (int i = 0; i < ipList.size(); i++) {
            if (i == 0) {
                if (ipList.get(i) < 1L || ipList.get(i) > 128L) {
                    System.out.println(error);
                    System.exit(0);
                }
            } else {
                if (ipList.get(i) < 0L || ipList.get(i) > 255L) {
                    System.out.println(error);
                    System.exit(0);
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (Long l : ipList) {
            String str = Long.toBinaryString(l);
            int length = 8 - str.length();
            for (int i = 0; i < length; i++) {
                sb.append("0");
            }
            sb.append(str);
        }

        long x = Long.parseLong(sb.toString(), 2);
        if (x < 16777216L || x > 2164260863L) {
            System.out.println(error);
            System.exit(0);
        }
        System.out.println(x);
    }
}
