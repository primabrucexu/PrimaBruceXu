package cn.pbx.pattern.behavior.state;

/**
 * @author BruceXu
 * @date 2021/9/30
 */
public class Context {

    private LifecycleState state;

    public Context() {
        state = new LifecycleState.Close();
    }

    public void start() {
        state = new LifecycleState.Init();
    }

    public void update(int delta) {
        if (state.isClose()) {
            System.out.println("Service is closed, reject update");
            return;
        }

        if (delta < 0) {
            state = new LifecycleState.Close();
        } else if (delta == 0) {
            state = new LifecycleState.Pause();
        } else {
            state = new LifecycleState.Running();
        }
        state.update(this);
    }

}
