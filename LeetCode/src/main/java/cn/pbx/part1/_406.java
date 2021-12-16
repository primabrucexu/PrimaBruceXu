package cn.pbx.part1;

import java.util.*;

/**
 * @author BruceXu
 * @date 2020/11/5
 */
public class _406 {

    public static void main(String[] args) {
        int[][] array = new int[][]{{6, 0}, {5, 0}, {4, 0}, {3, 2}, {2, 2}, {1, 4}};
        int[][] ints = reconstructQueue2(array);
    }

    public static int[][] reconstructQueue2(int[][] people) {
        Arrays.sort(people, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] == o2[0] ? o1[1] - o2[1] : o2[0] - o1[0];
            }
        });
        List<int[]> res = new LinkedList<>();
        for (int[] person : people) {
            res.add(person[1], person);
        }
        return res.toArray(people);
    }

    public static int[][] reconstructQueue(int[][] people) {
        Arrays.sort(people, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[1] - o2[1] != 0 ? o1[1] - o2[1] : o1[0] - o2[0];
            }
        });
        int maxHeight = 0;
        for (int[] person : people) {
            maxHeight = Math.max(person[0], maxHeight);
            System.out.println(person[0] + "-" + person[1]);
        }
        System.out.println("============================");
        List<int[]> queue = new LinkedList<>();
        for (int i = 0; i < people.length; i++) {
            if (people[i][1] == 0 || people[i][0] == maxHeight) {
                queue.add(people[i]);
                continue;
            }
            int heigher = 0;
            int index = 0;
            // 找到高于当前元素的前K个
            while (heigher < people[i][1]) {
                int[] ints = queue.get(index);
                index++;
                if (ints[0] >= people[i][0]) {
                    heigher++;
                }
            }
            // 找到第K+1个，找不到就加在
            while (index < queue.size()) {
                int[] p = queue.get(index);
                if (p[0] < people[i][0]) {
                    index++;
                } else {
                    break;
                }
            }
            queue.add(index, people[i]);
            for (int[] ints : queue) {
                System.out.println(ints[0] + "-" + ints[1]);
            }
            System.out.println("===================");
        }
        return queue.toArray(people);
    }
}
