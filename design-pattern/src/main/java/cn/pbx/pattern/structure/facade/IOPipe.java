package cn.pbx.pattern.structure.facade;

/**
 * @author BruceXu
 * @date 2021/6/2
 */
public class IOPipe implements Pipe {
    @Override
    public void open() {
        System.out.println("打开io通道");
    }

    @Override
    public void close() {
        System.out.println("关闭io通道");
    }
}
