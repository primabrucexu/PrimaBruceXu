package cn.pbx.pattern.structure.facade;

/**
 * @author BruceXu
 * @date 2021/6/2
 */
public class ResourcesManager {

    private final DBPipe dbPipe;
    private final IOPipe ioPipe;
    private final NetworkPipe networkPipe;

    public ResourcesManager() {
        dbPipe = new DBPipe();
        ioPipe = new IOPipe();
        networkPipe = new NetworkPipe();
    }

    public void openPipe() {
        dbPipe.open();
        ioPipe.open();
        networkPipe.open();
    }

    public void close() {
        dbPipe.close();
        ioPipe.close();
        networkPipe.close();
    }

}
