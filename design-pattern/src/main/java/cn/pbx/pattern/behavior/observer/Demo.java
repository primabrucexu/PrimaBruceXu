package cn.pbx.pattern.behavior.observer;

import java.math.BigDecimal;

/**
 * @author BruceXu
 * @date 2021/9/28
 */
public class Demo {
    public static void main(String[] args) {
        BitCoin bitCoin = new BitCoin();
        bitCoin.add(new Player());
        bitCoin.add(new Miner());
        bitCoin.add(new Scalper());

        bitCoin.change(new BigDecimal(10));
        System.out.println("==========================");
        bitCoin.change(new BigDecimal(-10));

    }
}
