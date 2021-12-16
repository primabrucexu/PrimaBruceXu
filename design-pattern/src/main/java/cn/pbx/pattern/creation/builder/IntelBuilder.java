package cn.pbx.pattern.creation.builder;

import cn.pbx.pattern.creation.builder.product.*;

/**
 * @author BruceXu
 * @date 2021/9/27
 */
public class IntelBuilder extends AbstractPCBuilder {
    @Override
    public void buildCPU() {
        CPU cpu = new CPU();
        cpu.setBrand("Intel").setModel("i9-11900K").setCore(8).setThread(16).setTDP(250).setFrequency(4.2).setBoost(5.1);
        pc.setCpu(cpu);
    }

    @Override
    public void buildGPU() {
        GPU gpu = new GPU();
        gpu.setBrand("Nvidia").setModel("3080TI").setCUDA(10496).setMemoryType("GDDR6X").setGraphicMemory(12).setRTX(true).setTDP(320);
        pc.setGpu(gpu);
    }

    @Override
    public void buildMotherBoard() {
        MotherBoard motherBoard = new MotherBoard();
        motherBoard.setChipset("Z590");
        pc.setMotherBoard(motherBoard);
    }

    @Override
    public void buildMemory() {
        Memory memory = new Memory();
        memory.setSize(32).setUnit("GB");
        pc.setMemory(memory);
    }

    @Override
    public void buildPowerSupply() {
        PowerSupply powerSupply = new PowerSupply();
        powerSupply.setPower(1000).setLevel("GOLD");
        pc.setPowerSupply(powerSupply);
    }
}
