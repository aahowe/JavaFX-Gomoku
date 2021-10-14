package gomoku.kernel;

import java.io.Serializable;
import java.util.Stack;

public class Save implements Serializable {
    private int[] myWin, aiWin;
    private Stack<Step> steps;
    private int win;
    public int[][] chessBoard;
    private long time = 0;
    private String saveName;

    public Save() {
        this.myWin = new int[1000];
        this.aiWin = new int[1000];
        this.steps = new Stack<>();
        this.win = 0;
        this.chessBoard = new int[15][15];
        //初始化棋盘
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                chessBoard[i][j] = 0;
            }
        }
        //初始化win
        for (int i = 0; i < 1000; i++) {
            myWin[i] = 0;
            aiWin[i] = 0;
        }
    }


    public int[] getMyWin() {
        return myWin;
    }

    public int[] getAiWin() {
        return aiWin;
    }

    public Stack<Step> getSteps() {
        return steps;
    }

    public int getWin() {
        return win;
    }

    public int[][] getChessBoard() {
        return chessBoard;
    }

    public long getTime() {
        return time;
    }

    public String getSaveName() {
        return saveName;
    }

    public void setMyWin(int[] myWin) {
        this.myWin = myWin;
    }

    public void setAiWin(int[] aiWin) {
        this.aiWin = aiWin;
    }

    public void setSteps(Stack<Step> steps) {
        this.steps = steps;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public void setChessBoard(int[][] chessBoard) {
        this.chessBoard = chessBoard;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setSaveName(String saveName) {
        this.saveName = saveName;
    }
}
