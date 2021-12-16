package cn.pbx.part1;

import java.util.LinkedList;
import java.util.List;

/**
 * @author BruceXu
 * @date 2020/11/6
 */
public class main {
    public static void main(String[] args) {
        List<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        System.out.println(list);

        list.add(3, 0);
        System.out.println(list);
    }
}
