package cn.pbx.Algorithm.easy;

import java.util.Stack;

/**
 * @author BruceXu
 * @date 2020/9/26
 */
public class Solution_20_isValid {
    public static void main(String[] args) {

    }

    public boolean isValid(String s) {
        if (s.length() % 2 != 0) {
            return false;
        }
        Stack<Character> stack = new Stack<>();
        char[] array = s.toCharArray();
        for (char c : array) {
            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);
            } else {
                // 在匹配之前，判断栈是否为空，如果为空，显然不能匹配
                if (stack.empty()) {
                    return false;
                }
                char temp = stack.pop();
                if (temp == '(' && c != ')') {
                    return false;
                }
                if (temp == '[' && c != ']') {
                    return false;
                }
                if (temp == '{' && c != '}') {
                    return false;
                }
            }
        }
        return stack.empty();
    }
}
