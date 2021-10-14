package gomoku.ui.frameworks.dialogs;


import gomoku.kernel.Controller;
import gomoku.ui.frameworks.ContentPane;


/**
 * 存档名称输入对话框
 */

public class NameInputDialog extends InputDialog {
    protected ContentPane contentPane;
    protected Controller controller;

    public NameInputDialog() {
        super("请输入存档名称");
        contentPane = ContentPane.getSelf();
        controller = Controller.getSelf();
        getButtonBar().getButton(0).setText("保存");
        getButtonBar().getButton(0).setOnAction(e -> {
            String name = textField.getText();
            controller.saveGame(name);
            contentPane.setSaveList(contentPane.getSaveDialog().refresh());
            contentPane.showDialog(contentPane.getSaveDialog());
            controller.setCurrentSave(name);
        });
        getButtonBar().getButton(1).setOnAction(e -> {
            contentPane.showDialog(contentPane.getGameDisplay());
        });
    }


}
