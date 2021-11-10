package gomoku.kernel;

import java.io.Serializable;

public class Step implements Serializable {
    private int i;
    private int j;
    private long time;
    private int num;

    public Step(int i, int j, long time, int num) {
        this.i = i;
        this.j = j;
        this.time = time;
        this.num = num;
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

    public int getNum() {
        return num;
    }
}
