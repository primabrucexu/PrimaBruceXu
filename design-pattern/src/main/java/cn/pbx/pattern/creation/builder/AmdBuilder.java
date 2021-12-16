package cn.pbx.pattern.creation.builder;

import cn.pbx.pattern.creation.builder.product.*;

/**
 * @author BruceXu
 * @date 2021/9/27
 */
public class AmdBuilder extends AbstractPCBuilder {
    @Override
    public void buildCPU() {
        CPU cpu = new CPU();
        cpu.setBrand("amd").setModel("Ryzen 5800x").setCore(8).setThread(16).setTDP(160).setFrequency(4.0).setBoost(4.8);
        pc.setCpu(cpu);
    }

    @Override
    public void buildGPU() {
        GPU gpu = new GPU();
        gpu.setBrand("amd").setModel("6900xt").setCUDA(5120).setMemoryType("GDDR6").setGraphicMemory(16).setRTX(false).setTDP(300);
        pc.setGpu(gpu);
    }

    @Override
    public void buildMotherBoard() {
        MotherBoard motherBoard = new MotherBoard();
        motherBoard.setChipset("X570");
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
