# LeetCode-101

## 第一章 贪心算法

### 1.1 简介

- 贪心算法是指，在对问题求解时，总是做出在当前看来是最好的选择。也就是说，**不从整体最优上加以考虑**
  ，算法得到的是在某种意义上的局部最优解。==贪心算法不是对所有问题都能得到整体最优解==，关键是贪心策略的选择。也就是说，不从整体最优上加以考虑，做出的只是在某种意义上的局部最优解。
- 注意
    - ==贪心算法不是对所有问题都能得到整体最优解==

---

### 1.2 分配问题

#### [455. 分发饼干（Easy）](https://leetcode-cn.com/problems/assign-cookies/)

- 分析

    - 每个孩子按照胃口的大小从小到大依次吃饼干，要保证胃口小的孩子先吃到满足胃口的大小最小的饼干

- 代码

  ~~~java
  public int findContentChildren(int[] g, int[] s) {
      Arrays.sort(g);
      Arrays.sort(s);
      int child = 0;
      int cookies = 0;
      while (child<g.length && cookies<s.length) {
          if (g[child] <= s[cookies]) {
              child++;
          }
          cookies++;
      }
      return child;
  }
  ~~~

#### [135. 分发糖果（Hard）](https://leetcode-cn.com/problems/candy/)

- 分析

    - 第一次从左向右遍历，如果右边孩子的评分 > 左边，则右边孩子的糖果=左边的+1
    - 第二次从右向左遍历，如果左边孩子的评分 > 右边，且左边孩子的糖果小于等于右边，则左边的糖果=右边+1
    - 贪心策略：每次遍历过程中，只考虑一遍的评分情况

- 代码

  ~~~java
  public int candy(int[] ratings) {
      int[] array = new int[ratings.length];
      Arrays.fill(array, 1);
      for (int i = 1; i < ratings.length; i++) {
          // 右边大于左边，右边=左边+1
          if (ratings[i] > ratings[i-1]) {
              array[i] = array[i-1] + 1;
          }
      }
      int res = array[ratings.length-1];
      for (int i = ratings.length-2; i >= 0; i--) {
          // 左边大于右边，如果左边<=右边，则左边=右边+1，不然左边不变
          if (ratings[i] > ratings[i+1]) {
              array[i] = array[i] <= array[i+1] ? array[i+1] + 1 : array[i];
          }
          res += array[i];
      }
      return res;
  }
  ~~~

---

### 1.3 区间问题

#### [435. 无重叠区间（Medium）](https://leetcode-cn.com/problems/non-overlapping-intervals/)

- 分析

    - 要想尽可能多的保留区间，那么我们需要将选择区间长度尽可能短的区间，这样，就会留给其他区间更多的空间
    - 因此，采取如下的贪心策略
        - 优先保留结尾小的且不相交的区间
    - 先将区间们按照结尾从小到大进行排序，然后依次比较是否相交。不相交则不用删除，相交则删除


- 代码

  ~~~java
  public static int eraseOverlapIntervals(int[][] intervals) {
      if (intervals.length <= 1)
      return 0;
      // 先排序
      Arrays.sort(intervals, new Comparator<>() {
          @Override
          public int compare(int[] o1, int[] o2) {
               return o1[1] - o2[1];
          }
      });
      for (int[] interval : intervals) {
          System.out.println(interval[0] + "-" + interval[1]);
      }
      int res = 0;
      int[] prev = intervals[0];
      // 如果当前区间和prev区间相交，那么则删除。
      for (int i = 1; i < intervals.length; i++) {
          if (intervals[i][0] < prev[1]) {
              res++;
          } else {
              prev = intervals[i];
          }
      }
      return res;
  }
  ~~~

- 注意

    - 如果只给出0个或1个区间，那么删除0个

---

### 1.4 基础练手

#### [605. 种花问题（Easy）](https://leetcode-cn.com/problems/can-place-flowers/)

- 分析

- 先判断头尾是否满足能种上的条件，然后依次逐个判断能否种上

- 代码

  ```java
  public static boolean canPlaceFlowers(int[] flowerbed, int n) {
      if(n==0) {
          return true;
      }
      int len = flowerbed.length;
      if (len == 1) {
          return flowerbed[0] == 0 && n == 1;
      }
  
      if (flowerbed[0] == 0 && flowerbed[1] == 0) {
          flowerbed[0] = 1;
          n--;
      }
      if (flowerbed[len-1] == 0 && flowerbed[len-2] == 0) {
          flowerbed[len-1] = 1;
          n--;
      }
      for (int i = 1; i < len - 1; i++) {
          if (flowerbed[i-1] == 0 && flowerbed[i+1] == 0 && flowerbed[i] == 0) {
              flowerbed[i] = 1;
              n--;
          }
      }
      return n <= 0;
  }
  ```

- 注意：

    - 因为依次判断3个，所以需要对头尾进行特殊处理
    - 特殊情况下的判断：[0]，n=1

#### [452. 用最少数量的箭引爆气球（Medium）](https://leetcode-cn.com/problems/minimum-number-of-arrows-to-burst-balloons/)

- 分析

    - 先对给出的气球坐标数组排序，按照Xstart升序排序。对于start一样的，按照end升序排序
    - 对于排好序后的数组做如下判断，
        - 前1个是否和当前的相交，如果相交，取交集。下一个继续和交集比较。如果不相交，则说明要多花一根箭去射爆气球

- 代码

  ```java
  public static int findMinArrowShots(int[][] points) {
      if (points.length <= 1) {
          return points.length;
      }
      Arrays.sort(points, new Comparator<int[]>() {
          @Override
          public int compare(int[] o1, int[] o2) {
              if (o1[0] > o2[0])
                  return 1;
              else if (o1[0] < o2[0])
                  return -1;
              else {
                  if (o1[1] > o2[1])
                      return 1;
                  else if (o1[1] < o2[1])
                      return -1;
                  else
                      return 0;
              }
          }
      });
      int[] temp = points[0];
      int res = 1;
      for (int i = 1; i < points.length; i++) {
          // 相交的情况下
          if (temp[1] >= points[i][0]) {
              temp[0] = Math.max(temp[0], points[i][0]);
              temp[1] = Math.min(temp[1], points[i][1]);
          } else {
              temp = points[i];
              res++;
          }
      }
      return res;
  }
  ```

- 注意

    - `-2^31 <= xstart < xend <= 2^31 - 1`
        - 这个条件导致了两个int类型的数直接进行加减运算可能导致溢出，所以需要在进行排序的时候不能简单的返回一个`o1[0]-o2[0]`

---

### 1.5 进阶尝试

#### [406. 根据身高重建队列（Medium）](https://leetcode-cn.com/problems/queue-reconstruction-by-height/)

- 分析

    - 我的思路
        - 先对拿到的数组进行排序，按照K升序排序。若K一样，则按照H升序排序。同时记录下最高的高度
        - 先把K等于0的插入到队列中
        - 对于剩余元素，因为K是递增的，所有从头开始遍历队列，找到高于H的前K+1个元素，记录位置index，在index处插入

- 代码

  ~~~java
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
      }
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
      }
      return queue.toArray(people);
  }
  ~~~

- 问题：耗时太长了，不够简练，时间复杂度为O(n^3)

- 优化改进（来自LeetCode官方题解）

    - 让我们从最简单的情况下思考，当队列中所有人的 `(h,k)` 都是相同的高度 `h`，只有 `k` 不同时，解决方案很简单：每个人在队列的索引 `index = k`。
    - 即使不是所有人都是同一高度，这个策略也是可行的。因为个子矮的人相对于个子高的人是 “看不见” 的，所以可以先安排个子高的人。
    - 总结
        - 将最高的人按照 k 值升序排序，然后将它们放置到输出队列中与 k 值相等的索引位置上。 按降序取下一个高度，同样按 k 值对该身高的人升序排序，然后逐个插入到输出队列中与 k 值相等的索引位置上。 直到完成为止。

    - 简直太强了好吗

