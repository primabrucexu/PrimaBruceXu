package cn.pbx.pattern.creation.builder;

import cn.pbx.pattern.creation.builder.product.PC;

/**
 * @author BruceXu
 * @date 2021/9/27
 */
public abstract class AbstractPCBuilder implements PCBuilder {

    protected PC pc;

    public AbstractPCBuilder() {
        this.pc = new PC();
    }

    public abstract void buildCPU();

    public abstract void buildGPU();

    public abstract void buildMotherBoard();

    public abstract void buildMemory();

    public abstract void buildPowerSupply();

    @Override
    public PC build() {
        buildCPU();
        buildGPU();
        buildMotherBoard();
        buildMemory();
        buildPowerSupply();
        return pc;
    }
}
