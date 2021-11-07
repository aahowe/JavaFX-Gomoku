package gomoku.ui.frameworks.dialogs;

import com.jfoenix.controls.JFXPasswordField;
import gomoku.ConfigService;
import gomoku.database.Operate;
import gomoku.ui.frameworks.ContentPane;
import gomoku.ui.nodes.*;
import javafx.scene.image.ImageView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterDialog extends DialogBase {
    public static final double width = 640;
    public static final double height = 450;
    protected GameTextField nameField;
    protected GameTextField passWordField;
    protected GameTextField phoneField;
    ImageView account;
    ImageView password;
    ImageView phone;

    public RegisterDialog() {
        super(width, height, true);
        getButtonBar().addButton(new GreenButton("确定"), new RedButton("取消"));
        TitleLabel title = new TitleLabel("注册");
        title.setGraphic(new ImageView(ConfigService.getResource("drawable/icon/login.png")));
        title.setLayoutX(20);
        title.setLayoutY(10);
        this.getButtonBar().getButton(1).setOnAction(e -> ContentPane.getSelf().hideDialog());
        this.getButtonBar().getButton(0).setOnAction(e -> add());
        nameField = new GameTextField();
        passWordField = new GameTextField();
        phoneField = new GameTextField();
        nameField.setLayoutX(80);
        nameField.setLayoutY(100);
        this.nameField.setPrefWidth(width - 100);
        passWordField.setLayoutX(80);
        passWordField.setLayoutY(180);
        this.passWordField.setPrefWidth(width - 100);
        phoneField.setLayoutX(80);
        phoneField.setLayoutY(260);
        this.phoneField.setPrefWidth(width - 100);
        account = new ImageView("/drawable/icon/account.png");
        password = new ImageView("/drawable/icon/password.png");
        phone = new ImageView("/drawable/icon/phone.png");
        account.setLayoutX(20);
        account.setLayoutY(100);
        password.setLayoutX(20);
        password.setLayoutY(180);
        phone.setLayoutX(20);
        phone.setLayoutY(260);
        getChildren().addAll(title, nameField, passWordField, phoneField, account, password, phone);
    }


    private void add() {
        if (isMobile(phoneField.getText())){
            if (Operate.addUser(nameField.getText(), passWordField.getText())) {
                ContentPane.getSelf().showTips("注册成功");
            } else {
                ContentPane.getSelf().showTips("用户名已被注册");
            }
        }else {
            ContentPane.getSelf().showTips("请输入正确的11位手机号");
        }
    }



    public boolean isMobile(String mobiles) {
        Pattern p = Pattern.compile("^((13[0-9])|(14[0|5|6|7|9])|(15[0-3])|(15[5-9])|(16[6|7])|(17[2|3|5|6|7|8])|(18[0-9])|(19[1|8|9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }
}
