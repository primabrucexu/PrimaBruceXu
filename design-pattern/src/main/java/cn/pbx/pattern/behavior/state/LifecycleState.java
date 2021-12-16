package cn.pbx.pattern.behavior.state;

/**
 * @author BruceXu
 * @date 2021/9/30
 */
public abstract class LifecycleState {

    protected String state;

    public abstract void update(Context context);

    public abstract boolean isClose();

    static class Close extends LifecycleState {

        public Close() {
            this.state = "close";
        }

        @Override
        public void update(Context context) {
            System.out.println(state);
        }

        @Override
        public boolean isClose() {
            return true;
        }
    }

    static class Init extends LifecycleState {
        public Init() {
            this.state = "init";
        }

        @Override
        public void update(Context context) {
            System.out.println(state);
        }

        @Override
        public boolean isClose() {
            return false;
        }
    }

    static class Running extends LifecycleState {
        public Running() {
            this.state = "running";
        }

        @Override
        public void update(Context context) {
            System.out.println(state);
        }

        @Override
        public boolean isClose() {
            return false;
        }
    }

    static class Pause extends LifecycleState {
        public Pause() {
            this.state = "stop";
        }

        @Override
        public void update(Context context) {
            System.out.println(state);
        }

        @Override
        public boolean isClose() {
            return false;
        }
    }

}


