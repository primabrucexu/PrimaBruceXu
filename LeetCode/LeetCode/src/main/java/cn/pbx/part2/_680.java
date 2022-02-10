package cn.pbx.part2;

/**
 * @author BruceXu
 * @date 2020/11/11
 */
public class _680 {

    public static void main(String[] args) {
        String s = "abca";
        System.out.println(validPalindrome(s));
    }

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
                return flag1 || flag2;
            }
        }
        return true;
    }
}


