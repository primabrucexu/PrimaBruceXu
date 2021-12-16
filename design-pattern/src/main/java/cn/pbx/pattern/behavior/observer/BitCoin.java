package cn.pbx.pattern.behavior.observer;

import java.math.BigDecimal;

/**
 * @author BruceXu
 * @date 2021/9/28
 */
public class BitCoin extends AbstractSubject {

    public BitCoin() {
        super();
    }

    @Override
    public void change(BigDecimal change) {
        int i = change.compareTo(BigDecimal.ZERO);
        if (i > 0) {
            System.out.println("比特币涨价");
            higher();
        } else if (i < 0) {
            System.out.println("比特币跌了");
            lower();
        } else {
            System.out.println("比特币无涨幅");
        }
    }


}
