package cn.pbx.pattern.creation.builder.product;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author BruceXu
 * @date 2021/9/27
 */
@Data
@Accessors(chain = true)
public class CPU {

    private String brand;
    private String model;
    private Integer core;
    private Integer thread;
    private Double frequency;
    private Double boost;
    private Integer TDP;

}
