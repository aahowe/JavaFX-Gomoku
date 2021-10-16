package gomoku.ui.frameworks;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import gomoku.ConfigService;

/**
 * 棋盘类，只负责显示棋盘（无棋子）
 */

public class ChessBoard extends Pane {
    ImageView bkg;

    public ChessBoard() {
        super();
        bkg = new ImageView(ConfigService.getResource("drawable/chessboard.png"));
        bkg.getStyleClass().add("chess_board_background");
        getChildren().add(bkg);
    }

}
