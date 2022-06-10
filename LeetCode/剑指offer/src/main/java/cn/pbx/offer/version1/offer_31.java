package cn.pbx.offer.version1;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author BruceXu
 * @date 2022/6/9
 */
public class offer_31 {

    public static void main(String[] args) {
        offer_31 demo = new offer_31();

        int[] pushed = {1, 0};
        int[] popped = {1, 0};
        System.out.println(demo.validateStackSequences(pushed, popped));
    }

    /**
     * 所有数字只能进出栈一次
     * 核心思想：模拟进出栈
     *
     * @param pushed
     * @param popped
     * @return
     */
    public boolean validateStackSequences(int[] pushed, int[] popped) {
        Deque<Integer> deque = new ArrayDeque<>();

        int i = 0;
        int j = 0;
        int length = pushed.length;
        if (length == 0) {
            return true;
        }
        while (j < popped.length) {
            if (deque.isEmpty()) {
                // 如果栈为空，则说明需要安排新元素进栈
                deque.addLast(pushed[i]);
                i++;
                continue;
            }

            Integer top = deque.getLast();
            int nextToPop = popped[j];
            if (top == nextToPop) {
                // 栈顶元素是否为符合出栈顺序，符合则弹出
                deque.removeLast();
                j++;
            } else {
                // 这里说明下一个要pop的不符合popped的顺序，那么就去判断pushed中有没有剩下的元素
                if (i == length) {
                    // 没有待进栈的元素，则这个出栈顺序和进栈顺序不匹配
                    return false;
                }

                while (i < length) {
                    // 如果有的话，那么进栈，直到栈顶元素是下一个要pop的元素
                    if (deque.getLast() == popped[j]) {
                        break;
                    }
                    deque.addLast(pushed[i]);
                    i++;
                }
            }
        }

        return true;
    }
}
