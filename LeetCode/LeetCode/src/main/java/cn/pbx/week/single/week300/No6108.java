package cn.pbx.week.single.week300;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Bruce Xu
 * @date 2022/7/3
 */
public class No6108 {
    public static void main(String[] args) {
        No6108 demo = new No6108();
        String key = "eljuxhpwnyrdgtqkviszcfmabo";
        String message = "zwx hnfx lqantp mnoeius ycgk vcnjrdb";
//        "eljuxhpwnyrdgtqkviszcfmabo"
//        "zwx hnfx lqantp mnoeius ycgk vcnjrdb"
        System.out.println(demo.decodeMessage(key, message));
    }

    public String decodeMessage(String key, String message) {
        Map<Character, Character> map = buildDecodeTable(key);
        StringBuilder sb = new StringBuilder(message.length());
        for (int i = 0; i < message.length(); i++) {
            sb.append(map.getOrDefault(message.charAt(i), ' '));
        }
        return sb.toString();
    }

    private Map<Character, Character> buildDecodeTable(String key) {
        Map<Character, Character> map = new HashMap<>();
        int offset = 0;
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            if (map.containsKey(c) || c == ' ') {
                continue;
            }
            map.put(c, (char) ('a' + offset++));
        }
        return map;
    }
}
