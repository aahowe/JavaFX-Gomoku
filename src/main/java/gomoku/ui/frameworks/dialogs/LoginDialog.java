package gomoku.ui.frameworks.dialogs;


import gomoku.database.Operate;
import gomoku.ui.frameworks.ContentPane;

public class LoginDialog extends LoginBox {

    public LoginDialog() {
        super("登录", "");
        getButtonBar().getButton(0).setOnAction(e ->
                validate()
        );
        getButtonBar().getButton(1).setOnAction(e ->
                add(nameField.getText(), passWordField.getText())
        );
    }

    private void validate() {
        if (Operate.ConnectUserDB(nameField.getText(), passWordField.getText())) {
            System.out.println("登录成功");
            ContentPane.getSelf().showTips("登陆成功");
            ContentPane.getSelf().setUsername(nameField.getText());
            ContentPane.getSelf().setSaveList(ContentPane.getSelf().getSaveDialog().refresh());
            ContentPane.getSelf().hideDialog();
            ContentPane.getSelf().hidePopup();
        } else {
            ContentPane.getSelf().showTips("账号或密码错误");
            System.out.println("登录失败");
        }
    }

    private void add(String name, String password) {
        if (Operate.addUser(nameField.getText(), passWordField.getText())) {
            ContentPane.getSelf().showTips("注册成功");
        } else {
            ContentPane.getSelf().showTips("用户名已被注册");
        }
    }

}
