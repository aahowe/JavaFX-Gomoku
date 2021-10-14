package gomoku.kernel;

import java.io.Serializable;

public class Step implements Serializable {
    private int i;
    private int j;
    private long time;

    public Step(int i, int j, long time) {
        this.i = i;
        this.j = j;
        this.time = time;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public long getTime() {
        return time;
    }
}
