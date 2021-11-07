package gomoku.ui.frameworks.dialogs;


import gomoku.database.Operate;
import gomoku.ui.frameworks.ContentPane;
import javafx.scene.image.ImageView;

public class LoginDialog extends LoginBox {

    ImageView account;
    ImageView password;

    public LoginDialog() {
        super("登录");
        getButtonBar().getButton(0).setOnAction(e ->
                validate()
        );
        getButtonBar().getButton(1).setOnAction(e ->
                ContentPane.getSelf().showDialog(ContentPane.getSelf().getRegisterDialog())
        );
        account = new ImageView("/drawable/icon/account.png");
        password = new ImageView("/drawable/icon/password.png");
        account.setLayoutX(20);
        account.setLayoutY(100);
        password.setLayoutX(20);
        password.setLayoutY(180);
        getChildren().addAll(account,password);
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


}