- 新代码

  ~~~java
  public int[][] reconstructQueue(int[][] people) {
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
  ~~~

#### [665. 非递减数列（Easy）](https://leetcode-cn.com/problems/non-decreasing-array/)

- 分析

    - 要将递减的部分修改成非递减的，一共有两种修改方法（此时 `nums[i-1] > nums[i]`），拉高`nums[i]`或拉低 `nums[i-1]`
        - 采用拉低的情况：`nums[i]>=nums[i-2]`
            - 对于`nums[i]>=nums[i-2]`这种情况时，既可以采取拉高也可以从采取拉低，但是采取拉低能避免 `nums[i]<nums[i+1]<nums[i-1]`拉高无法修复的问题
        - 采用拉高的情况：`nums[i]<nums[i-2]`

- 代码

  ~~~java
  public boolean checkPossibility(int[] nums) {
      int times = 0;
      for (int i = 1; i < nums.length; i++) {
          if (nums[i-1] > nums[i]) {
              times++;
              if (i == 1 || nums[i-2] <= nums[i]) {
                  nums[i-1] = nums[i];
              } else {
                  nums[i] = nums[i-1];
              }
          }
  
      }
      return times <= 1;
  }
  ~~~

---

## 第二章 双指针

### 2.1 介绍

- 算法思想
    - **双指针主要用于遍历数组**，两个指针指向不同的元素，从而协同完成任务。也可以延伸到多个数组的多个指针。
    - 若两个指针指向同一数组，遍历方向相同且不会相交，则也称为**滑动窗口**（两个指针包围的区域即为当前的窗口），经常用于**区间搜索。**
    - 若两个指针指向同一数组，但是遍历方向相反，则可以用来进行**搜索**，待搜索的数组往往是排好序的。

---

### 2.2 Two Sum问题

#### [167. 两数之和 II - 输入有序数组（简单）](https://leetcode-cn.com/problems/two-sum-ii-input-array-is-sorted/)

- 分析

    - 由于给出的数组是有序的，我们可以采取类似二分查找的思想进行解题
        - 一头一尾两个指针。判断和和target的关系。如果比target大，则尾指针往前走，不然头指针往后走

- 代码

  ```java
  public int[] twoSum(int[] numbers, int target) {
      int start = 0;
      int end = numbers.length-1;
      while (start < end) {
          if (numbers[start] + numbers[end] < target) {
              start++;
          } else if (numbers[start] + numbers[end] > target) {
              end--;
          } else {
              return new int[]{start + 1, end + 1};
          }
      }
      return null;
  }
  ```


- 注意：题目要求返回的下标是从1开始的

---

### 2.3 归并两个有序数组

#### [88. 合并两个有序数组（简单）](https://leetcode-cn.com/problems/merge-sorted-array/)

- 思路

    - 我的想法
    - 由于这两个数组是排好序的，所以只需要两个指针同时从头开始，谁小把动谁，同时放置元素。
    - 由于这个题目中使用nums1来保存合并之后的数据，所以需要将其中的元素先保存起来
    - 时间 ***O(m+n)*** ，空间 ***O(m)***

- 代码

  ~~~java
  public void merge(int[] nums1, int m, int[] nums2, int n) {
      int[] backup = new int[m];
      System.arraycopy(nums1, 0, backup, 0, m);
      int p1 = 0;
      int p2 = 0;
      int i = 0;
      while (p1 < m && p2 < n) {
          if (backup[p1] <= nums2[p2]) {
              nums1[i] = backup[p1];
              p1++;
          } else {
              nums1[i] = nums2[p2];
              p2++;
          }
          i++;
      }
      // 假设其中一个已经完成复制，剩下的数组中还有一些数据。
      // nums1中已经有了p1+p2个数据，一共要复制m+n个数据，则剩下的数据量为m+n-p1-p2
      if (p1 < m) {
          System.arraycopy(backup, p1, nums1, p1+p2, m+n-p1-p2);
      }
      if (p2 < n) {
          System.arraycopy(nums2, p2, nums1, p1+p2, m+n-p1-p2);
      }
  }
  ~~~


- 优化空间为 ***O(1)*** 的新思路，来自LeetCode官方

    - 双指针，因为nums1从m-1开始到最后都是没有数据的，所以可以采取如下思路
        - p1指向nums1的“尾巴”，这个尾巴只的是下标为m-1的位置
        - p2指向nums2的尾巴
        - p指向的是nums1的最后一个位置
        - 谁大谁复制，然后往前走

- 代码

  ~~~java
  public void merge(int[] nums1, int m, int[] nums2, int n) {
      int p1 = m - 1;
      int p2 = n - 1;
      int p = m + n - 1;
      while (p1 >= 0 && p2 >= 0) {
          if (nums1[p1] > nums2[p2]) {
              nums1[p] = nums1[p1];
              p1--;
          } else {
              nums1[p] = nums2[p2];
              p2--;
          }
          p--;
      }
      // 注意点：循环结束之后是什么情况？
      if (p2 >= 0) {
          System.arraycopy(nums2, 0, nums1, 0,p2 + 1);
      }
  }
  ~~~


- 注意点：==循环结束之后是什么情况？==

    - nums1用完了，剩下nums2，剩余元素p2+1个，那么复制过去即可
    - nums2用完了，剩下nums1，剩下元素p1+1个。因为我们用nums1作为新容器，所以不用进行操作

---

### 2.4 快慢指针

- **KEY**
    - ==利用快慢指针解题的关键是使用fast和slow的路程差==

#### [142. 环形链表 II（中等）](https://leetcode-cn.com/problems/linked-list-cycle-ii/)

- 分析

    - 判断有没有环

        - fast一次走2步，slow一次走1步，如果有环，那么在若干次移动之后fast肯定能追上slow。这一点不难理解，如果有环的，那么fast一定能无限走下去，在加之fast走的比slow快，那么fast肯定能追上slow

    - 有没有环的问题解决了，那么问题来了，**怎么找到环的入口**

    - 找到环的入口

        - 借用官方的一张图

          ![fig1](https://gitee.com/primabrucexu/image/raw/main/pic/2021/09/20210926112042.png)

        - 先给出fast走过的长度：`a+n(b+c)+b`，slow走过的长度:`a+b`

        - 由于fast的步长是slow的两倍，所以 `a+n(b+c)+b=2(a+b)`，化简整理之后得：`a=(n-1)(b+c)+c`

        - 这就简单了，slow再走c个长度之后就能到环的入口。也就是说从头开始走c个长度之后也能到环的入口，这样也就可以找到环的入口了

- 代码

  ~~~java
  public static ListNode detectCycle(ListNode head) {
      if (head == null || head.next == null) {
      return null;
      }
      ListNode fast = head.next.next;
      ListNode slow = head.next;
      while (fast != slow && fast != null && fast.next != null) {
          fast = fast.next.next;
          slow = slow.next;
      }
      if (fast == slow) {
          fast = head;
          while (fast != slow) {
              fast = fast.next;
              slow = slow.next;
          }
          return fast;
      }
      return null;
  }
  ~~~

---

### 2.5 滑动窗口

- **KEY**
    - ==滑动窗口类型的问题中都会有两个指针。一个用于「延伸」现有窗口的 `right` 指针，和一个用于「收缩」窗口的 `left` 指针。在任意时刻，只有一个指针运动，而另一个保持静止。==

#### [3. 无重复字符的最长子串（中等）](https://leetcode-cn.com/problems/longest-substring-without-repeating-characters/)

- 分析

    - 显而易见的是，如果我们遍历字符串。从`i`开始，找无重复字符的子串。假设结束位置为 `t`
    - 那么，当我们滑动窗口的时候，从 `i+1`到 `t`的这个子串一定是无重复字符的子串。
    - 所以，当我们不停的移动 `i`，遍历整个字符串，即可找到最长的，无重复子串

- 代码

  ~~~java
  public int lengthOfLongestSubstring(String s) {
      Set<Character> set = new HashSet<>();
      int len = 0;
      int right = 0;
      for (int i = 0; i < s.length(); i++) {
          if (i != 0) {
              set.remove(s.charAt(i));
          }
          while (right < s.length() && !set.contains(s.charAt(right))) {
              set.add(s.charAt(right));
              right++;
          }
          // 子串长度为 right - 1 - i + 1 = right - i
          len = Math.max(len, right - i);
      }
      return len;
  }
  ~~~


- 注意

    - right变量存在的位置实际上是与当前子串出现重复的后面的第一个字符的位置，所以，子串的实际范围是从`i`到`right-1`，所以子串的长度即为 `right-i`，即`set`的`size()`

#### [☆ 76. 最小覆盖子串（困难）](https://leetcode-cn.com/problems/minimum-window-substring/)

- 分析

    - 两个指针 `left` 和 `right`组成的窗口中，一定要包含`T`中的所有字符。
        - 如果不包含，则扩大窗口，即 `right`往右移；如果包含，则缩小窗口，即 `left`往右移动。
    - 同时维护两个变量。一个用于记录长度进行比较，一个用来记录窗口中的字符串
    - 怎么判断窗口中是否都包含了 `T`中的所有字符呢？
        - 使用一个map来记录 `T`中的字符和数量**（为什么要记录数量？因为 `T`中可能存在重复的字符）**

- 代码

  ~~~java
  public String minWindow(String s, String t) {
      int length = 1000000;
      Map<Character, Integer> tmap = new HashMap<>();
      Map<Character, Integer> smap = new HashMap<>();
  
      for (int i = 0; i < t.length(); i++) {
          char c = t.charAt(i);
          if (!tmap.containsKey(t.charAt(i))) {
              tmap.put(c, 1);
              smap.put(c, 0);
          } else {
              tmap.replace(c, tmap.get(c) + 1);
          }
      }
      String res = "";
      int left = 0, right = -1;
      while (right < s.length()) {
          right++;
          if (right == s.length()) {
              break;
          }
          char cr = s.charAt(right);
          if (smap.containsKey(cr)) {
              smap.replace(cr, smap.get(cr) + 1);
          }
          boolean flag = check(tmap, smap);
          while (flag && left <= right) {
              char cl = s.charAt(left);
              if (right - left + 1 < length) {
                  length = right - left + 1;
                  res = s.substring(left, right+1);
              }
              if (smap.containsKey(cl)) {
                  smap.replace(cl, smap.get(cl) - 1);
              }
              // 如果我们缩小窗口时，删除的不是t中的字符，那么再次check肯定没问题，不然就要重新check了
              flag = tmap.containsKey(cl) ? check(tmap, smap) : true;
              left++;
          }
      }
      return res;
  }
  
  public boolean check(Map<Character, Integer> tmap, Map<Character, Integer> wmap) {
      for (Character character : tmap.keySet()) {
          if (!wmap.containsKey(character) || wmap.get(character) < tmap.get(character)) {
              return false;
          }
      }
      return true;
  }
  ~~~


- 注意：

    - 当 `left` 在 `p1` 处，`right` 在 `p2` 处时。窗口代表的子串为从 `[p1,p2]`的子串（包含p2）。所以，对于的取子串的操作时，需要`right+1`，因为Java中的取子串是前闭后开的选取

- 优化点（挖坑）

    - 我给出的代码没有进行优化，根据官方题解给出的思路，还有优化空间

    - `s` 中包含了许多 `t` 中未出现的字符，那么我们能否先处理一下 `s`，只关心那些 `t` 中的字符，忽略多余的字符，然后再进行滑动窗口

      ….

---

### 2.6 基础练习

#### [367. 有效的完全平方数（简单）](https://leetcode-cn.com/problems/valid-perfect-square/)

- 思路

    - 采用二分查找法搜索平方根（来自LeetCode官方）
    - 左边界为2，右边界为num/2
        - 如果 `left < right` ，`mid = (left + right) /2` 判断mid*mid和target的关系
        - 如果大于，则 `right = mid - 1`；如果小于 `left = mid + 1`
    - 注意：int类型做平方运算，极有可能溢出，所以要使用long防止溢出

- 代码

  ~~~java
  public boolean isPerfectSquare(int num) {
      if (num == 1) {
      return true;
      }
      long left = 2;
      long right = num / 2;
      while (left <= right) {
          long mid = left + (right - left) / 2;
          long t = mid * mid;
          if (t > num) {
              right = mid - 1;
          } else if (t < num) {
              left = mid + 1;
          } else {
              return true;
          }
      }
      return false;
  }
  ~~~

#### [633. 平方数之和（中等）](https://leetcode-cn.com/problems/sum-of-square-numbers/)

- 思路

    - 找到最接近这个数的平方数，然后从这个记为n。从n开始，二分查找两个符合条件的平方数

- 代码

  ~~~java
  public boolean judgeSquareSum(int c) {
      if (c <= 5) {
      return c != 3;
      }
      long mid = 0;
      long left = 2;
      long right = c / 2;
      while (left <= right) {
          mid = left + (right - left) / 2;
          long t = mid * mid;
          if (t > c) {
              right = mid - 1;
          } else if (t < c) {
              left = mid + 1;
          } else {
              break;
          }
      }
  
      left = 0;
      right = mid;
      while (left <= right) {
          long sum = left*left + right*right;
          mid = left + left + (right - left) / 2;
          if (sum < c) {
              left++;
          } else if (sum > c) {
              right--;
          } else {
              return true;
          }
      }
      return false;
  }
  ~~~

- 优化

    - 如果使用内置求平方根函数的话可以大大提高时间效率

#### [680. 验证回文字符串 Ⅱ（简单）](https://leetcode-cn.com/problems/valid-palindrome-ii/)

- 思路

    - 回文串的判断是经典可以使用二分查找思想进行解题的问题
    - 一头一尾开始进行判断。如果相等，则继续下一个位置。
    - 如果不相等，则有两种情况：删除left所在的字符或者删除right所在的字符
        - 不论删除哪里所在的字符，重新判断的时候，因为 `[0,left)`和 `(right, s.length()-1]`在内的字符都已经判断过了，剩余 `[left, right]`区间内的还未进行判断。
        - 所以只需要判断 `(left, right]` 或`[left, right)` 中的字符即可
    - 时间复杂度 ***O(n)***

- 代码

  ~~~java
  public static boolean validPalindrome(String s) {
      if (s.length() <= 2) {
      return true;
      }
      int left = 0;
      int right = s.length() - 1;
      while (left <= right) {
          if (s.charAt(left) == s.charAt(right)) {
              left++;
              right--;
          } else {
              // 删除左边
              boolean flag1 = true;
              boolean flag2 = true;
              int newLeft = left + 1;
              int newRight = right;
              while (newLeft <= newRight) {
                  if (s.charAt(newLeft) == s.charAt(newRight)) {
                      newLeft++;
                      newRight--;
                  } else {
                      flag1 = false;
                      break;
                  }
              }
              // 删除右边
              newLeft = left;
              newRight = right - 1;
              while (newLeft <= newRight) {
                  if (s.charAt(newLeft) == s.charAt(newRight)) {
                      newLeft++;
                      newRight--;
                  } else {
                      flag2 = false;
                      break;
                  }
              }
              // 两边只要有一个能满足条件，就可以返回true
              return flag1 || flag2;
          }
      }
      return true;
  }
  ~~~

- 注意：

    - 如果删除左边的不能是回文，那么还需要判断删除右边的是不是回文。如果有一个是的话那么还是满足条件的

#### [524. 通过删除字母匹配到字典里最长单词（中等）](https://leetcode-cn.com/problems/longest-word-in-dictionary-through-deleting/)

- 思路

    - 如何判断一个字符串是否可由给出字符串中删除某些字符得出？
        - 即判断d中的字符串是否为s的子序列。
    - 如何快速的判断一个字符串是否为某个字符串的子序列？
        - 模仿两个有序数组归并排序，时间复杂度为 *O(n)* ，n为待判断的字符串长度
    - 时间复杂度 ***O(MN)*** ，其中，M为字典d中的字符个数。N为字典中最长的字符长度

- 代码

  ~~~JAVA
  public String findLongestWord(String s, List<String> d) {
      String res = "";
  // 依次从字典d中取出字符串进行匹配比较
      for (String t : d) {
          int i = 0;
          int j = 0;
          while (i < t.length() && j < s.length()) {
              if (t.charAt(i) == s.charAt(j)) {
                  i++;
                  j++;
              } else {
                  j++;
              }
          }
          // 如果t符合条件退出时，i = t.length(), j <= s.length()
          if (i == t.length() && j <= s.length() ) {
              if (t.length() > res.length()) {
                  // 当t的长度大于res的长度时，可以直接赋值
                  res = t;
              } else if (t.length() == res.length()) {
                  // 当t的长度==res的长度时，需要进行字典序排序 
                  String[] array = {res, t};
                  Arrays.sort(array);
                  res = array[0];
              }
          }
       }
      return res;
  }
  ~~~

- 注意

    - 返回结果是**字典序**中序号较小的，所以在给出答案时，要进行字典序判定

---

### 2.7 进阶练习

#### [☆ 340. 至多包含 K 个不同字符的最长子串（困难）](https://leetcode-cn.com/problems/longest-substring-with-at-most-k-distinct-characters/)

- 思路

    - 使用HashMap记录子串中的字符及其个数，因为子串中一个字符可以重复出现多次
    - 滑动窗口，start = end = 0
        - 当Map中的字符种类个数小于等于K时，扩大窗口；大于K时缩小窗口
    - 符合条件的子串为 `[start, end]`

- 代码

  ~~~java
  public static int lengthOfLongestSubstringKDistinct(String s, int k) {
      if (k == 0) {
      return 0;
      }
      if (k >= s.length()) {
          return s.length();
      }
  
      Map<Character, Integer> map = new HashMap<>();
      int maxLen = 0;
      int start = 0;
      int end = 0;
      while (end < s.length()) {
          char c = s.charAt(end);
          // 尝试将c放入map中
          map.put(c, end);
  
          if (map.size() <= k) {
              // c放入之后满足条件，继续扩大窗口
              end++;
          } else {
              maxLen = Math.max(end - start, maxLen);
              // c放入之后不满足条件了，需要缩小窗口
              while (start <= end) {
                  char t = s.charAt(start);
                  if (map.get(t) == start) {
                      map.remove(t);
                      break;
                  }
                  start++;
              }
              maxLen = Math.max(end - start, maxLen);
              start++;
          }
      }
      if (map.size() <= k) {
          maxLen = Math.max(end - start, maxLen);
      }
      return maxLen;
  }
  ~~~

- 注意一些特殊情况的处理

    - s = "a"，k = 0，answer = 0
    - s = "a"，k >= 1，answer = 1
    - s = ”aa“, k = 1，answer = 2

---

## 第三讲 二分查找

### 3.1 介绍

- 二分查找也叫折半查找，顾名思义，就是查找的时候只查找其中的一半，这样的话大大降低了查找的时间复杂度，为 *O(log n)* ，其中n为数组的长度
- 二分查找也可以看作双指针的一种特殊情况，但我们一般会将二者区分。
    - 双指针类型的题，指针通常是一步一步移动的；
    - 二分查找时，指针每次移动半个区间长度。
- 注意：
    - **对于端点上的处理**（如果对端点的处理不够细心，有可能会导致无法得出正确答案或者无法退出循环等问题）
        - 端点的处理方式：左闭右开（符合大多数语言的习惯），左闭右闭（方便处理边界条件）
        - 首先，养成固定使用一个方式处理的习惯
        - 其次，==思考当区间中有一个或两个或没有元素时，是否能正确执行并退出循环==
    - **二分查找中关于mid的选取**
        - mid良好的赋值方式应为 `mid = low + ((high - low) / 2)` ，而不是 `mid = (low + high) / 2`，这是因为当数组较大时，可能会出现溢出的情况
        - 对mid向上还是向下取整要根据实际情况来判断
    - ==**while(left <= right) 和 while(left < right) 的区别**==
        - **while(left <= right)** 表示在区间中还剩下一个元素的时候，我们还要在进行一次循环
            - 通常和 `right = mid -1` ， `left = mid + 1` 配合使用
        - **while(left < right)** 表示在区间中还剩下一个元素的时候，停止循环
            - 通常和 `right = mid` ，`left = mid + 1` 或 `right = mid - 1` ，`left = mid `配合使用
- **来自 LeetCode @liweiwei1419 对于二分搜索编码的建议**
    - 循环终止条件写成：while (left < right) ，表示退出循环的时候只剩下一个元素；
    - 在循环体内考虑如何缩减待搜索区间，也可以认为是在待搜索区间里排除一定不存在目标元素的区间；
    - 根据中间数被分到左边和右边区间，来调整取中间数的行为；
    - 如何缩小待搜索区间
        - 从 nums[mid] 满足什么条件的时候一定不是目标元素去考虑，进而考虑 mid 的左边元素和右边元素哪一边可能存在目标元素。
        - 一个结论是：当看到 left = mid 的时候，取中间数需要上取整，这一点是为了避免死循环；
    - 退出循环的时候，根据题意看是否需要单独判断最后剩下的那个数是不是目标元素。
    - 边界设置的两种写法：
        - `right = mid` 和 `left = mid + 1` 和 `mid = left + (right - left) / 2` 一定是配对出现的
        - `right = mid - 1` 和 `left = mid` 和 `mid = left + (right - left + 1) / 2` 一定是配对出现的
- 使用二分查找的要点
    - ==找到某些规则或规律使得指针可以一次移动一半的区间==

---

### 3.2 平方根

#### [69. x 的平方根（简单）](https://leetcode-cn.com/problems/sqrtx/)

- 思路

    - 同2.6练习中的367

- 代码

  ~~~java
  public int mySqrt(int x) {
      if (x <= 1) {
          return x;
      }
      if (x < 4) {
          return  1;
      }
      int left = 2;
      int right = x / 2;
      int mid = 0;
      while (left <= right) {
          mid = left + (right - left) / 2;
          long temp = (long)mid * (long)mid;
          if (temp < x) {
              left = mid + 1;
          } else if (temp > x) {
              right = mid - 1;
          } else {
              return mid;
          }
      }
      return (long)mid * (long)mid > x ? mid - 1 : mid;
  }
  ~~~

- 注意：

    - `int` 除 `int` 会导致直接向下取整，这里导致了精度上的损失。所以，最后要多判断一下 `mid - 1` 的平方
    - `int` 乘 `int` 会先得出`int`类型的结果，然后将这个结果转换成`long`，这回导致溢出，所以在乘之前要先转成`long`类型或者统一使用 `long` 最后在返回的时候转成 `int`

---

### 3.3 查找区间

#### [34. 在排序数组中查找元素的第一个和最后一个位置（中等）](https://leetcode-cn.com/problems/find-first-and-last-position-of-element-in-sorted-array/)

- 分析

    - 使用二分查找找一个特定的数字并不难实现自然而然的，可以想到，我们先找到这个target，然后从target的位置开始试探，通过试探可以找出第一次出现的位置和最后一个出现的位置。
        - 理想情况下，只需要几次试探即可找到，这样时间复杂度为 *O(log n)*
        - 但是最坏的情况，当一个数组中全部都是target时，那么这样时间复杂度就会升级成为 *O(n)* ，不符合题目要求
    - 那么能不能通过调整二分查找的一些策略，找到我们需要的位置呢？
        - 以找第一次出现的位置举例
        - 找到target之后，不着急停止循环，而是将right定位在这个位置。然后继续向前查找，直到找不到位置。那么最后一次right的位置就是它的起始位置了

- 代码

  ```java
  public int[] searchRange(int[] nums, int target) {
      if (nums == null || nums.length == 0) {
          return new int[]{-1, -1};
      }
      if (nums.length <= 1) {
          return nums[0] == target ? new int[]{0, 0} : new int[] {-1, -1};
      }
  
      int[] res = new int[]{-1, -1};
      int left = 0;
      int right = nums.length - 1;
  
      // 找到第一次出现的位置
      while (left < right) {
          int mid = left + (right - left) / 2;
          if (nums[mid] >= target) {
              right = mid;
          } else {
              left = mid + 1;
          }
      }
      res[0] = nums[right] != target ? -1 : right;
      if (res[0] == -1) {
          return new int[] {-1, -1};
      }
  
      // 找最后一次出现的位置
      left = right;
      right = nums.length - 1;
      while (left < right) {
          int mid = left + (right - left) / 2;
          if (nums[mid] > target) {
              right = mid;
          } else {
              left = mid + 1;
          }
      }
      res[1] = nums[left] == target ? left : left - 1;
  
      return res;
  }
  ```

- 注意：

    - 两次二分查找时对于左右边界上的处理，如果处理不当，会导致死循环的错误

---

### 3.4 旋转数组查找数字

#### [81. 搜索旋转排序数组 II（中等）](https://leetcode-cn.com/problems/search-in-rotated-sorted-array-ii/)

- 分析

    - 仔细观察旋转后的数组会发现，旋转过的数组分为两部分，这两部分都是有序的，既然是有序的，那么就可以使用二分查找进行搜索
    - 记数组下标为 `0,1,...,i,i+1,...,n-1`，其中 `nums[i+1] < nums[i]`
    - 那么我们只需要对 `[0,i]` 和 `[i+1, n-1]` 这两块分别进行二分搜索即可，那么只需要找到 `i` 即可
        - 要找到 `i` 必然要对数组进行遍历，并且因为数组中存在重复的元素，所以必须要遍历整个数组，才能确定出 `i` 。因此，这一部分的时间复杂度为 *O(n)*
    - 找到 `i` 时间复杂度为 ***O(n)*** ，二分查找时间复杂度为 ***O(log n)***，总的时间复杂度为 ***O(n)***

- 代码

  ```java
  public boolean search(int[] nums, int target) {
      if (nums.length == 0) {
          return false;
      }
      if (nums.length == 1) {
          return nums[0] == target;
      }
      int flag = 0;
      for (int i = 0; i < nums.length - 1; i++) {
          if (nums[i] > nums[i + 1]) {
              flag = i;
          }
      }
      
      return binarySearch(0, flag, nums, target) || binarySearch(flag + 1, nums.length - 1, nums, target);
  }
  
  public boolean binarySearch(int left, int right, int[] nums,int target) {
      while (left <= right) {
          int mid = left + (right - left) / 2;
          if (nums[mid] > target) {
              right = mid - 1;
          } else if (nums[mid] < target) {
              left = mid + 1;
          } else {
              return true;
          }
      }
      return false;
  }
  ```

- 改进（来自LeetCode官方题解）

    - 要使用二分搜索，那么就要求被搜索的区间是有序的，而由题目给出的数组是局部有序的，那么我们能不能只在有序的部分进行搜索呢，忽略无序部分？
    - 这启示我们可以在常规二分搜索的时候查看当前 mid 为分割位置分割出来的两个部分 `[left, mid]` 和 `[mid + 1, right]`
      哪个部分是有序的，并根据有序的那个部分确定我们该如何改变二分搜索的上下界，因为我们能够根据有序的那部分判断出 `target` 在不在这个部分：
        - 如果 `[left, mid]`是有序数组，且 `target` 的大小满足 `[nums[left],nums[mid])`，则我们应该将搜索范围缩小至 `[left, mid-1]`
          ，否则在 `[mid + 1, right]` 中寻找。
        - 如果 `[mid, right]` 是有序数组，且 target 的大小满足 `(nums[mid+1],nums[right]]`，则我们应该将搜索范围缩小至 `[mid + 1, right]`
          ，否则在 `[left, mid - 1]` 中寻找。
    - 时间复杂度 *O(log n)*

- 实现

  ~~~java
  public boolean search(int[] nums, int target) {
      if (nums.length == 0) {
          return false;
      }
      if (nums.length == 1) {
          return nums[0] == target;
      }
      int left = 0;
      int right = nums.length - 1;
      while (left <= right) {
          int mid = left + (right - left) / 2;
          if (nums[mid] == target) {
              return true;
          }
          if (nums[left] == nums[mid]) {
              left++;
          } else if (nums[mid] <= nums[right]) {
              // 右边是有序的
              if (target > nums[mid] && target <= nums[right]) {
                  // target落在有序区间内
                  left = mid + 1;
              } else {
                  right = mid - 1;
              }
          } else {
              // 左边是有序的
              if (target >= nums[left] && target < nums[mid]) {
                  // target落在有序区间内
                  right = mid - 1;
              } else {
                  left = mid + 1;
              }
          }
      }
  
      return false;
  }
  ~~~

- ==要点：如何判断哪边有序？==

    - 因为数组存在**重复数字**，所以，当 `nums[mid] == nums[left]` 时，我们无法确定哪边是递增的，这时需要将 `left` 左移一个位置

---

### 3.5 基础练习

#### [154. 寻找旋转排序数组中的最小值 II（困难）](https://leetcode-cn.com/problems/find-minimum-in-rotated-sorted-array-ii/)

- 分析

    - 旋转数组在旋转前，我们可以表示为 `[小数区间][大数区间]` ，那么旋转之后，就是`[大数区间][小数区间]` 。我们要找的这个最小值，就是大数区间变成小数区间的转折点
        - **哪怕数组中有重复元素也不例外，这个转折点必然是数组的最小值**（想一想，为什么？）
    - 记这个元素下标为 `i`
        - `[0, i-1]` 中的所有元素全部大于等于该元素
        - `i` 小于等于 `[i+1, ... ,n-1]` 中的所有元素
    - 参考3.4，制定策略
        - 如果 `nums[mid] < nums[right]` ，那么转折点在 `[left, mid-1]`范围内
        - 如果 `nums[mid] == nums[right]` ，无法确定转折点所在的区间，`right`左移一位
        - 如果 `nums[mid] > nums[right]` ，那么转折点在 `[mid + 1, right]`范围内
    - 为什么要使用 `right` 进行判断而不是使用 `left` ？
        - 如果整个数组都是有序的，使用 `left` 无法正确得出结果。（经过多次尝试后发现，百思不得其解，参考了一下官方解法才发现关键在这里）

- 代码

  ```java
  public int findMin(int[] nums) {
      if (nums.length <= 2) {
          return nums.length == 1 ? 0 : Math.min(nums[0], nums[1]);
      }
      int left = 0;
      int right = nums.length - 1;
      int mid = 0;
      while (left < right) {
          mid = left + (right - left) / 2;
          if (nums[mid] < nums[right]) {
              right = mid;
          } else if (nums[right] == nums[mid]) {
              right--;
          } else {
              left = mid + 1;
          }
      }
      return nums[left];
  }
  ```

- 注意

    - 根据分析，当二分查找结束后，剩下的那个元素就是我们要找的中间点，于是我们采用 `left < right` 作为循环结束的条件

#### [540. 有序数组中的单一元素（中等）](https://leetcode-cn.com/problems/single-element-in-a-sorted-array/)

- 分析

    - 考虑单一元素出现前后，数组奇数和偶数下标对应的元素发生的变化
        - 在单一元素出现之前，第 `i` 对元素的下标是 `2i-2` 和 `2i-1`
        - 在单一元素出现之后，第 `i` 对元素的下标是 `2i-1` 和 `2i`
    - 也就是说，
        - 在单一元素出现之前，对于奇数下标的元素，和它一样的元素在它的前面
        - 在单一元素出现之后，对于奇数下标的元素，和它一样的元素在它的后面
    - 根据这样的规律，即可推断出单一元素是在 `mid` 的 左边还是右边
        - 如果 `nums{mid]` 和前后元素都不相同，则 `nums{mid]` 就是单一元素
        - 如果 `mid` 是奇数，且 `nums{mid]` 和后面元素相同，单一元素在 `[left, mid-1]` 范围内，不然就在 `[mid+1, right]` 范围内
        - 如果 `mid` 是偶数，且 `nums{mid]` 和后面元素相同，单一元素在 `[mid+1, right]`范围内，不然就在  `[left, mid-1]` 范围内

- 代码

  ~~~java
  public int singleNonDuplicate(int[] nums) {
      if (nums.length == 0) {
          return 0;
      }
      if (nums.length == 1 || nums[0] != nums[1]) {
          return nums[0];
      }
      if (nums[nums.length - 1] != nums[nums.length -2]) {
          return nums[nums.length - 1];
      }
      int left = 0;
      int right = nums.length - 1;
      while (left <= right) {
          int mid = left + (right - left) / 2;
          if (nums[mid] != nums[mid-1] && nums[mid] != nums[mid+1]) {
              return nums[mid];
          }
          if (mid % 2 != 0) {
              // mid是奇数
              if (nums[mid] == nums[mid + 1]) {
                  right = mid - 1;
              } else {
                  left = mid + 1;
              }
          } else {
              // mid是偶数
              if (nums[mid] == nums[mid + 1]) {
                  left = mid + 1;
              } else {
                  right = mid - 1;
              }
          }
      }
      return 0;
  }
  ~~~

- 注意

    - 因为涉及到了取 `mid-1`  和 `mid+1` 的操作，所以要对首尾的元素进行特殊操作，避免数组越界

---

### 3.6 进阶练习

#### [4. 寻找两个正序数组的中位数（困难）](https://leetcode-cn.com/problems/median-of-two-sorted-arrays/)

- 分析

    - 如果对时间复杂度没有要求
        - 那么将两个数组合并之后再查找中位数即可，这样的话时间复杂度为 *O(m+n)* 。
        - 那么可不可以不合并数组来进行查找呢？
            - 双指针，谁小移谁，一共移动 `(m+n)/2 `次后即可得到中位数，不过这样并没有优化时间复杂度，只是把空间复杂度从 *O(m+n)* 降低到了 *O(1)*
    - 如果对时间复杂度的要求有 log，通常都需要用到二分查找，这里我是没想出来有什么好方法，后来看了官方题解后恍然大悟
        - 根据中位数的定义，当 `m+n` 是奇数时，中位数是两个有序数组中的第 `(m+n)/2` 个元素，当 `m+n` 是偶数时，中位数是两个有序数组中的第 `(m+n)/2` 个元素和第 `(m+n)/2+1`
          个元素的平均值。因此，这道题可以转化成寻找两个有序数组中的第 `k` 小的数，其中 k 为 `(m+n)/2` 或 `(m+n)/2+1`
        - 假设两个有序数组分别是 A 和 B。要找到第 `k` 个元素，我们可以比较 `A[k/2−1]` 和 `B[k/2−1]`。由于 `A[k/2−1]` 和 `B[k/2−1]` 的前面分别有 `A[0..k/2−2]`
          和 `B[0..k/2−2]`，即 `k/2-1` 个元素，对于 `A[k/2−1]` 和 `B[k/2−1]` 中的较小值，最多只会有 `(k/2−1)+(k/2−1)≤k−2` 个元素比它小，那么它就不能是第 `k`
          小的数了。
        - 因此我们可以归纳出三种情况：
            - 如果 `A[k/2−1]<B[k/2−1]` ，则比 `A[k/2−1]` 小的数最多只有 `A` 的前 `k/2-1` 个数和 `B` 的前 `k/2−1` 个数，即比 `A[k/2−1]`
              小的数最多只有 `k-2` 个，因此 `A[k/2−1]` 不可能是第 `k` 个数，`A[0]` 到 `A[k/2−1]` 也都不可能是第 `k` 个数，可以全部排除。
            - 如果 `A[k/2−1]>B[k/2−1]` ，则可以排除 `B[0]` 到 `B[k/2−1]`。
            - 如果 `A[k/2−1]=B[k/2−1]` ，则可以归入第一种情况处理。
        - 对于某些情况，需要特殊处理
            - 如果 `A[k/2−1]` 或者 `B[k/2−1]` 越界，那么我们可以选取对应数组中的最后一个元素。在这种情况下，我们必须根据排除数的个数减少 `k` 的值，而不能直接将 `k` 减去 `k/2`。
            - 如果一个数组为空，说明该数组中的所有元素都被排除，我们可以直接返回另一个数组中第 `k` 小的元素。
            - 如果 `k=1`，我们只要返回两个数组首元素的最小值即可。
    - **总结**
        - 核心思想：==找到第k个小的数==
        - 在两个数组中依次比较第 k / 2 个数字，谁小，就说明该数组的前 k / 2 个数字都不是第 k 个小的数，然后缩减该数组，直到 k = 1，然后取两个中最小的那一个
    - 每次缩减数组之后，k要减去数组减少的元素个数

- 代码

  ~~~java
  public double findMedianSortedArrays(int[] nums1, int[] nums2) {
      int len = nums1.length + nums2.length;
  int k = len / 2;
      if (len % 2 == 0) {
          int r1 = findKth(nums1, nums2, k);
          int r2 = findKth(nums1, nums2, k + 1);
          return (r1 + r2) / 2.0;
      } else {
          return findKth(nums1, nums2, k+1);
      }
  }
  
  private int findKth(int[] nums1, int[] nums2, int k) {
      int length1 = nums1.length;
      int length2 = nums2.length;
  
      // index表示的是排除元素之后，“新数组” 的开始位置
      int index1 = 0;
      int index2 = 0;
  
      while (true) {
          // 如果其中一个数组为空，则返回另外一个数组的中位数
          if (index1 == length1) {
              return nums2[index2 + k - 1];
          }
          if (index2 == length2) {
              return nums1[index1 + k - 1];
          }
          // 当 k=1 时退出循环
          if (k == 1) {
              return Math.min(nums1[index1], nums2[index2]);
          }
  
          int half = k / 2;
          // 这步操作保证了newIndex不会超出数组长度
          int newIndex1 = Math.min(index1 + half, length1) - 1;
          int newIndex2 = Math.min(index2 + half, length2) - 1;
          int pivot1 = nums1[newIndex1], pivot2 = nums2[newIndex2];
  
          // k要减去每次排除的元素个数
          if (pivot1 <= pivot2) {
              k -= (newIndex1 - index1 + 1);
              index1 = newIndex1 + 1;
          } else {
              k -= (newIndex2 - index2 + 1);
              index2 = newIndex2 + 1;
          }
      }
  }
  ~~~

---

## 第四讲 排序

### 4.1 巨经典的排序算法

#### 1. 冒泡排序（很简单）

- 平均时间复杂度 ***O(n^2)*** ，空间复杂度 ***O(1)***，稳定

- 基本思想

    - 两个数比较大小，较大的数下沉，较小的数冒起来。

- 演示（图片来自菜鸟教程）

  ![bubbleSort](https://gitee.com/primabrucexu/image/raw/main/pic/2021/09/20210926112142.gif)

- 代码

  ```java
  /**
   * 冒泡排序
   * @param array 待排序的数组
   */
  public static void bubbleSort(int[] array) {
      for(int i=0; i<array.length-1; i++){
          for(int j=array.length-1; j>i; j--){
              if(array[j] < array[j-1]){
                  int temp = array[j];
                  array[j] = array[j-1];
                  array[j-1] = temp;
              }
          }
      }
  }
  ```

#### 2. 选择排序

- 平均时间复杂度 ***O(n^2)*** ，空间复杂度 ***O(1)***，不稳定

- 基本思想

    - 遍历数组，依次找到第 `i` 小的元素，然后把它和第`i` 个元素交换位置

- 演示

  ![selectionSort](https://gitee.com/primabrucexu/image/raw/main/pic/2021/09/20210926112142.gif)

- 代码

  ```java
  /**
   * 选择排序
   * @param array 待排序的数组
   */
  public static void selectSort(int[] array) {
      for(int i=0;i<array.length-1;i++){
          int minIndex = i;
          for(int j=i+1;j<array.length;j++){
              if(array[j]<array[minIndex]){
                  minIndex = j;
              }
          }
          if(minIndex != i){
              int temp = array[i];
              array[i] = array[minIndex];
              array[minIndex] = temp;
          }
      }
  }
  ```

#### 3. 插入排序

- 平均时间复杂度 ***O(n^2)*** ，空间复杂度 ***O(1)***，稳定

- 基本思想

    - 在要排序的一组数中，假定前 `i` 个数已经排好序了，那么第 `i+1` 个数只需要插入到前面已经排好序的数组中即可

- 演示（图片来自菜鸟教程）

  ![insertionSort](https://gitee.com/primabrucexu/image/raw/main/pic/2021/09/20210926112145.gif)

- 代码

  ~~~java
  /**
   * 插入排序
   * @param array 待排序的数组
   */
  public void insertSort(int[] array) {
      for (int i = 0; i < array.length - 1; i++) {
          for (int j = i + 1; j > 0; j--) {
              if (array[j] < array[j-1]) {
                  int temp = array[j-1];
                  array[j-1] = array[j];
                  array[j] = temp;
              } else {
                  break;
              }
          }
      }
  }
  ~~~

#### 4. 归并排序

- 平均时间复杂度 ***O(n log(n))*** ，空间复杂度 ***O(n)***，稳定

- 基本思想（分治）

    - 首先考虑如何合并两个有序数组
        - 我们只需要依次比较两个数组中的第一个数即可，谁小放到新数组中
    - 归并排序的主要思想就是归并两个有序数组。
        - 将原数组分成二组 A，B。如果A，B都是有序的，归并即可。
        - 但是A，B都不是有序的，那么就继续分。直到这个数组中只有1个数据的时候。
        - 这个时候可以认为他是有序的，然后依次向上合并
    - 总结：递归分解数组，然后再从小到大合并

- 演示（图片来自菜鸟教程）

  ![mergeSort](https://gitee.com/primabrucexu/image/raw/main/pic/2021/09/20210926112145.gif)

- 代码

  ```java
  /**
   * 归并排序
   * @param array 需要排序的数组
   * @return 排好序的数组
   */
  public int[] mergeSort(int[] array) {
      // 创建额外的空间
      int[] res = Arrays.copyOf(array, array.length);
      if (res.length < 2) {
          return res;
      }
      int mid = array.length / 2;
      int[] left = Arrays.copyOfRange(array, 0, mid);
      int[] right = Arrays.copyOfRange(array, mid, array.length);
      return merge(mergeSort(left), mergeSort(right));
  }
  
  /**
   * 归并两个有序数组
   * @param array1 有序数组1
   * @param array2 有序数组2
   * @return 归并后的新数组
   */
  private int[] merge(int[] array1, int[] array2) {
      int[] res = new int[array1.length + array2.length];
      int p = 0;
      int i = 0;
      int j = 0;
      while (i < array1.length && j < array2.length) {
          if (array1[i] <= array2[j]) {
              res[p++] = array1[i++];
          } else {
              res[p++] = array2[j++];
          }
      }
      // 剩下了left
      while (i < array1.length) {
          res[p++] = array1[i++];
      }
      // 剩下的是right
      while (j < array2.length) {
          res[p++] = array2[j++];
      }
      return res;
  }
  ```

#### 5. 快速排序（很常用）

- 平均时间复杂度 ***O(n log(n))*** ，空间复杂度 ***O(log n)***，不稳定

- 算法思想（分治）

    - 选取一个key，对于比key大的，全部放到右边，对于比key小的，全部放到key的左边。这样key的位置就可以唯一确定了。然后对key两边的区间重复操作就可以确定所有元素的位置了

- 演示（图片来自菜鸟教程）

  ![img](https://gitee.com/primabrucexu/image/raw/main/pic/2021/09/20210926112146.gif)

- 具体操作

    1. `i=0，j=n-1，key=array[i]`
    2. `j` 向前移动，找到第一个比 `key` 小的元素，把这个元素放到 `i`
    3. `i` 向后移动，找到第一个比 `key` 大的元素，将它放到 `j` 的位置
    4. 在 `i` 的地方放上 `key`
    5. 经过这么一步操作，就可以使得 `[0,i-1]` 中所有的元素都是比 `i` 小的，`[j,n-1]` 中的所有元素都是比 `i` 大 的，然后重复操作，直到 `i==j`
       这样就满足了 `array[0,i-1] < array[i] < array[i+1,n-1]` 。然后 `i`左右两边的区间重复操作。

- 代码

  ```java
  /**
   * @param array 需要排序区间所在的数组
   * @param left 区间的起始下标
   * @param right 区间的结束下标
   */
  public void quickSort(int[] array, int left, int right) {
      if (left < right) {
          int i = left;
          int j = right;
          int key = array[left];
          while (i < j) {
              // 从j开始向左寻找到第一个比 key 小的数
              while (i < j && array[j] >= key) {
                  j--;
              }
              if (i < j ) {
                  array[i] = array[j];
                  i++;
              }
              // 从i开始向右寻找第一个大于等于 key 的数
              while (i < j && array[i] < key) {
                  i++;
              }
              if (i < j) {
                  array[j] = array[i];
                  j--;
              }
          }
          array[i] = key;
          quickSort(array, left, i-1);
          quickSort(array, i+1, right);
      }
  }
  ```

#### 6. 堆排序

- 平均时间复杂度 ***O(n+k)*** ，空间复杂度 ***O(k)***，稳定

- **什么是堆？**

    - 堆是具有以下性质的完全二叉树：每个结点的值都大于或等于其**左右孩子**结点的值，称为大顶堆；或者每个结点的值都小于或等于其**左右孩子**结点的值，称为小顶堆。（注意是左右孩子而不是左右子树）

- 基本思想

    - 将待排序的数组构造成一个大顶堆。此时，堆顶的元素就是最大的，将堆顶元素和最后一个元素交换位置，然后重新构造大顶堆。
    - 我们不需要想构造一颗二叉树一样去构造堆，我们只需要按照以下规则进行对应即可：
        - 即数组下标为 `i` 的元素，他的左右孩子的下标是 `2i+1` 和 `2i+2`
    - 如此一来，我们就不需要额外的空间

- 演示

  ![img](https://gitee.com/primabrucexu/image/raw/main/pic/2021/09/20210926112149.gif)

- 代码

  ```java
  /**
   * 堆排序
   * @param array 待排序的数组
   */
  public void heapSort(int[] array) {
      // len表示的是未进行排序的长度
      int len = array.length;
      for (int i = 0; i < array.length; i++) {
          // 从最后一个非叶子节点开始调整，使其满足大顶堆的性质
          int last = len / 2 - 1;
          for (int j = last; j >= 0; j--) {
              int left = 2 * j + 1;
              int right = left + 1;
              if (array[left] > array[j]) {
                  swap(array, left, j);
              }
              if (right < len && array[right] > array[j]) {
                  swap(array, right, j);
              }
          }
          len--;
          // 将堆顶元素和放到正确的地方
          swap(array, 0, array.length - 1 - i);
      }
  }
  
  /**
   * 交换数组中的两个元素
   * @param array 数组
   * @param i1 元素1
   * @param i2 元素2
   */
  private void swap(int[] array, int i1, int i2) {
      int temp =  array[i1];
      array[i1] = array[i2];
      array[i2] = temp;
  }
  ```

#### 7. 计数排序

- 平均时间复杂度 ***O(n+k)*** ，空间复杂度 ***O(k)***，稳定。其中，k是整数的范围

- 基本思想

    - 遍历数组，得出最大值、最小值、差距三者中的两个。
    - 申请一个新数组，数组长度为差距。然后对原数组中的数进行遍历，记录出现的次数
    - 最后遍历新数组，就可以得到排序后的结果

- 演示

  ![countingSort](https://gitee.com/primabrucexu/image/raw/main/pic/2021/09/20210926112149.gif)

- 代码

  ```java
  /**
   * 计数排序
   * @param array 待排序的数组
   */
  public void countingSort(int[] array) {
      int min = array[0];
      int max = array[0];
      // 找最大值和最小值
      for (int i : array) {
          min = Math.min(i, min);
          max = Math.max(i, max);
      }
  
      // 申请额外的空间，大小为最值之间的范围
      int[] temp = new int[max - min + 1];
  
      // 填充新数组
      for (int i : array) {
          temp[i - min]++;
      }
  
      // 遍历新数组，然后填充原数组
      int index = 0;
      for (int i = 0; i < temp.length; i++) {
          if (temp[i] != 0) {
              Arrays.fill(array, index, index + temp[i], i + min);
              index += temp[i];
          }
      }
  }     
  ```

- 注意：计数排序对于一定范围内的整数进行排序具有最高的效率，**前提是整数的范围不要太大**

#### 8. 桶排序

- 平均时间复杂度 ***O(n)*** ，空间复杂度 ***O(n+k)***，稳定。其中k是桶的数量

- 基本思想

    - 首先划分若干个范围相同的 “桶” ，
    - 然后将待排序的数据扔到属于他自己的桶中
    - 对桶中的数据进行排序
    - 依次输出所有桶中的数据

- 关键

    - 计数排序的优化，适合于对于大量有范围的数据
    - 桶的划分要尽量保证元素应当可以均匀分散到所有桶中，如果说所有元素都集中在一个桶中，那么桶排序就会失效

- 演示

  ![img](https://gitee.com/primabrucexu/image/raw/main/pic/2021/09/20210926112149.gif)

- 代码

    - 桶排序的关键在于桶的划分和选取。需要根据实际使用场景按需进行设计，所以这里不做展示。

---

### 4.2 快速选择

#### [215. 数组中的第K个最大元素（中等）](https://leetcode-cn.com/problems/kth-largest-element-in-an-array/)

- 思路

    - 快速选择算法是快速排序的一个变形
    - 从上文对于快速排序的讲解中不难发现。对于一个 `key` 来说，在进行一次快速排序的过程中，是可以确定出这个 `key` 所在有序数组中的位置。
    - 那么我们只需要关注这个位置是不是我们需要的 `K` 即可。如果比 `k` 大，则在 `key` 的右边再做一次快速排序即可；反之，则在左边做一次快速排序

- 时间复杂度 *O(n)*，空间复杂度 *O(1)*

- 代码

  ~~~java
  public int findKthLargest(int[] nums, int k) {
      int left = 0;
      int right = nums.length - 1;
      int target = nums.length - k;
      while (left < right) {
          int p = quickSort(nums, left, right);
          if (p < target) {
              left = p + 1;
          } else if (p > target) {
              right = p - 1;
          } else {
              return nums[p];
          }
      }
      return nums[left];
  }
  
  // 快速排序函数，返回key的下标
  public int quickSort(int[] array, int left, int right) {
      int i = left;
      int j = right;
      int key = array[left];
      while (i < j) {
          while (i < j && array[j] >= key) {
              j--;
          }
          if (i < j) {
              array[i] = array[j];
              i++;
          }
          while (i < j && array[i] < key) {
              i++;
          }
          if (i < j) {
              array[j] = array[i];
              j--;
          }
      }
      array[i] = key;
      return i;
  }
  ~~~

- 注意点

    - 当 `k=1` 时，函数可能不会在循环体中返回结果。此时，退出循环后`left=5` ，所以要返回 `nums[left]`

---

### 4.3 桶排序

#### [347. 前 K 个高频元素（中等）](https://leetcode-cn.com/problems/top-k-frequent-elements/)

- 思路

    - 典型的桶排序的使用场景，来回顾一下桶排序
        - 桶排序就是把需要排序的元素放到属于他的桶中，然后再对桶中的元素进行排序，最后把所用桶中的元素输出就是排序之后的结果。==重点是让所有元素均匀的分布到所有桶中==
    - 如果我们的桶中只放这个元素自己，那么桶的大小就是这个元素的出现次数，然后根据桶的大小进行排序，就可以很方便的得到出现频率前K个高频元素
    - 重点：将元素和出现频率进行对应
    - 时间 ***O(n log(n))***， 空间 ***O(n)***

- 代码

  ~~~java
  public int[] topKFrequent(int[] nums, int k) {
      int[] res = new int[k];
      // 用哈希表做桶排序，每个元素作为key，出现次数作为value
      Map<Integer, Integer> map = new HashMap<>();
      for (int num : nums) {
          map.put(num, map.getOrDefault(num, 0)+1);
      }
      int[][] bucket = new int[map.size()][2];
      int p = 0;
      // 利用数组将元素和出现次数进行对应
      for (Integer i : map.keySet()) {
          bucket[p][0] = i;
          bucket[p++][1] = map.get(i);
      }
      // 降序排序
      Arrays.sort(bucket, ((o1, o2) -> o2[1]-o1[1]));
      for (int i = 0; i < k; i++) {
          res[i] = bucket[i][0];
      }
      return res;
  }
  ~~~

- 优化

    - 在得到每个元素的频率之后，可以再对频率进行一次桶排序，这样的话可以把时间复杂度降低到 *O(n)*

- 代码

  ~~~java
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
  ~~~

---

### 4.4 基础练习

#### [451. 根据字符出现频率排序（中等）](https://leetcode-cn.com/problems/sort-characters-by-frequency/)

- 思路

    - 桶排序，每个字母一个桶，最后降序输出

- 代码

  ~~~java
  public String frequencySort(String s) {
      Map<Character, Integer> map = new HashMap<>();
      // 对字母进行桶排序，得到每个字母出现的频率
      int max = 0;
      for (int i = 0; i < s.length(); i++) {
          map.put(s.charAt(i), map.getOrDefault(s.charAt(i), 0) + 1);
          max = Math.max(max, map.get(s.charAt(i)));
      }
      // 再对频率进行一次桶排序
      ArrayList<Character>[] bucket = new ArrayList[max + 1];
      for (char c : map.keySet()) {
          int f = map.get(c);
          if (bucket[f] == null) {
              bucket[f] = new ArrayList<>();
          }
          bucket[f].add(c);
      }
      int p = 0;
      char[] chars = s.toCharArray();
      for (int i = max; i >= 0; i--) {
          if (bucket[i] != null) {
              for (char c : bucket[i]) {
                  Arrays.fill(chars, p, p+i, c);
                  p += i;
              }
          }
      }
      return new String(chars);
  }
  ~~~

---

### 4.5 进阶练习

#### [75. 颜色分类（中等）](https://leetcode-cn.com/problems/sort-colors/)

- 思路

    - 遍历数组，对三种颜色出现的次数进行统计，然后填充数组

- 代码

  ~~~java
  public void sortColors(int[] nums) {
      int[] f = new int[3];
      for (int num : nums) {
          f[num]++;
      }
      int p = 0;
      for (int i = 0; i < 3; i++) {
          Arrays.fill(nums, p, p + f[i], i);
          p += f[i];
      }
  }
  ~~~

---

## 第五讲 BFS与DFS

### 5.1 介绍

- BFS和DFS是两种最常见的优先搜索算法，广泛应用于图和树等数据结构的搜索中
- **什么是DFS**
    - 在遍历的时候，就认准一条路走到黑，不撞南墙不回头。撞了南墙之后再回头找别的路
- **什么是BFS**
    - 在进行遍历的时候，每遇到一个“分岔路口”，先随便选一个，记录没选的，然后继续向下走，直到走不下去了。然后在回过头来找最近的“分岔路口”
- **什么是回溯法**
    - 回溯法（backtracking）是优先搜索的一种特殊情况，又称为试探法，常用于需要记录节点状态的深度优先搜索。通常来说，排列、组合、选择类问题使用回溯法比较方便
    - ==回溯法的核心就是回溯，要还原所有被修改的东西==

---

### 5.2 DFS

#### [695. 岛屿的最大面积（中等）](https://leetcode-cn.com/problems/max-area-of-island/)

- 分析

    - 对于图中的任意一个不为0的点，需要搜索的是这个点的四个方向。同时为了保证不重复计算面积，我们需要将已经访问过的点至为0，这样就保证了在计算面积的时候不会重复计算
    - 时间 ***O(n)***， 空间 ***O(n)***，其中 `n` 是地图中所有点的数量

- 题解

  ~~~java
  public int maxAreaOfIsland(int[][] grid) {
      if (grid.length == 0) {
          return 0;
      }
      int maxArea = 0;
      for (int i = 0; i < grid.length; i++) {
          for (int j = 0; j < grid[0].length; j++) {
              int area = dfs(i, j, grid);
              maxArea = Math.max(maxArea, area);
          }
      }
  
      return maxArea;
  }
  
  private int dfs(int i, int j, int[][] grid) {
      // 这里要注意判断时的顺序
      if (i < 0 || j < 0 || i == grid.length || j == grid[0].length || grid[i][j] == 0) {
          return 0;
      }
      int size = 1;
      grid[i][j] = 0;
      // 这个点的面积 S =  s上 + s下 + s左 + s右
      size += dfs(i, j - 1, grid) + dfs(i, j + 1, grid) + dfs(i - 1, j, grid) + dfs(i + 1, j, grid);
  
      return size;
  }
  ~~~

#### [547. 朋友圈（中等）](https://leetcode-cn.com/problems/friend-circles/)

- 分析

    - 这道题和上一道题目有异曲同工之妙，类似于上一题中计算岛屿的数量
    - 同样要注意对已经搜索过朋友圈的同学进行标记
    - 时间 ***O(n)***， 空间 ***O(n)***，其中 `n` 是同学个数

- 题解

  ~~~java
  public int findCircleNum(int[][] M) {
      if (M.length == 0) {
          return 0;
      }
      int n = 0;
      boolean[] flag = new boolean[M.length];
      for (int i = 0; i < M.length; i++) {
          // 如果同学i没有搜索过，则搜索同学i
          if (!flag[i]) {
              // 标记同学i已经被搜索
              flag[i] = true;
              dfs(i, M, flag);
              n++;
          }
      }
      return n;
  }
  
  private void dfs(int i, int[][] M, boolean[] flag) {
      // 搜索同学i的朋友圈
      for (int k = 0; k < M.length; k++) {
          if (!flag[k] && M[i][k] == 1) {
              flag[k] = true;
              dfs(k, M, flag);
          }
      }
  }
  ~~~

#### [417. 太平洋大西洋水流问题（中等）](https://leetcode-cn.com/problems/pacific-atlantic-water-flow/)

- 分析

    - 对于一个陆地单元来说，如果水流可以从这里同时流动到两个大洋，那么这个单元必定在两个大洋的交界处。或者说可以通过其他单元流动到两个海洋
    - 如果说我们对于每一个点都进行一次判断，看他是否能走到两个大洋的话，会出现不少重复的判断。
    - 所以我们可以考虑倒着来。从海洋出发，然后看看那些点可以到达，这样的话就可以避免重复判断。（这里有点动态规划的意思，单元 `i` 能否留到海洋，由他上下左右四个方向上的单元 和 其能否到达这四个方向上的单元共同决定）
    - 时间 ***O(n)***， 空间 ***O(n)***，其中 `n` 是陆地单元个数

- 题解

  ~~~java
  public static List<List<Integer>> pacificAtlantic(int[][] matrix) {
      if (matrix.length == 0) {
      return null;
      }
      List<List<Integer>> res = new ArrayList<>();
      int n = matrix.length;
      int m = matrix[0].length;
      boolean[][] toPacific = new boolean[n][m];
      boolean[][] toAtlantic = new boolean[n][m];
  
      // 从两个海洋出发，进行搜索，
      for (int i = 0; i < n; i++) {
          dfs(matrix, toPacific, i, 0);
          dfs(matrix, toAtlantic, i, m - 1);
      }
      for (int i = 0; i < m; i++) {
          dfs(matrix, toPacific, 0, i);
          dfs(matrix, toAtlantic, n - 1, i);
      }
  
      // 取交集
      for (int i = 0; i < n; i++) {
          for (int j = 0; j < m; j++) {
              if (toAtlantic[i][j] && toPacific[i][j]) {
                  res.add(Arrays.asList(i, j));
              }
          }
      }
      return res;
  }
  
  /**
   * @param matrix 矩阵
   * @param flag   状态表
   * @param i      纵坐标
   * @param j      横坐标
   */
  private static void dfs(int[][] matrix, boolean[][] flag, int i, int j) {
      if (i < 0 || j < 0 || i == matrix.length || j == matrix[0].length || flag[i][j]) {
          return;
      }
      flag[i][j] = true;
  
      // 因为我们倒过来进行搜索，要能流过滤，也就是说要满足这个点的高度小于等于某个方向上的高度，这样才能流过来
      if (i > 0 && matrix[i][j] <= matrix[i - 1][j]) {
          dfs(matrix, flag, i - 1, j);
      }
      if (i < matrix.length - 1 && matrix[i][j] <= matrix[i + 1][j]) {
          dfs(matrix, flag, i + 1, j);
      }
      if (j > 0 && matrix[i][j] <= matrix[i][j - 1]) {
          dfs(matrix, flag, i, j - 1);
      }
      if (j < matrix[0].length - 1 && matrix[i][j] <= matrix[i][j + 1]) {
          dfs(matrix, flag, i, j + 1);
      }
  }
  ~~~

---

### 5.3 回溯法

#### [46. 全排列（中等）](https://leetcode-cn.com/problems/permutations/)

- 分析

    - 全排列是一个典型的回溯算法
    - 我们先往栈里面放一个数，然后标记一个这个数已经被使用过了，然后对剩下的继续搜索。当搜索完所有的数之后，我们将当前栈中的内容做个记录。然后抛出栈顶元素，还原状态，再继续搜索
    - 递归终点：栈内的元素个数 等于 `nums` 中的元素个数
    - 时间 ***O(n)***， 空间 ***O(n)***，其中 `n` 是元素个数

- 题解

  ~~~java
  public List<List<Integer>> permute(int[] nums) {
      List<List<Integer>> res = new ArrayList<>();
      if (nums.length == 0) {
          return res;
      }
      int n = nums.length;
      boolean[] flag = new boolean[n];
      Deque<Integer> stack = new ArrayDeque<>(3);
      dfs(nums, flag, stack, res, n);
      return res;
  }
  
  /**
   * @param nums  需要全排列的数组
   * @param flag  状态数组，记录是否访问过该元素
   * @param stack 记录单次排列结果
   * @param res   记录所有的排列结果
   * @param n     元素个数
   */
  private void dfs(int[] nums, boolean[] flag, Deque<Integer> stack, List<List<Integer>> res, int n) {
      // 递归终点
      if (stack.size() == n) {
          // 为了防止添加的是引用而不是整个栈，所以这里直接创建一个新的对象即可
          res.add(new ArrayList<>(stack));
          return;
      }
      for (int i = 0; i < n; i++) {
          // 如果这个元素没有被访问过，那么访问，然后递归调用。递归结束之后，还原状态变量
          if (!flag[i]) {
              flag[i] = true;
              stack.addLast(nums[i]);
              dfs(nums, flag, stack, res, n);
              flag[i] = false;
              stack.removeLast();
          }
      }
  }
  ~~~

#### [79. 单词搜索（中等）](https://leetcode-cn.com/problems/word-search/)

- 分析

    - 回溯算法

- 题解

  ```java
  public static boolean exist(char[][] board, String word) {
      if (board.length == 0) {
          return false;
      }
      boolean[][] flag = new boolean[board.length][board[0].length];
      for (int i = 0; i < board.length; i++) {
          for (int j = 0; j < board[0].length; j++) {
              System.out.println("i = " + i + ", j = " + j);
              if (dfs(board, word, flag, 0, i, j)) {
                  return true;
              }
          }
      }
      return false;
  }
  
  /**
   * @param board 字母表
   * @param word  单次
   * @param flag  标记数组
   * @param k     单词搜索到的位置
   * @param i     横坐标
   * @param j     纵坐标
   * @return 查找结果
   */
  private static boolean dfs(char[][] board, String word, boolean[][] flag, int k, int i, int j) {
      if (k == word.length()) {
          return true;
      }
      if (i < 0 || j < 0 || i == board.length || j == board[0].length || flag[i][j]) {
          return false;
      }
      if (word.charAt(k) != board[i][j]) {
          return false;
      }
      // 向上下左右四个方向去搜索
      flag[i][j] = true;
      boolean up = dfs(board, word, flag, k + 1, i - 1, j);
      boolean down = dfs(board, word, flag, k + 1, i + 1, j);
      boolean left = dfs(board, word, flag, k + 1, i, j - 1);
      boolean right = dfs(board, word, flag, k + 1, i, j + 1);
      flag[i][j] = false;
      return up || down || left || right;
  }
  ```

- 分析

    - 当board中全部是word的字符时，会超时，原因是以为递归很容易超时。上述给出的算法，时间复杂度为***O(mn x 4^L)***，其中，`m`，`n`，`L`
      分别为字符表的长、宽和word的长度。显而易见的，我们需要进行一些剪枝。

- 优化

    - 参考官方题解给出的解决，优化代码结构如下

      ~~~java
      private static boolean dfs(char[][] board, String word, boolean[][] flag, int k, int i, int j) {
          if (word.charAt(k) != board[i][j]) {
              return false;
          }
          if (k == word.length() - 1) {
              return true;
          }
          // 向上下左右四个方向去搜索
          flag[i][j] = true;
          int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
          boolean result = false;
          for (int[] dir : directions) {
              int newi = i + dir[0];
              int newj = j + dir[1];
              if (newi >= 0 && newi < board.length && newj >= 0 && newj < board[0].length) {
                  if (!flag[newi][newj]) {
                      boolean p = dfs(board, word, flag, k + 1, newi, newj);
                      if (p) {
                          result = true;
                          break;
                      }
                  }
              }
          }
          flag[i][j] = false;
          return result;
      }
      ~~~

#### [51. N 皇后（困难）](https://leetcode-cn.com/problems/n-queens/)

- 分析

    - 逐行安排皇后，同时标记不能安排的地方。检查下一行，如果下一行没地方，则回溯
    - 时间 ***O(n!)***， 空间 ***O(n)***，其中 `n` 是皇后个数

- 题解

  ~~~java
  public static List<List<String>> solveNQueens(int n) {
      List<List<String>> res = new ArrayList<>();
      if (n == 0) {
          return res;
      }
      // 创建棋盘
      List<char[]> map = new ArrayList<>(n);
      for (int i = 0; i < n; i++) {
          char[] p = new char[n];
          Arrays.fill(p, '.');
          map.add(p);
      }
  
      // 创建访问数组
      boolean[][] flag = new boolean[n][n];
      NQueens(res, map, flag, 0, n);
      return res;
  }
  
  /**
   * @param res   结果集
   * @param map   棋盘
   * @param flag  位置数组
   * @param index 第几行的皇后需要安排
   * @param n     皇后的数量
   */
  private static void NQueens(List<List<String>> res, List<char[]> map, boolean[][] flag, int index, int n) {
      if (index == n) {
          List<String> list = new ArrayList<>();
          for (char[] chars : map) {
              list.add(new String(chars));
          }
          res.add(list);
          return;
      }
      // 对在index行上的皇后依次安排
      for (int i = 0; i < n; i++) {
          if (flag[index][i]) {
              continue;
          }
          map.get(index)[i] = 'Q';
          List<int[]> temp = setFlag(n, flag, index, i);
          NQueens(res, map, flag, index + 1, n);
          // 把上次修改的改回来。思考为什么不能直接复用setFlag函数？
          for (int[] p : temp) {
              flag[p[0]][p[1]] = false;
          }
          map.get(index)[i] = '.';
      }
  }
  
  /**
   * 记录新增皇后后修改的节点。这里要注意，原有不可放置的地方不能被记录
   * 因为恢复的时候要按照这里记录的信息进恢复
   *
   * @param n      皇后的数量
   * @param flag   状态表
   * @param row    新增皇后的行号
   * @param column 新增行号的列号
   * @return 新增皇后之后，修改的状态表节点
   */
  private static List<int[]> setFlag(int n, boolean[][] flag, int row, int column) {
      List<int[]> res = new ArrayList<>();
      for (int nr = row + 1; nr < n; nr++) {
          for (int nc = 0; nc < n; nc++) {
              if (flag[nr][nc]) {
                  continue;
              }
              if (nc == column || nc + nr == column + row || nc - nr == column - row) {
                  flag[nr][nc] = true;
                  res.add(new int[]{nr, nc});
              }
          }
      }
      return res;
  }
  ~~~

- 重点

    - ==为什么在新增皇后之后需要记录状态表修改的位置？==

    - 假设我们在安排第 `i` 行的皇后，成功安排好了。然后去安排第 `i+1` 行的皇后。在对该行进行试探的时候，如果某处试探失败，我们需要回溯，把状态表改回原来的样子。那么如果直接根据位置进行修改，可能会影响到第 `i`
      行皇后放置后增加的修改条件。

    - 举例

      <img src="https://gitee.com/primabrucexu/image/raw/main/pic/2021/09/20210926112237.png" alt="image-20201214181517768" style="zoom: 50%;" />

        - 如图所示，红色标记的是第1行皇后所在位置的限制，绿色表示的是安排第二行之后**新增**的位置限制
        - 因为我们采取逐行进行试探，所以不需要记录同行中的变化
        - 假如绿色三角位置的皇后试探失败，
        - 如果我们根据绿色三角的位置进行清除，清除行，清除对角线，清除列。会删除到红色位置的标记，这就使得红色三角的限制不完善。
        - 所以我们要记录绿色的位置，然后根据绿色的位置进行还原。**（这就是回溯法的精髓所在，==只改上一次改的==）**

---

### 5.4 BFS

#### [934. 最短的桥（中等）](https://leetcode-cn.com/problems/shortest-bridge/)

- 分析

    - 要架桥，那么就要找到这两个岛。于是先进行一次BFS，找到两个岛，同时记录这个岛上的所有点。
    - 然后选择一个岛，从这个岛出发，进行BFS搜索，寻找到另外一个岛的路径长度，返回最短的路径长度
    - 时间 ***O(MN)***， 空间 ***O(MN)***，其中 `M` 和 `N`  是地图的长和宽

- 代码

  ~~~java
  public int shortestBridge(int[][] A) {
      Queue<int[]> queue = new LinkedList<>();
      Queue<int[]> island = new LinkedList<>();
      int[][] direction = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
      int row = A.length;
      int column = A[0].length;
      // 找到第一个岛
      boolean sign = false;
      for (int i = 0; i < row; i++) {
          if (sign) {
              break;
          }
          for (int j = 0; j < column; j++) {
              if (A[i][j] == 1) {
                  queue.offer(new int[]{i, j});
                  // bfs(queue, island, A, direction);
                  dfs(island, A, i, j);
                  sign = true;
                  break;
              }
          }
      }
  
      // bfs查找另外一个岛
      int len = -1;
      while (!island.isEmpty()) {
          int size = island.size();
          len++;
          while (size-- > 0) {
              int[] p = island.poll();
              for (int[] i : direction) {
                  int newR = p[0] + i[0];
                  int newC = p[1] + i[1];
                  if (newR >= 0 && newR < row && newC >= 0 && newC < column) {
                      if (A[newR][newC] == 2) {
                          continue;
                      }
                      if (A[newR][newC] == 1) {
                          return len;
                      }
                      A[newR][newC] = 2;
                      island.offer(new int[]{newR, newC});
                  }
              }
          }
      }
      return len;
  }
  
  private void dfs(Queue<int[]> island, int[][] map, int row, int column) {
      if (row < 0 || column < 0 || row == map.length || column == map[0].length || map[row][column] == 2 || map[row][column] == 0) {
          return;
      }
      if (map[row][column] == 1) {
          map[row][column] = 2;
          island.offer(new int[]{row, column});
      }
      dfs(island, map, row - 1, column);
      dfs(island, map, row + 1, column);
      dfs(island, map, row, column - 1);
      dfs(island, map, row, column + 1);
  }
  
  private void bfs(Queue<int[]> queue, Queue<int[]> island, int[][] map, int[][] direction) {
      while (!queue.isEmpty()) {
          int[] p = queue.poll();
          island.offer(p);
          int r = p[0];
          int c = p[1];
          map[r][c] = 2;
          for (int[] i : direction) {
              int newR = r + i[0];
              int newC = c + i[1];
              if (newR >= 0 && newR < map.length && newC >= 0 && newC < map[0].length && map[newR][newC] == 1) {
                  queue.offer(new int[]{newR, newC});
              }
          }
      }
  }
  ~~~

- 分析：

    - 我的思路是进行两次BFS，提交之后发现超时了。不明所以，自测的时候发现并不是进入死循环，时间大概在3秒左右。

    - 翻看题解，发现题解采用了DFS。尝试使用了DFS，自测时间发现时间大大缩短，因此不明所以。如果有大佬知道为什么的话，还望解惑

#### [126. 单词接龙 II（困难）](https://leetcode-cn.com/problems/word-ladder-ii/)

- 分析

    - 看到最短就要想到BFS，看到BFS就要想到图。由于没有给出明确的图的模型，所以我们需要将题目中的要求抽象成图，然后进行BFS搜索
    - 如果两个单词之间可以进行相互转换，那么我们就认为这两个单词相连，由此可以抽象出一个无向图
    - 在得到图之后，BFS搜索找到最短路径的长度即可
    - 在得到最短路径之后，我们在用回溯法去搜索
    - 时间 ***O(N^2 x C)***， 空间 ***O(N^2)***，其中 `N` 为单词表的长度，`C`为单词表中单词的长度

- 代码

  ~~~java
  public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
      List<List<String>> res = new ArrayList<>();
  if (beginWord.length() == 1) {
          res.add(Arrays.asList(beginWord, endWord));
          return res;
      }
      Map<String, Set<String>> map = new HashMap<>();
      generate(map, beginWord, wordList);
      if (!map.containsKey(endWord)) {
          return res;
      }
      // bfs找到最短路径长度
      Set<String> visited = new HashSet<>();
      Deque<String> path = new ArrayDeque<>();
      int step = bfs(beginWord, endWord, map, path, visited);
      System.out.println(step);
      // dfs回溯搜索路径
      List<List<String>> allPath = new ArrayList<>();
      path.clear();
      visited.clear();
      dfs(res, beginWord, endWord, map, path, visited, step);
      return res;
  }
  
  public void dfs(List<List<String>> res, String beginWord, String endWord, Map<String, Set<String>> map,
                         Deque<String> stack, Set<String> visited, int step) {
  
      if (beginWord.equals(endWord) && stack.size() == step - 1) {
          stack.addLast(beginWord);
          res.add(new ArrayList<>(stack));
          stack.removeLast();
          return;
      }
      if (visited.contains(beginWord) || stack.size() >= step) {
          return;
      }
      visited.add(beginWord);
      stack.addLast(beginWord);
      for (String s : map.get(beginWord)) {
          dfs(res, s, endWord, map, stack, visited, step);
      }
      visited.remove(beginWord);
      stack.removeLast();
  }
  
  public int bfs(String beginWord, String endWord,
                        Map<String, Set<String>> map, Deque<String> queue, Set<String> visited) {
      queue.add(beginWord);
      visited.add(beginWord);
      int len = 1;
      int size = queue.size();
      while (size-- > 0 && !queue.isEmpty()) {
          String current = queue.removeFirst();
          Set<String> set = map.get(current);
          for (String s : set) {
              if (visited.contains(s)) {
                  continue;
              }
              if (s.equals(endWord)) {
                  return len + 1;
              }
              queue.addLast(s);
              visited.add(s);
          }
          if (size == 0) {
              size = queue.size();
              len++;
          }
      }
      return 0;
  }
  
  public void generate(Map<String, Set<String>> map, String beginWord, List<String> wordList) {
      List<String> list = new ArrayList<>(wordList);
      list.add(beginWord);
      for (String s1 : list) {
          Set<String> set = new HashSet<>();
          for (String s2 : list) {
              if (check(s1, s2)) {
                  set.add(s2);
              }
          }
          map.put(s1, set);
      }
  }
  
  public boolean check(String word1, String word2) {
      if (word1.equals(word2)) {
          return false;
      }
      int p = 0;
      for (int i = 0; i < word1.length(); i++) {
          if (word1.charAt(i) != word2.charAt(i)) {
              p++;
          }
      }
      return p < 2;
  }
  ~~~

- 分析：超时。当图比较复杂时，时间消耗主要集中在DFS回溯搜索上。

- 优化：在BFS搜索的时候，记录路径。然后一起返回

  ~~~java
  private static void bfs2(List<List<String>> res, String beginWord, String endWord, Map<String, Set<String>> map,
                    Set<String> visited) {
      // 把beginword放到路径中，然后放到队列中
      Deque<Deque<String>> queue = new ArrayDeque<>();
      Deque<String> p = new LinkedList<>();
      p.add(beginWord);
      visited.add(beginWord);
      queue.add(p);
      int size = queue.size();
      int len = 1;
      // 因为bfs进行搜索的时候，第一次找到的一定是最短的路径，此时记录最短路径长度
      // 再找到最短的之后，就没有必要在继续向下一层去搜索了，只要横向搜索即可
      int min = map.size() + 1;
      while(size-- > 0 && !queue.isEmpty() && min >= len) {
          // 出队，获得原路径上最后一个点，再从这个点开始进行搜索
          Deque<String> path = queue.removeFirst();
          String last = path.getLast();
          for(String s : map.get(last)) {
              if (visited.contains(s)) {
                  continue;
              }
              if(s.equals(endWord) && min >= len) {
                  path.addLast(s);
                  min = len;
                  res.add(new ArrayList<>(path));
                  continue;
              }
              Deque<String> nextPath = new ArrayDeque<>(path);
  
              nextPath.addLast(s);
              queue.addLast(nextPath);
          }
          if (size == 0) {
              size = queue.size();
              len++;
              // 在这里记录这层中被访问过的元素
              for (Deque<String> strings : queue) {
                  visited.addAll(strings);
              }
          }
      }
  }
  ~~~

---

### 5.5 基础练习

#### [257. 二叉树的所有路径（简单）](https://leetcode-cn.com/problems/binary-tree-paths/)

- 分析

    - 很简单的回溯算法
    - 时间 ***O(N^2)***， 空间 ***O(N^2)***，其中 `N` 树的节点数量

- 代码

  ~~~java
  public List<String> binaryTreePaths(TreeNode root) {
      List<String> res = new ArrayList<>();
  if(root == null) {
          return res;
      }
      Stack<TreeNode> stack = new Stack<>();
      stack.push(root);
      dfs(res, stack, root);	
      return res;
  }
  
  private void dfs(List<String> res, Stack<TreeNode> stack, TreeNode node) {
      // TODO Auto-generated method stub
      if(node.left == null && node.right == null) {
          StringBuilder sb = new StringBuilder();
          for(int i = 0; i < stack.size(); i++) {
              if (i != 0) {
                  sb.append("->");
              }
              sb.append(stack.get(i).val);
          }
          res.add(sb.toString());
      }
      if(node.left != null) {
          stack.push(node.left);
          dfs(res, stack, node.left);
          stack.pop();
      }
      if(node.right != null) {
          stack.push(node.right);
          dfs(res, stack, node.right);
          stack.pop();
      }
  }
  ~~~

#### [39. 组合总和（中等）](https://leetcode-cn.com/problems/combination-sum/)

- 分析

    - 回溯算法
    - 时间 ***O(S)***， 空间 ***O(target)***，其中 `S` 为所有可行解的长度之和

- 代码

  ~~~java
  public List<List<Integer>> combinationSum(int[] candidates, int target) {
      List<List<Integer>> res = new ArrayList<>();
  Deque<Integer> stack = new ArrayDeque<>();
      Arrays.sort(candidates);
      dfs(res, stack, candidates, 0, target);
      return res;
  }
  
  private void dfs(List<List<Integer>> res, Deque<Integer> stack, int[] nums, int current, int target) {
      // TODO Auto-generated method stub
      if (current == target) {
          List<Integer> list = new ArrayList<>(stack);
          Collections.sort(list);
          for(List<Integer> l : res) {
              if (list.equals(l)) {
                  return;
              }
          }
          res.add(list);
          return;
      }
      for(int i : nums) {
          if(current + i > target) {
              return;
          }
          stack.addLast(i);
          dfs(res, stack, nums, current + i, target);
          stack.removeLast();
      }
  }
  ~~~

- 缺点：简单剪枝的回溯算法，效率较低

#### [130. 被围绕的区域（中等）](https://leetcode-cn.com/problems/surrounded-regions/)

- 分析

    - 因为任何与边界相连的O都不会被填充为X，那么我们就从边界开始寻找。找到所有不会被填充的位置，然后填充除了这些位置之外的位置
    - 时间 ***O(MN)***， 空间 ***O(MN)***，其中 `M` 和 `N`  是地图的长和宽

- 代码

  ~~~java
  public void solve(char[][] board) {
      if (board.length == 0) {
      return;
      }
      boolean[][] flag = new boolean[board.length][board[0].length];
      int row = board.length;
      int coloum = board[0].length;
      // 先搜索上下边界的点
      for (int i = 0; i < coloum; i++) {
          if (board[0][i] == 'O' && !flag[0][i]) {
              dfs(board, flag, 0, i);
          }
          if (board[row - 1][i] == 'O' && !flag[row - 1][i]) {
              dfs(board, flag, row - 1, i);
          }
      }
      // 再搜索左右边界的点
      for (int i = 0; i < row; i++) {
          if (board[i][0] == 'O' && !flag[i][0]) {
              dfs(board, flag, i, 0);
          }
          if (board[i][coloum - 1] == 'O' && !flag[i][coloum - 1]) {
              dfs(board, flag, i, coloum - 1);
          }
      }
      for (int i = 0; i < row; i++) {
          for (int j = 0; j < coloum; j++) {
              if (board[i][j] != 'X' && !flag[i][j]) {
                  board[i][j] = 'X';
              }
          }
      }
  }
  
  private void dfs(char[][] board, boolean[][] flag, int r, int c) {
      if (board[r][c] != 'O') {
          return;
      }
      flag[r][c] = true;
      int[][] pos = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
      for (int i = 0; i < 4; i++) {
          int nr = r + pos[i][0];
          int nc = c + pos[i][1];
          if (nr >= 0 && nc >= 0 && nr < board.length && nc < board[0].length && !flag[nr][nc]) {
              dfs(board, flag, nr, nc);
          }
      }
  }
  ~~~

---

### 5.6 进阶练习

#### [47. 全排列 II（中等）](https://leetcode-cn.com/problems/permutations-ii/)

- 分析

    - 这个和普通的全排列问题不一样。区别在于本题给出的元素中包含重复的数字，这样就会导致可能存在重复的结果。关键就是怎么把结果去重
    - 时间 ***O(n x n!)***， 空间 ***O(n)***，其中 `n` 是元素个数。因为对于 `n` 个数，全排列的结果数是 `n！`，每次在去重的时候，都要遍历已经排列出来的所有组合

- 代码

  ~~~java
  public List<List<Integer>> permuteUnique(int[] nums) {
      List<List<Integer>> res = new ArrayList<>();
  Deque<Integer> stack = new ArrayDeque<>(nums.length);
      boolean[] flag = new boolean[nums.length];
      dfs(res, nums, stack, flag);
      return res;
  }
  
  private void dfs(List<List<Integer>> res, int[] nums, Deque<Integer> stack, boolean[] flag) {
      if(stack.size() == nums.length) {
          List<Integer> list = new ArrayList<>(stack);
          for(List<Integer> l : res) {
              if (l.equals(list)) {
                  return;
              }
          }
          res.add(list);
          return;
      }
      for(int i = 0; i < nums.length; i++) {
          if(!flag[i]) {
              stack.addLast(nums[i]);
              flag[i] = true;
              dfs(res, nums, stack, flag);
              stack.removeLast();
              flag[i] = false;
          }
      }
  }
  ~~~

- 问题：在完成一次全排列之后进行查重。这样可能会进行多次无用的全排列，从而提高耗时

#### [40. 组合总和 II（中等）](https://leetcode-cn.com/problems/combination-sum-ii/)

- 分析

    - 依旧是回溯法，由于存在重复的数字，且不允许给出重复的解，所以要进行去重
    - 常用的而且比较显而易见的剪枝方法是将 `candidates` 排序，然后在去排列组合。对于给出的 `current` 如果已经大于 `target`，则直接返回

- 代码

  ~~~java
  public List<List<Integer>> combinationSum2(int[] candidates, int target) {
      List<List<Integer>> res = new ArrayList<>();
  Arrays.sort(candidates);
      int sum = 0;
      for(int i : candidates) {
          sum += i;
      }
      if(sum < target) {
          return res;
      }
      Deque<Integer> stack = new ArrayDeque<>();
      boolean[] visited = new boolean[candidates.length];
      dfs(res, stack, visited, candidates, 0, target);
      return res;
  }
  
  private void dfs(List<List<Integer>> res, Deque<Integer> stack, boolean[] flag, int[] nums, int current,
          int target) {
      if (current == target) {
          List<Integer> list = new ArrayList<>(stack);
          Collections.sort(list);
          for(List<Integer> l : res) {
              if (l.equals(list)) {
                  return;
              }
          }
          res.add(list);
          return;
      }
      for(int i = 0; i< nums.length; i++) {
          if(current + nums[i] <= target && !flag[i]) {
              stack.addLast(nums[i]);
              flag[i] = true;
              dfs(res, stack, flag, nums, current + nums[i], target);
              stack.removeLast();
              flag[i] = false;
          } else {
              return;
          }
      }
  }
  ~~~

#### [310. 最小高度树（中等）（有缺陷，不完善）](https://leetcode-cn.com/problems/minimum-height-trees/)

- 分析

    - 转换图的存储方式，使用hashmap存储图，key是节点编号，value是相连的节点编号
    - 要找最小高度的数，而且最小高度的数可能不只一颗，所以很容易就想到了遍历所有节点，然后找到高度最小树。
    - 时间 ***O(n^2)***， 空间 ***O(n)***，

- 代码

  ~~~java
  public static List<Integer> findMinHeightTrees(int n, int[][] edges) {
      List<Integer> res = null;
  if(n <= 2) {
          res = new ArrayList<>();
          for(int i = 0; i < n; i++) {
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
      for(int i = 0; i < n; i++) {
          boolean[] flag = new boolean[n];
          int h = bfs(map, i, flag);
          List<Integer> list = hmap.getOrDefault(h, new ArrayList<>());
          list.add(i);
          hmap.put(h, list);
      }
      System.out.println(hmap);
      for (int i = 0; i < n; i++) {
          if (hmap.containsKey(i)) {
              res = hmap.get(i);
              break;
          }
      }
      return res;
  }
  
  private static int bfs(Map<Integer, List<Integer>> map, int n, boolean[] flag) {
      Deque<Integer> queue = new ArrayDeque<>();
      queue.addLast(n);
      int size = queue.size();
      int h = 0;
      flag[n] = true;
      while(size-- > 0 && !queue.isEmpty()) {
          List<Integer> list = map.get(queue.removeFirst());
  
          for(int i : list) {
              if (!flag[i]) {
                  queue.addLast(i);
                  flag[i] = true;
              }	
          }
          if(size == 0) {
              size = queue.size();
              h++;
          }
      }
  
      return h;
  }
  
  private static void set(Map<Integer, List<Integer>> map, int n, int[][] edges) {
      for(int[] i : edges) {
          List<Integer> list1 = map.getOrDefault(i[0], new ArrayList<Integer>());
          list1.add(i[1]);
          map.put(i[0], list1);
          List<Integer> list2 = map.getOrDefault(i[1], new ArrayList<Integer>());
          list2.add(i[0]);
          map.put(i[1], list2);
      }
  }
  ~~~

- 缺点：

    - 对于每一颗树，都要进行 ***O(n)*** 级别的计算高度。这在树的数量非常多的时候会浪费很多时间。
    - 不过我们可以优化计算高度的过程，如果出现了较小的高度，那么我们在BFS计算高度的时候，如果目前高度已经超过了这个较小的高度，那么我们可以直接终止这次计算，这样的话能减少额外计算高度的代价

- 优化

  ~~~java
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
  ~~~

- 不过就算优化过了BFS求高度，依然还是超时

- 官方题解

    - LeetCode美国站的官方题解给出了 ***O(N)*** 级别的算法，不过这涉及到了图的拓扑排序，所以这里我们暂且搁置。

#### [37. 解数独（困难）](https://leetcode-cn.com/problems/sudoku-solver/)

- 分析

    - 数独题，是十分经典的回溯算法的题目。事实上，回溯法对于数独题来说并不是最优解，不过我们这里还是用了回溯法，作为对回溯法的巩固
    - 先遍历整个列表,当我们遍历到第 `i` 行第 `j` 列的位置时
        - 如果这个位置是空白元素，那么我们将保存这个位置，方便后续的递归操作
        - 如果这个位置是一个数字，那么我们将 `row[i][x - 1]` 、 `column[j][x - 1]` 、`block[i / 3][j / 3][x - 1]` 标记为 `true`
          。其中`row[i][x - 1]` 表示在第 `i` 中，数字 `x` 已经出现过了
    - 然后再进行回溯搜索
        - 在遍历到 `board[i][j]` 时， 尝试填充数字 `x`， 其中要求 `row[i][x - 1]` 、 `column[j][x - 1]` 、`block[i / 3][j / 3][x - 1]`
          均为 `false` ，同时将上述三个位置标记为 `true`。
    - 重点：记得要让递归及时停止

- 代码

  ~~~java
  public void solveSudoku(char[][] board) {
      boolean[][] row = new boolean[9][9];
      boolean[][] column = new boolean[9][9];
      boolean[][][] block = new boolean[3][3][9];
      List<int[]> pos = new ArrayList<>();
      for (int i = 0; i < 9; i++) {
          for (int j = 0; j < 9; j++) {
              if (board[i][j] != '.') {
                  int p = board[i][j] - '1';
                  row[i][p] = true;
                  column[j][p] = true;
                  block[i / 3][j / 3][p] = true;
              } else {
                  pos.add(new int[]{i, j});
              }
          }
      }
      boolean[] flag = new boolean[1];
      dfs(board, pos, 0, row, column, block, flag);
  }
  
  private void dfs(char[][] board, List<int[]> pos, int n, boolean[][] row, boolean[][] column, boolean[][][] block, boolean[] flag) {
      if (n == pos.size()) {
          flag[0] = true;
          return;
      }
      int[] o = pos.get(n);
      int r = o[0];
      int c = o[1];
      for (int i = 0; i < 9; i++) {
          if (!row[r][i] && !column[c][i] && !block[r / 3][c / 3][i] && !flag[0]) {
              row[r][i] = true;
              column[c][i] = true;
              block[r / 3][c / 3][i] = true;
              board[r][c] = (char) (i + '1');
              dfs(board, pos, n + 1, row, column, block, flag);
              row[r][i] = false;
              column[c][i] = false;
              block[r / 3][c / 3][i] = false;
          }
      }
  }
  ~~~

---



