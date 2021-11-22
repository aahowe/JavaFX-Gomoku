package gomoku.kernel;

import gomoku.ui.frameworks.ContentPane;
import gomoku.ui.frameworks.dialogs.GameDisplay;
import gomoku.ui.nodes.StepListView;
import javafx.application.Platform;

/**
 * @stu 黄逸航(原作者)
 * @description 观察者。用来看着时钟。时钟到零了，由观察者下棋子并且更新时钟时间
 */
public class PlayWatcher2Plus extends Thread {
    private Thread thread = null; //线程
    private HTimer2Plus hTimer2Plus; // 观察者要持有一个闹钟，不然他看谁？

    // 构造函数（传入一个要被观察的时钟）
    public PlayWatcher2Plus(HTimer2Plus hTimer2Plus) {
        this.hTimer2Plus = hTimer2Plus;
    }

    // 用法说明
    private void test() {
        // 必须：创建一个观察者
        HTimer2Plus hTimer2Plus = new HTimer2Plus(); // 创建一个时钟，这个时钟要传给观察者
        PlayWatcher2Plus playWatcher2Plus = new PlayWatcher2Plus(hTimer2Plus); // 创建观察者
        // 用法
        // 1.让观察者看着时钟
        playWatcher2Plus.watch();
        // 2.观察者别看了
        playWatcher2Plus.pause();
    }

    // 开始盯着时钟看
    public void watch() {
        // 实例化线程
        thread = new Thread(this);
        thread.setPriority(Thread.MIN_PRIORITY);
        // 开始盯着看
        thread.start();
    }

    // 不要盯着看
    public void pause() {
        // 如果线程存在，才能停下来。
        if (thread != null) {
            thread.interrupt();
        }
    }

    @Override
    public void run() {
        int[] k = {-1};
        try {
            // 如果观察者没有被人停下来
            while (!thread.isInterrupted()) {
                //下棋回放
                GameDisplay gameDisplay = GameDisplay.getSelf();
                Controller controller = Controller.getSelf();
                gameDisplay.setGo(new Gomoku(controller.getSave()));
                StepListView list = gameDisplay.getOperationBar().getListView();
                gameDisplay.getOperationBar().getOperationButtons()[0].setDisable(true);
                GameDisplay.getSelf().getOperationBar().getAISwitch().setDisable(true);
                GameDisplay.getSelf().getOperationBar().getOperationButtons()[2].setDisable(true);
                ContentPane.getSelf().getMenuBar().getButton(2).setDisable(true);
                ContentPane.getSelf().getMenuBar().getButton(4).setDisable(true);
                if (hTimer2Plus.getTime() <= 0) {
                    Platform.runLater(() -> {
                        Step step = controller.getSave().getSteps().get(k[0]);
                        int i = step.getI();
                        int j = step.getJ();
                        int type = controller.getSave().getChessBoard()[i][j];
                        gameDisplay.getPiece(i, j).push(type, controller.getSave().getSteps().get(k[0]).getNum() + 1);
                        list.getItems().add("[" + Controller.getTypeName(type) + "]" + " " + Controller.getPieceName(i, j) + "  思考用时:" + (float) step.getTime() / 1000 + " 秒");
                        list.getSelectionModel().select(list.getItems().size() - 1);
                    });
                    k[0]++;
                    if (k[0] >= controller.getSave().getSteps().size() - 1) {
                        gameDisplay.getOperationBar().getOperationButtons()[0].setDisable(false);
                        gameDisplay.getOperationBar().getOperationButtons()[2].setDisable(false);
                        GameDisplay.getSelf().getOperationBar().getAISwitch().setDisable(false);
                        ContentPane.getSelf().getMenuBar().getButton(2).setDisable(false);
                        ContentPane.getSelf().getMenuBar().getButton(4).setDisable(false);
                        thread.interrupt(); // 观察者不要盯着看了，因为没有棋子要下了
                    } else {
                        // 还有下一个棋子的话，就让闹钟重新开始倒计时（时间为下一个棋子的落子时间）
                        hTimer2Plus.startTimer(controller.getSave().getSteps().get(k[0]+1).getTime());
                    }
                }
                // 观察者休息一下，不要老是盯着人家看！休息完了，再看看闹钟到点了吗
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}//end of class
