package cn.pbx.pattern.structure.proxy;

/**
 * @author BruceXu
 * @date 2021/6/1
 */
public class ProjectManager implements Programme {

    private Coder coder;

    public ProjectManager() {
    }

    @Override
    public void code(String type) {
        if (coder == null) {
            coder = new Coder();
        }
        coder.code(type);
    }
}
