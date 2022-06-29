package cn.pbx.week.single.week289;

/**
 * @author Bruce Xu
 * @date 2022/6/29
 */
public class No2243 {
    public static void main(String[] args) {
        String s = "12345";
        StringBuilder sb = new StringBuilder(s);
        System.out.println(sb.replace(0, 3, "x"));
    }

    public String digitSum(String s, int k) {
        StringBuilder sb = new StringBuilder(s);
        while (sb.length() > k) {
            StringBuilder old = new StringBuilder(sb);
            int start = 0;
            int end = k;
            sb = new StringBuilder();
            while (end < old.length()) {
                sb.append(sumString(old.substring(start, end)));
                start += k;
                end += k;
            }
            sb.append(sumString(old.substring(start)));
        }
        return sb.toString();
    }

    private String sumString(String s) {
        int sum = 0;
        for (int i = 0; i < s.length(); i++) {
            sum += s.charAt(i) - '0';
        }
        return String.valueOf(sum);
    }

}
