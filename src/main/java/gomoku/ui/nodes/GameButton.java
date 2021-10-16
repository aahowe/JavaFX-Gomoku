package gomoku.ui.nodes;

import com.jfoenix.controls.JFXButton;

/**
 * 普通按钮
 */

public class GameButton extends JFXButton {
    public GameButton(String title) {
        super(title);
        this.getStyleClass().add("game_button");
    }

    public GameButton(String title, int fontSize) {
        this(title);
        this.setStyle("-fx-font-size:" + fontSize + ";");
    }
}
