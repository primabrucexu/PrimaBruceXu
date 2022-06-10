package cn.pbx.offer.version1;

import java.util.HashSet;
import java.util.Set;

/**
 * @author BruceXu
 * @date 2022/6/10
 */
public class offer_38 {
    public String[] permutation(String s) {
        // 不可重复的话，用set来去重
        Set<String> set = new HashSet<>();
        boolean[] flag = new boolean[s.length()];

        full(new StringBuilder(s.length()), s, flag, set);
        return set.toArray(new String[0]);
    }

    private void full(StringBuilder sb, String s, boolean[] flag, Set<String> res) {
        if (sb.length() == s.length()) {
            res.add(sb.toString());
            return;
        }

        for (int i = 0; i < flag.length; i++) {
            if (!flag[i]) {
                sb.append(s.charAt(i));
                flag[i] = true;
                full(sb, s, flag, res);
                sb.deleteCharAt(sb.length() - 1);
                flag[i] = false;
            }
        }
    }

}
