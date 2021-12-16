package cn.pbx.pattern.structure.facade;

/**
 * @author BruceXu
 * @date 2021/6/2
 */
public class NetworkPipe implements Pipe {
    @Override
    public void open() {
        System.out.println("打开网络通道");
    }

    @Override
    public void close() {
        System.out.println("关闭网络通道");
    }
}
