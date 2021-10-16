package gomoku.ui.frameworks.dialogs;

import gomoku.kernel.*;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import gomoku.ui.elements.Piece;
import gomoku.ui.frameworks.ChessBoard;
import gomoku.ui.frameworks.ContentPane;
import gomoku.ui.frameworks.OperationBar;
import gomoku.ui.nodes.GameButton;
import gomoku.ui.nodes.StepListView;


/**
 * 棋盘对话框
 */

public class GameDisplay extends DialogBase {
    public static final double width = 900;
    public static final double height = 700;
    private Controller controller;
    private Piece[][] pieceGroup;
    private ChessBoard chessBoard;
    private OperationBar operationBar;
    private ContentPane contentPane;
    private Save save;
    private Gomoku go;
    private boolean closed = false;

    public GameDisplay() {
        super(width, height);
        contentPane = ContentPane.getSelf();
        chessBoard = new ChessBoard();
        chessBoard.setLayoutX(25);
        chessBoard.setLayoutY(25);
        getChildren().addAll(chessBoard);
        pieceGroup = new Piece[15][15];
        double offsetX = 53 - Piece.width / 2;
        double offsetY = 50 - Piece.height / 2;
        for (int i = 0; i < pieceGroup.length; i++) {
            for (int j = 0; j < pieceGroup[0].length; j++) {
                pieceGroup[i][j] = new Piece();
                pieceGroup[i][j].setLayoutX(offsetX + j * Piece.width);
                pieceGroup[i][j].setLayoutY(offsetY + i * Piece.height);
                pieceGroup[i][j].setOnMousePressed(new OnMouseClickHandler(i, j));
                getChildren().add(pieceGroup[i][j]);
            }
        }
        operationBar = new OperationBar();
        operationBar.layoutXProperty().bind(this.widthProperty().subtract(operationBar.widthProperty()));
        operationBar.prefHeightProperty().bind(this.heightProperty());
        getChildren().add(operationBar);
        save = new Save();
        controller = new Controller(this, save);
        controller.restart();


        StepListView list = operationBar.getListView();
        list.getItems().add("[游戏开始]");
        list.getSelectionModel().select(0);
        GameButton backButton = operationBar.getStepButtons()[0];
        GameButton forwardButton = operationBar.getStepButtons()[1];
        list.getSelectionModel().selectedIndexProperty().addListener(ov -> {
            int index = list.getSelectionModel().getSelectedIndex();
            backButton.setDisable(index <= 0);
            forwardButton.setDisable(index >= list.getItems().size() - 1);
            controller.peek(index - 1);
        });
        backButton.setOnAction(e -> {
            list.getSelectionModel().select(list.getSelectionModel().getSelectedIndex() - 1);
        });
        forwardButton.setOnAction(e -> {
            list.getSelectionModel().select(list.getSelectionModel().getSelectedIndex() + 1);
        });
        operationBar.getOperationButtons()[0].setOnAction(e -> {
            controller.restart();
            go.setAi(operationBar.getAISwitch().isSelected());
        });
        operationBar.getOperationButtons()[1].setOnAction(e -> {
            contentPane.getNameInputDialog().getTextField().setText("");
            contentPane.showDialog(contentPane.getNameInputDialog());
        });
        operationBar.getOperationButtons()[2].setOnAction(e -> ContentPane.getSelf().showDialog(ContentPane.getSelf().getSaveDialog()));
        //回放棋局
        /*operationBar.getOperationButtons()[3].setOnAction(e -> {
            operationBar.getListView().getSelectionModel().select(0);
            operationBar.getStepButtons()[1].setDisable(true);
            closed = true;
            operationBar.getTimeDisplay().stop();
            operationBar.getTimeDisplay().clear();
            controller.updateCountDisplay();

            int n = save.getSteps().size();
            for (int k = n - 1; k > controller.getPeekIndex(); k--) {
                if (list.getItems().size() >= k + 2)
                    list.getItems().remove(k + 1);
                int i = save.getSteps().get(k).getI();
                int j = save.getSteps().get(k).getJ();
                save.getChessBoard()[i][j] = PieceType.EMPTY;
                pieceGroup[i][j].pop();
                System.out.println("删除：" + k);
            }

            for (int k = 0; k < save.getSteps().size()-1; k++) {
                System.out.println(k);
                if (k == 0) {
                    Step step = save.getSteps().get(0);
                    int i = step.getI();
                    int j = step.getJ();
                    int type = save.getChessBoard()[i][j];
                    pieceGroup[i][j].push(type);
                    operationBar.getListView().getItems().add("[" + Controller.getTypeName(type) + "]" + " " + Controller.getPieceName(i, j));
                } else {
                    Step step = save.getSteps().get(k);
                    workTime(step.getTime());
                    int i = step.getI();
                    int j = step.getJ();
                    int type = save.getChessBoard()[i][j];
                    pieceGroup[i][j].push(type);
                    operationBar.getListView().getItems().add("[" + Controller.getTypeName(type) + "]" + " " + Controller.getPieceName(i, j));
                }
            }
        });*/
        operationBar.getAISwitch().selectedProperty().addListener(ov -> {
            controller.restart();
            go.setAi(operationBar.getAISwitch().isSelected());
        });

    }

    public Piece getPiece(int i, int j) {
        return pieceGroup[i][j];
    }
    //取位于(i,j)处的棋子对象

    public OperationBar getOperationBar() {
        return this.operationBar;
    }
    //取操作栏对象


    public boolean isClosed() {
        return this.closed;
    }
    //判断是否禁止下棋（胜利时）

    public void setClosed(boolean closed) {
        this.closed = closed;
    }
    //设置是否禁止下棋


    public void setSave(Save save) {
        this.save = save;
    }

    public Save getSave() {
        return save;
    }

    public Controller getController() {
        return controller;
    }

    public void setGo(Gomoku go) {
        this.go = go;
    }

    public Gomoku getGo() {
        return go;
    }
    //返回下棋控制

    private class OnMouseClickHandler implements EventHandler<MouseEvent> {
        private int i;
        private int j;

        public OnMouseClickHandler(int i, int j) {
            this.i = i;
            this.j = j;
        }

        @Override
        public void handle(MouseEvent e) {
            if (!closed) {
                controller.push(i, j);
            }
        }
    }
    //鼠标单击时的事件处理内部类

    private static void workTime(long ms) {
        final long l = System.currentTimeMillis();
        while (System.currentTimeMillis() <= l + ms) {
        }
    }
}
