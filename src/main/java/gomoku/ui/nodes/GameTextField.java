package gomoku.ui.nodes;

import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.TextField;

/**
 * 输入框
 */

public class GameTextField extends JFXTextField {
    public GameTextField(){
        super();
        this.getStyleClass().add("game_text_field");
    }
}
