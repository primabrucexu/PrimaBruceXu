package cn.pbx.pattern.structure.facade;

/**
 * @author BruceXu
 * @date 2021/6/2
 */
public class Demo {
    public static void main(String[] args) {
        ResourcesManager resourcesManager = new ResourcesManager();
        resourcesManager.openPipe();
        resourcesManager.close();
    }
}
