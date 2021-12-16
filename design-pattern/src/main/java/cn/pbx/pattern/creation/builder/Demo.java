package cn.pbx.pattern.creation.builder;

import cn.pbx.pattern.creation.builder.product.PC;

/**
 * @author BruceXu
 * @date 2021/9/27
 */
public class Demo {
    public static void main(String[] args) {
        AmdBuilder amdBuilder = new AmdBuilder();
        PC amd = amdBuilder.build();

        IntelBuilder intelBuilder = new IntelBuilder();
        PC intel = intelBuilder.build();

        System.out.println(amd);
        System.out.println(intel);

    }
}
