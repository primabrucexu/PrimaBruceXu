package cn.pbx.pattern.creation.builder.product;

import lombok.Data;

/**
 * @author BruceXu
 * @date 2021/9/27
 */
@Data
public class PC {

    private CPU cpu;
    private GPU gpu;
    private MotherBoard motherBoard;
    private Memory memory;
    private PowerSupply powerSupply;


    @Override
    public String toString() {
        return "电脑配置：cpu: " + cpu + ", 显卡: " + gpu + "，主板: " + motherBoard +
                ", 内存: " + memory + ", 电源: " + powerSupply;
    }
}
