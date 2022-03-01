package cn.pbx.offer;

import java.util.HashMap;
import java.util.Map;

/**
 * 自动机总是可以解决这类字符串是否满足某些特定条件的问题
 * <p>
 * 数值按顺序可以分为如下几个部分:
 * 1.（可选）1个符号，'+' 或 '-'
 * 2. 若干个数字
 * 3.（可选）小数点
 * 4. 若干个数字
 * 5.（可选）1个符号，'E' 或者 'e'
 * 6.（可选）1个符号，'+' 或 '-'
 * 7. 若干个数字
 *
 * @author BruceXu
 * @date 2022/2/14
 */
public class offer_20 {

    public static void main(String[] args) {
        offer_20 demo = new offer_20();
        String s = "1 ";
        System.out.println(demo.isNumber(s));
    }

    public boolean isNumber(String s) {
        // 特例处理
        if (s.length() == 1) {
            return CharType.get(s.charAt(0)).equals(CharType.NUMBER);
        }

        // 初始状态集合
        Map<CharType, State> initMap = new HashMap<>();
        initMap.put(CharType.SPACE, State.INIT_SPACE);
        initMap.put(CharType.SIGNAL, State.SIGNAL_BEFORE_E);
        initMap.put(CharType.POINT, State.POINT_NO_INT_LEFT);
        initMap.put(CharType.NUMBER, State.INT_BEFORE_E);

        // 获取第一个字符所处的状态
        State state = initMap.get(CharType.get(s.charAt(0)));
        if (state == null) {
            return false;
        }

        for (int i = 1; i < s.length(); i++) {
            char c = s.charAt(i);
            CharType charType = CharType.get(c);
            if (charType.equals(CharType.ILLEGAL)) {
                return false;
            }
            state = State.trans(state, charType);
            if (state == null) {
                return false;
            }
        }

        switch (state) {
            case INT_BEFORE_E:
            case INT_AFTER_E:
            case POINT_RIGHT:
            case POINT_WITH_INT_LEFT:
            case LAST_SPACE:
                return true;
            default:
                return false;
        }

    }

    enum State {
        /**
         * 开头的若干空格
         */
        INIT_SPACE,
        /**
         * 整数或小数部分的符号
         */
        SIGNAL_BEFORE_E,

        /**
         * 整数部分
         */
        INT_BEFORE_E,

        /**
         * 左侧无整数数的小数点
         */
        POINT_NO_INT_LEFT,

        /**
         * 左侧有整数的小数点
         */
        POINT_WITH_INT_LEFT,

        /**
         * 小数点右侧的小数部分
         */
        POINT_RIGHT,

        /**
         * 指数符号
         */
        E,

        /**
         * 指数的符号
         */
        SIGNAL_AFTER_E,

        /**
         * 指数部分的整数
         */
        INT_AFTER_E,

        /**
         * 剩下的空格
         */
        LAST_SPACE;

        static final Map<State, Map<CharType, State>> TRANS_MAP = new HashMap<>();

        static {
            TRANS_MAP.put(INIT_SPACE, new HashMap() {{
                put(CharType.SPACE, INIT_SPACE);
                put(CharType.SIGNAL, SIGNAL_BEFORE_E);
                put(CharType.POINT, POINT_NO_INT_LEFT);
                put(CharType.NUMBER, INT_BEFORE_E);
            }});
            TRANS_MAP.put(SIGNAL_BEFORE_E, new HashMap() {{
                put(CharType.NUMBER, INT_BEFORE_E);
                put(CharType.POINT, POINT_NO_INT_LEFT);
            }});
            TRANS_MAP.put(INT_BEFORE_E, new HashMap() {{
                put(CharType.NUMBER, INT_BEFORE_E);
                put(CharType.POINT, POINT_WITH_INT_LEFT);
                put(CharType.E, E);
                put(CharType.SPACE, LAST_SPACE);
            }});
            TRANS_MAP.put(POINT_NO_INT_LEFT, new HashMap() {{
                put(CharType.NUMBER, POINT_RIGHT);
            }});
            TRANS_MAP.put(POINT_WITH_INT_LEFT, new HashMap() {{
                put(CharType.NUMBER, POINT_RIGHT);
                put(CharType.E, E);
                put(CharType.SPACE, LAST_SPACE);
            }});
            TRANS_MAP.put(POINT_RIGHT, new HashMap() {{
                put(CharType.NUMBER, POINT_RIGHT);
                put(CharType.E, E);
                put(CharType.SPACE, LAST_SPACE);
            }});
            TRANS_MAP.put(E, new HashMap() {{
                put(CharType.SIGNAL, SIGNAL_AFTER_E);
                put(CharType.NUMBER, INT_AFTER_E);
            }});
            TRANS_MAP.put(SIGNAL_AFTER_E, new HashMap() {{
                put(CharType.NUMBER, INT_AFTER_E);
            }});
            TRANS_MAP.put(INT_AFTER_E, new HashMap() {{
                put(CharType.NUMBER, INT_AFTER_E);
                put(CharType.SPACE, LAST_SPACE);
            }});
            TRANS_MAP.put(LAST_SPACE, new HashMap() {{
                put(CharType.SPACE, LAST_SPACE);
            }});
        }

        static State trans(State nowState, CharType nextCharType) {
            Map<CharType, State> stateMap = TRANS_MAP.get(nowState);
            return stateMap.get(nextCharType);
        }

    }

    enum CharType {
        /**
         * 小数点
         */
        POINT,

        /**
         * 数字
         */
        NUMBER,

        /**
         * E或者e
         */
        E,

        /**
         * 空格
         */
        SPACE,

        /**
         * 符号
         */
        SIGNAL,

        /**
         * 退出自动
         */
        ILLEGAL;

        static CharType get(char c) {
            if (c >= '0' && c <= '9') {
                return NUMBER;
            } else if (c == '.') {
                return POINT;
            } else if (c == 'e' || c == 'E') {
                return E;
            } else if (c == ' ') {
                return SPACE;
            } else if (c == '-' || c == '+') {
                return SIGNAL;
            } else {
                return ILLEGAL;
            }
        }

    }

}
