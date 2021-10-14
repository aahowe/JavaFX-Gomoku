package gomoku.kernel;

import java.util.Stack;

public class Gomoku {
    private Save save;
    private Object[][][] wins;
    private int count = 0;
    private int[] myWin, aiWin;
    private Stack<Step> steps;
    private boolean ai;
    private long start, end;
    private int win;
    public int[][] chessBoard;


    public Gomoku(Save save) {
        this.save = save;
        myWin = save.getMyWin();
        aiWin = save.getAiWin();
        steps = save.getSteps();
        win = save.getWin();
        chessBoard = save.getChessBoard();
        wins = new Object[15][15][572];
        // 阳线纵向90°的赢法
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 11; j++) {
                for (int k = 0; k < 5; k++) {
                    wins[i][j + k][count] = true;
                }
                count++;
            }
        }
        // 阳线横向0°的赢法
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 11; j++) {
                for (int k = 0; k < 5; k++) {
                    wins[j + k][i][count] = true;
                }
                count++;
            }
        }
        // 阴线斜向135°的赢法
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                for (int k = 0; k < 5; k++) {
                    wins[i + k][j + k][count] = true;
                }
                count++;
            }
        }
        // 阴线斜向45°的赢法
        for (int i = 0; i < 11; i++) {
            for (int j = 14; j > 3; j--) {
                for (int k = 0; k < 5; k++) {
                    wins[i + k][j - k][count] = true;
                }
                count++;
            }
        }
    }

    public int[] aiGo(int type) {
        // 电脑预落子的x位置
        int u = 0;
        // 电脑预落子的y位置
        int v = 0;
        // 玩家的分数
        int[][] myScore = new int[15][15];
        // 电脑的分数
        int[][] aiScore = new int[15][15];
        // 最优位置的分数
        int max = 0;
        // 返回数组
        int[] answer = new int[2];

        int[] flag = new int[1];

        //判断先手，电脑下棋
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if (chessBoard[i][j] == 0) {
                    flag[0]++;
                }
            }
        }
        if (flag[0] == 225) {
            chessBoard[7][7] = 2;
            answer[0] = 7;
            answer[1] = 7;
            //me=!me;
            return answer;
        }


        // 初始化分数的二维数组
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                myScore[i][j] = 0;
                aiScore[i][j] = 0;
            }
        }

        // 通过赢法统计数组为两个二维数组分别计分

        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if (chessBoard[i][j] == 0) {
                    for (int k = 0; k < count; k++) {
                        if (wins[i][j][k] != null && (boolean) wins[i][j][k]) {
                            if (myWin[k] == 1) {
                                myScore[i][j] += 10;
                            } else if (myWin[k] == 2) {
                                myScore[i][j] += 100;
                            } else if (myWin[k] == 3) {
                                myScore[i][j] += 1000;
                            } else if (myWin[k] == 4) {
                                myScore[i][j] += 10000;
                            }
                            if (aiWin[k] == 1) {
                                aiScore[i][j] += 11;
                            } else if (aiWin[k] == 2) {
                                aiScore[i][j] += 110;
                            } else if (aiWin[k] == 3) {
                                aiScore[i][j] += 1100;
                            } else if (aiWin[k] == 4) {
                                aiScore[i][j] += 11000;
                            }
                        }
                    }

                    // 如果玩家(i,j)处比目前最优的分数大，则落子在(i,j)处
                    if (myScore[i][j] > max) {
                        max = myScore[i][j];
                        u = i;
                        v = j;
                    } else if (myScore[i][j] == max) {
                        // 如果玩家(i,j)处和目前最优分数一样大，则比较电脑在该位置和预落子的位置的分数
                        if (aiScore[i][j] > aiScore[u][v]) {
                            u = i;
                            v = j;
                        }
                    }

                    // 如果电脑(i,j)处比目前最优的分数大，则落子在(i,j)处
                    if (aiScore[i][j] > max) {
                        max = aiScore[i][j];
                        u = i;
                        v = j;
                    } else if (aiScore[i][j] == max) {
                        // 如果电脑(i,j)处和目前最优分数一样大，则比较玩家在该位置和预落子的位置的分数
                        if (myScore[i][j] > myScore[u][v]) {
                            u = i;
                            v = j;
                        }
                    }
                }
            }
        }
        chessBoard[u][v] = type;

        for (int k = 0; k < count; k++) {
            if (wins[u][v][k] != null && (boolean) wins[u][v][k]) {
                aiWin[k]++;
                myWin[k] = 6;
                if (aiWin[k] == 5) {
                    win = 2;
                }
            }
        }
        //me = !me;
        answer[0] = u;
        answer[1] = v;
        return answer;
    }
    //ai下棋

    public void iGo(int i, int j, int type) {
        if (chessBoard[i][j] == 0) {

            // 改变棋盘信息(该位置有棋子)
            chessBoard[i][j] = type;

            // 遍历赢法统计数组
            for (int k = 0; k < count; k++) {
                if (wins[i][j][k] != null && (boolean) wins[i][j][k]) {
                    // 如果存在赢法,则玩家此赢法胜算+1(赢法为5胜取胜)
                    myWin[k]++;
                    // 如果存在赢法,则电脑此赢法胜算赋值为6(永远不等于5,永远无法在此处取胜)
                    aiWin[k] = 6;
                    // 玩家落子后,此处赢法数组凑够5,玩家取胜
                    if (myWin[k] == 5) {
                        win = 1;
                    }
                }
            }
            //me = !me;
        }
    }
    //玩家1下棋

    public void iGo1(int i, int j, int type) {
        if (chessBoard[i][j] == 0) {

            // 改变棋盘信息(该位置有棋子)
            chessBoard[i][j] = type;

            // 遍历赢法统计数组
            for (int k = 0; k < count; k++) {
                if (wins[i][j][k] != null && (boolean) wins[i][j][k]) {
                    aiWin[k]++;
                    myWin[k] = 6;
                    if (aiWin[k] == 5) {
                        win = 2;
                    }
                }
            }
            //me = !me;
        }
    }
    //玩家2下棋


    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public long getTime() {
        return end - start;
    }

    public int getWin() {
        return win;
    }
    //获取赢家

    public void setAi(boolean ai) {
        this.ai = ai;
    }
    //设置ai工作状态

    public boolean isAi() {
        return ai;
    }

    public Save getSave() {
        save.setAiWin(aiWin);
        save.setMyWin(myWin);
        save.setChessBoard(chessBoard);
        save.setSteps(steps);
        save.setWin(win);
        return save;
    }

}