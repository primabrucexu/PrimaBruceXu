package cn.pbx.part4;

import java.util.*;

/**
 * @author BruceXu
 * @date 2020/11/29
 */
public class _347 {
    public static void main(String[] args) {
        int[] num = new int[]{1, 1, 1, 2, 2, 3};
        int[] ints = topKFrequent2(num, 2);
        System.out.println(Arrays.toString(ints));
    }

    public static int[] topKFrequent(int[] nums, int k) {
        int[] res = new int[k];
        // 用哈希表做桶排序，每个元素作为key，出现次数作为value
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        System.out.println(map);
        int[][] bucket = new int[map.size()][2];
        int p = 0;
        // 利用数组将元素和出现次数进行对应
        for (Integer i : map.keySet()) {
            bucket[p][0] = i;
            bucket[p++][1] = map.get(i);
        }
        // 降序排序
        Arrays.sort(bucket, ((o1, o2) -> o2[1] - o1[1]));
        for (int i = 0; i < k; i++) {
            res[i] = bucket[i][0];
        }
        return res;
    }

    public static int[] topKFrequent2(int[] nums, int k) {

        // 用哈希表做桶排序，每个元素作为key，出现次数作为value
        Map<Integer, Integer> map = new HashMap<>();
        int maxFrequency = 0;
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
            maxFrequency = Math.max(maxFrequency, map.get(num));
        }

        // 再对频率进行一次桶排序，这样就可以得到前K个高频的元素
        // 对于频率最高的元素，放在最前面
        List<Integer>[] bucket = new List[maxFrequency + 1];
        for (int i : map.keySet()) {
            int f = maxFrequency - map.get(i);
            if (bucket[f] == null) {
                bucket[f] = new ArrayList<>();
            }
            bucket[f].add(i);
        }
        List<Integer> res = new ArrayList<>();
        int i = 0;
        while (k > 0) {
            List<Integer> list = bucket[i++];
            if (list != null) {
                res.addAll(list);
                k -= list.size();
            }
        }
        return res.stream().mapToInt(Integer::intValue).toArray();
    }


}
