package cn.pbx.demo;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Bruce Xu
 * @date 2022/7/5
 */
public class Main4 {

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
        if (rule.equals("*")) {
            return true;
        }
        StringBuilder sb = new StringBuilder();
        int pos = -1;
        for (int i = 0; i < rule.length(); i++) {
            char c = rule.charAt(i);
            if (c == '.' || c == '*') {
                pos = i;
                break;
            }
        }

        if (pos == -1) {
            return s.equals(rule);
        }
        String prefix = rule.substring(0, pos);
        if (pos >= s.length()) {
            return prefix.startsWith(s);
        }
        if (!s.startsWith(prefix)) {
            return false;
        }

        Deque<Character> text = new ArrayDeque<>();
        for (int i = pos; i < s.length(); i++) {
            text.addLast(s.charAt(i));
        }
        Deque<Character> ruleDeque = new ArrayDeque<>();
        for (int i = pos; i < rule.length(); i++) {
            ruleDeque.addLast(s.charAt(i));
        }

        return match(text, ruleDeque);
//        return true;
    }

    static boolean match(Deque<Character> text, Deque<Character> rule) {
        if (text.isEmpty()) {
            return true;
        }
        if (rule.isEmpty()) {
            return false;
        }
        Character r = rule.getFirst();
        Character t = text.getFirst();
        if (r.equals('.')) {
            Character c1 = text.removeFirst();
            Character c2 = rule.removeFirst();
            return match(text, rule);
        } else if (r.equals("*")) {
            while (!text.isEmpty()) {
                Character c = text.removeFirst();
                if (match(text, rule)) {
                    return true;
                }
                text.addFirst(c);
            }
        } else {
            return r.equals(t);
        }
        return false;
    }
}
