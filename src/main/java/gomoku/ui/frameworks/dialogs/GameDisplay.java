package gomoku.ui.frameworks.dialogs;

import gomoku.kernel.*;
import gomoku.ui.nodes.TimeDisplay;
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
    private static GameDisplay self;
    public static final double width = 900;
    public static final double height = 700;
    private Controller controller;
    private Piece[][] pieceGroup;
    private ChessBoard chessBoard;
    private OperationBar operationBar;
    private ContentPane contentPane;
    private Gomoku go;
    private HTimer2Plus timer;
    private PlayWatcher2Plus player;
    private boolean closed = false;

    public GameDisplay() {
        super(width, height);
        self = this;
        timer = new HTimer2Plus();
        player = new PlayWatcher2Plus(timer);
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
        controller = new Controller(this, new Save());
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
            operationBar.getOperationButtons()[1].setDisable(false);
            controller.restart();
            go.setAi(operationBar.getAISwitch().isSelected());
        });
        operationBar.getOperationButtons()[1].setOnAction(e -> {
            contentPane.getNameInputDialog().getTextField().setText("");
            contentPane.showDialog(contentPane.getNameInputDialog());
        });
        operationBar.getOperationButtons()[2].setOnAction(e -> {
            ContentPane.getSelf().getSaveDialog().setType(1);
            ContentPane.getSelf().showDialog(ContentPane.getSelf().getSaveDialog());
        });
        //回放棋局
        operationBar.getOperationButtons()[3].setOnAction(e -> {
            ContentPane.getSelf().getSaveDialog().setType(2);
            ContentPane.getSelf().showDialog(ContentPane.getSelf().getSaveDialog());
        });
        operationBar.getAISwitch().selectedProperty().addListener(ov -> {
            operationBar.getOperationButtons()[0].setDisable(false);
            operationBar.getOperationButtons()[1].setDisable(false);
            controller.restart();
            go.setAi(operationBar.getAISwitch().isSelected());
        });

    }

    public static GameDisplay getSelf() {
        return self;
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


    public HTimer2Plus getTimer() {
        return timer;
    }

    public void setTimer(HTimer2Plus timer) {
        this.timer = timer;
    }

    public PlayWatcher2Plus getPlayer() {
        return player;
    }

    public void setPlayer(PlayWatcher2Plus player) {
        this.player = player;
    }

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
