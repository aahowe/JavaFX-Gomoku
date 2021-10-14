package gomoku.ui.frameworks.dialogs;

import gomoku.ui.nodes.GameTextField;

/**
 * 输入对话框
 */

public class InputDialog extends MessageBox {
    protected GameTextField textField;

    public InputDialog(String title) {
        super(title, "");
        textField = new GameTextField();
        textField.setLayoutX(20);
        textField.setLayoutY(110);
        this.textField.setPrefWidth(width - 40);
        getChildren().add(textField);
    }

    public GameTextField getTextField() {
        return this.textField;
    }
}
