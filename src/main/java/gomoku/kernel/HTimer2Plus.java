package gomoku.kernel;

/**
 * @author D07114915(原作者)
 * @stu 黄逸航(些许改进)
 * @description 支持正计时和倒计时的闹钟。闹钟的所有常见操作已内置。闹钟食用方法参见test()方法。
 */
public final class HTimer2Plus extends Thread {
    enum TYPE{
        UP,
        DOWN
    }

    private Thread thread = null; // 线程
    private String min, sec, ns, sspTime; // 分别存储各个时间
    private long time = 0; // 存储毫秒数
    private int doubleSpeed = 1; // 倍速，默认为1
    private TYPE type = TYPE.DOWN; // 默认为倒计时

    public HTimer2Plus() {
        min = ("00"); // 初始化
        sec = ("00"); // 初始化
        ns = ("00"); // 初始化
        sspTime = ("00:00:00"); // 初始化
    }

    // 用法说明
    private void test() {
        // 创建一个时钟（必须）
        HTimer2Plus hTimer2Plus = new HTimer2Plus();
        // 【自带方法】：下面语句自由组合。
        // 1. 让时钟开始计时（默认为倒计时）
        hTimer2Plus.startTimer(60 * 1000); // 括号内是毫秒数，60代表秒数
        // 2. 让闹钟停下来，并且时间归0
        hTimer2Plus.stopTimer(0);
        // 3. 让闹钟停下来（停下来的时候时间是多少就是多少）
        hTimer2Plus.stopTimer(hTimer2Plus.getTime());
        // 4. 获取闹钟的时间
        long time = hTimer2Plus.getTime(); // 返回毫秒数
        // 5. 获取闹钟的文本
        String timeText = hTimer2Plus.getSspTime(); // 返回String,格式形如00:00:00。 获取其他格式，请通过4获取时间后自行处理。
        // 6. 设定闹钟的倍速
        int speed = 2;
        hTimer2Plus.setDoubleSpeed(speed);  // 设定为2倍速（整数倍数，不支持负数）。如果想要实现1.25倍速之类的，只需要将doubleSpeed的类型修改为double。
        // 7. 设定闹钟是正计时还是倒计时
        hTimer2Plus.setType(TYPE.UP); // 设定为正计时
        hTimer2Plus.setType(TYPE.DOWN); // 设定为倒计时
        // 注意：此设置实时同步。即：如果时钟是正在倒计时，突然使用此方法，将会让闹钟突然变成正计时。

        // 注意：此闹钟没有使用支持绑定的属性。如果想要闹钟的时间变化时自动更新某个组件的文字，请将sspTime属性修改为支持绑定的属性。
    }

    // 让时钟开始倒计时
    public void startTimer(long time) {
        this.time = time; // 设定时钟的时间
        thread = new Thread(this); // 创建线程
        thread.setPriority(Thread.MIN_PRIORITY); // 设定优先级
        thread.start(); // 开始跑任务
    }

    // 让时钟停下来
    public void stopTimer(long time) {
        // 如果停下来之前是在跑的，那么就让闹钟停下来
        if (thread != null) {
            thread.interrupt(); // 线程终止
        }
        this.time = time; // 设定时间（从参数中读入）
        setTime(time); // 更新时钟的文本
    }

    public void setTime(long time) {
        this.time = time; // 设定时钟的时间
        // 计算秒数
        int seconds = (int) ((time / 1000) % 60);
        if (seconds <= 9) {
            this.sec = ("0" + seconds);
        } else {
            this.sec = ("" + seconds);
        }
        // 计算分钟数
        int minutes = ((int) ((time / 1000) / 60));
        if (minutes <= 9) {
            this.min = ("0" + minutes);
        } else {
            this.min = ("" + minutes);
        }
        // 计算秒数后面那个数
        int ns = ((int) ((time % 1000) / 10));
        if (ns <= 9) {
            this.ns = ("0" + ns);
        } else {
            this.ns = ("" + ns);
        }
        // 更新一下时钟的文本
        this.sspTime = (this.min + ":" + this.sec + ":" + this.ns);
    }

    // 设定时钟的速度 1是正常
    public void setDoubleSpeed(int doubleSpeed) {
        this.doubleSpeed = doubleSpeed;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    // 获取时钟的毫秒数
    public long getTime() {
        return time;
    }

    // 获取时钟的文本
    public String getSspTime() {
        return sspTime;
    }

    // 设定这个时钟的每一次间隔
    private static final int SLEEPTIME = 65; // 使用const避免Magic Number

    // 这个时钟跑起来的时候要做的事情
    @Override
    public void run() {
        try {
            // 如果时钟没有被人停下来
            while (!thread.isInterrupted()) {
                // 如果时钟的时间<=0了，那么时钟别走了。不让要变负数了
                if (time <= 0 && this.type == TYPE.DOWN) {
                    // 设定时钟毫秒数为0（归位）
                    setTime(0);
                    // 时钟把自己停下来
                    thread.interrupt();
                    // 跳出while
                    break;
                }
                // 更新时钟文本
                setTime(this.time);
                sleep(SLEEPTIME);
                // 让时钟的时间减少，乘上倍数就能实现倍速。
                if (this.type == TYPE.UP){
                    // 正计时
                    this.time = this.time + (SLEEPTIME * doubleSpeed);
                }else{
                    // 倒计时
                    this.time = this.time - (SLEEPTIME * doubleSpeed);
                }
            }
        } catch (Exception e) {
        }

    }
}//end of class
