package gomoku.kernel;

import gomoku.database.Operate;
import gomoku.ui.frameworks.ContentPane;
import gomoku.ui.frameworks.dialogs.GameDisplay;
import gomoku.ui.nodes.StepListView;
import gomoku.ui.nodes.TimeDisplay;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Controller {
    private Save save;
    private GameDisplay gameDisplay;
    private int peekIndex = 0;
    private ContentPane contentPane;
    private String currentSave = "";
    private static Controller self;

    public Controller(GameDisplay gameDisplay, Save save) {
        contentPane = ContentPane.getSelf();
        self = this;
        this.gameDisplay = gameDisplay;
        this.save = save;
    }

    public static Controller getSelf() {
        return self;
    }
    //返回自身

    public void push(int i, int j) {
        gameDisplay.getGo().setEnd(System.currentTimeMillis());
        long time = gameDisplay.getGo().getTime();
        if (save.getSteps().empty())
            gameDisplay.getOperationBar().getTimeDisplay().start();
        cutToSelected();
        int currentType = getCurrentType();
        StepListView list = gameDisplay.getOperationBar().getListView();
        save.getChessBoard()[i][j] = currentType;
        save.getSteps().push(new Step(i, j, time));
        if (currentType == PieceType.BLACK) {
            gameDisplay.getGo().iGo(i, j, currentType);
            gameDisplay.getPiece(i, j).push(currentType);
            list.getItems().add("[" + getTypeName(currentType) + "]" + " " + getPieceName(i, j));
            list.getSelectionModel().select(list.getItems().size() - 1);
            if (gameDisplay.getGo().isAi()) {
                int[] ai = gameDisplay.getGo().aiGo(PieceType.WHITE);
                save.getSteps().push(new Step(ai[0], ai[1], time));
                gameDisplay.getPiece(ai[0], ai[1]).push(PieceType.WHITE);
                list.getItems().add("[" + getTypeName(currentType) + "]" + " " + getPieceName(i, j));
                list.getSelectionModel().select(list.getItems().size() - 1);
                gameDisplay.getGo().setStart(System.currentTimeMillis());
            }
        } else if (currentType == PieceType.WHITE) {
            gameDisplay.getGo().iGo1(i, j, currentType);
            gameDisplay.getPiece(i, j).push(currentType);
            list.getItems().add("[" + getTypeName(currentType) + "]" + " " + getPieceName(i, j));
            list.getSelectionModel().select(list.getItems().size() - 1);
        }
        if (gameDisplay.getGo().getWin() != PieceType.EMPTY) {
            if (gameDisplay.getGo().getWin() == PieceType.BLACK) {
                gameDisplay.getOperationBar().getTimeDisplay().stop();
                contentPane.showTips("黑棋获胜！");
            } else if (gameDisplay.getGo().getWin() == PieceType.WHITE) {
                gameDisplay.getOperationBar().getTimeDisplay().stop();
                contentPane.showTips("白棋获胜！");
            }
            gameDisplay.setClosed(true);
        }
        gameDisplay.getGo().setStart(System.currentTimeMillis());

        for (Step s : save.getSteps()) {
            System.out.println(s.getTime()+" ms");
        }
    }
    //下棋

    public void restart() {
        gameDisplay.setGo(new Gomoku(new Save()));
        gameDisplay.getOperationBar().getListView().getSelectionModel().select(0);
        cutToSelected();
        gameDisplay.getOperationBar().getStepButtons()[1].setDisable(true);
        gameDisplay.setClosed(false);
        gameDisplay.getOperationBar().getTimeDisplay().stop();
        gameDisplay.getOperationBar().getTimeDisplay().clear();
        currentSave = "";
        updateCountDisplay();
    }
    //重新开始游戏

    public void cutToSelected() {
        int n = save.getSteps().size();
        if (peekIndex >= n - 1) {
            return;
        }
        for (int k = n - 1; k > peekIndex; k--) {
            StepListView list = gameDisplay.getOperationBar().getListView();
            if (list.getItems().size() >= k + 2)
                list.getItems().remove(k + 1);
            int i = save.getSteps().get(k).getI();
            int j = save.getSteps().get(k).getJ();
            save.getChessBoard()[i][j] = PieceType.EMPTY;
            save.getSteps().pop();
        }
    }
    //悔棋后如果用户直接下棋，则将悔棋所在步骤与悔棋前步骤之间的所有棋子删除

    public void peek(int index) {
        int n = save.getSteps().size();
        updateCountDisplay();
        if (index == peekIndex || index >= n || index < -1) {
            return;
        } else if (index > peekIndex) {
            for (int k = peekIndex + 1; k <= index; k++) {
                int i = save.getSteps().get(k).getI();
                int j = save.getSteps().get(k).getJ();
                int type = getType(k);
                gameDisplay.getPiece(i, j).push(type);
            }
        } else {
            for (int k = peekIndex; k > index; k--) {
                int i = save.getSteps().get(k).getI();
                int j = save.getSteps().get(k).getJ();
                gameDisplay.getPiece(i, j).pop();
            }
        }
        peekIndex = index;
    }
    //将界面数据临时退回到第index步

    public void saveGame(String name) {
        save.setTime(gameDisplay.getOperationBar().getTimeDisplay().getTime());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        save.setSaveName(name + "   " + formatter.format(date));
        Operate.addSave(contentPane.getUsername(), save, save.getSaveName());
    }
    //保存存档，name指定存档名（不包含拓展名）

    public void loadGame(String name) {
        restart();
        for (Save s : contentPane.getSaveList()) {
            if (Objects.equals(s.getSaveName(), name)) {
                gameDisplay.setSave(s);
                save = s;
                System.out.println("存档载入成功");
            }
        }
        sync();
    }
    //载入存档，name指定存档名（不包含拓展名）

    public void sync() {
        StepListView list = gameDisplay.getOperationBar().getListView();
        if (save.getWin() != 0) {
            gameDisplay.setClosed(true);
        }
        for (int k = 0; k < save.getSteps().size(); k++) {
            Step step = save.getSteps().get(k);
            int i = step.getI();
            int j = step.getJ();
            int type = save.getChessBoard()[i][j];
            gameDisplay.getPiece(i, j).push(type);
            list.getItems().add("[" + getTypeName(type) + "]" + " " + getPieceName(i, j));
        }
        list.getSelectionModel().select(list.getItems().size() - 1);
        TimeDisplay timeDisplay = gameDisplay.getOperationBar().getTimeDisplay();
        timeDisplay.setTime(save.getTime());
        if (save.getWin() == 0 && save.getSteps().size() > 0) {
            timeDisplay.start();
        }
    }
    //载入存档后，将界面与数据进行同步

    public int getType(int count) {
        return (count % 2 == 0) ? (PieceType.BLACK) : (PieceType.WHITE);
    }

    public int getCurrentType() {
        return this.getType(save.getSteps().size());
    }

    public static String getPieceName(int i, int j) {
        return (String.valueOf((char) ('A' + j)) + (i + 1));
    }

    public String getTypeName() {
        return getTypeName(getCurrentType());
    }

    public static String getTypeName(int type) {
        if (type == PieceType.BLACK) {
            return "黑方";
        } else if (type == PieceType.WHITE) {
            return "白方";
        }
        return "无";
    }

    public void updateCountDisplay(int type) {
        int size = save.getSteps().size();
        switch (type) {
            case PieceType.BLACK:
                gameDisplay.getOperationBar().getBlackCountDisplay().setText(String.valueOf(size / 2 + size % 2));
                break;
            case PieceType.WHITE:
                gameDisplay.getOperationBar().getWhiteCountDisplay().setText(String.valueOf(size / 2));
                break;
            default:
                break;
        }
    }

    public void updateCountDisplay() {
        this.updateCountDisplay(PieceType.BLACK);
        this.updateCountDisplay(PieceType.WHITE);
    }

    public String getCurrentSave() {
        return this.currentSave;
    }

    public void setCurrentSave(String save) {
        this.currentSave = save;
    }

    public Save getSave() {
        return save;
    }
}
