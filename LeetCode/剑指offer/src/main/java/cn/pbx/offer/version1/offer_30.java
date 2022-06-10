package cn.pbx.offer.version1;

import java.util.*;

/**
 * 方法一：自定义链表，push的时候把当前最小值记录下来
 * 方法二：链表+优先队列
 *
 * @author BruceXu
 * @date 2022-01-15
 */
public class offer_30 {

    class MinStack {
        int min;
        Deque<Integer> deque;

        /**
         * initialize your data structure here.
         */
        public MinStack() {
            deque = new LinkedList<>();
        }

        public void push(int x) {
            min = deque.isEmpty() ? x : Math.min(min, x);
            deque.addLast(x);
        }

        public void pop() {
            Integer remove = deque.removeLast();
            if (remove == min && !deque.isEmpty()) {
                // 如果最小值出栈了，则需要维护最小值
                List<Integer> list = new ArrayList<>(deque);
                Collections.sort(list);
                min = list.get(0);
            }
        }

        public int top() {
            return deque.getLast();
        }

        public int min() {
            return min;
        }
    }

}
