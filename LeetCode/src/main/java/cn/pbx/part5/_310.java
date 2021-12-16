package cn.pbx.part5;

import java.util.*;

/**
 * @author BruceXu
 * @date 2020-12-20
 */

public class _310 {

    public static void main(String[] args) {
        test1();
    }

    public static void test1() {
        int[][] edges = {
                {0, 1}, {1, 2}, {2, 3}, {0, 4}, {4, 5}, {4, 6}, {6, 7}
        };
        List<Integer> list = findMinHeightTrees(8, edges);
        System.out.println(list);
    }

    public static void test2() {
        int[][] edges = {{1, 0}, {2, 1}, {3, 1}};
        List<Integer> list = findMinHeightTrees(4, edges);
        System.out.println(list);
    }

    public static List<Integer> findMinHeightTrees(int n, int[][] edges) {
        List<Integer> res;
        if (n <= 2) {
            res = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                res.add(i);
            }
            return res;
        }
        // 把给出的边分类做记录
        Map<Integer, List<Integer>> map = new HashMap<>();
        set(map, n, edges);
        System.out.println(map);
        // key：高度，value：根节点序号
        Map<Integer, List<Integer>> hmap = new HashMap<>();
        int minH = n;
        for (int i = 0; i < n; i++) {
            boolean[] flag = new boolean[n];
            int h = bfs(map, i, flag, minH);
            minH = Math.min(minH, h);
            List<Integer> list = hmap.getOrDefault(h, new ArrayList<>());
            list.add(i);
            hmap.put(h, list);
        }
        System.out.println(hmap);
        res = hmap.get(minH);
        return res;
    }

    private static int bfs(Map<Integer, List<Integer>> map, int n, boolean[] flag, int minH) {
        Deque<Integer> queue = new ArrayDeque<>();
        queue.addLast(n);
        int size = queue.size();
        int h = 0;
        flag[n] = true;
        while (size-- > 0 && !queue.isEmpty()) {
            if (h > minH) {
                return flag.length;
            }
            List<Integer> list = map.get(queue.removeFirst());
            for (int i : list) {
                if (!flag[i]) {
                    queue.addLast(i);
                    flag[i] = true;
                }
            }
            if (size == 0) {
                size = queue.size();
                h++;
            }
        }
        return h;
    }

    private static void set(Map<Integer, List<Integer>> map, int n, int[][] edges) {
        for (int[] i : edges) {
            List<Integer> list1 = map.getOrDefault(i[0], new ArrayList<>());
            list1.add(i[1]);
            map.put(i[0], list1);
            list1 = map.getOrDefault(i[1], new ArrayList<>());
            list1.add(i[0]);
            map.put(i[1], list1);
        }
    }
}
