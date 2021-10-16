package gomoku.ui.frameworks.dialogs;

import com.jfoenix.controls.JFXPasswordField;
import gomoku.ConfigService;
import gomoku.ui.frameworks.ContentPane;
import gomoku.ui.nodes.*;
import javafx.scene.image.ImageView;

public class LoginBox extends DialogBase {
    public static final double width = 640;
    public static final double height = 350;
    private TitleLabel title;
    private ContextLabel context;
    protected GameTextField nameField;
    protected JFXPasswordField passWordField;

    public LoginBox(String title, String context) {
        super(width, height, true);
        getButtonBar().addButton(new GreenButton("登录"), new RedButton("注册"));
        this.title = new TitleLabel(title);
        this.title.setGraphic(new ImageView(ConfigService.getResource("drawable/icon/login.png")));
        this.context = new ContextLabel(context);
        this.title.setLayoutX(20);
        this.title.setLayoutY(10);
        this.context.setLayoutX(20);
        this.context.setLayoutY(90);
        this.context.setPrefWidth(width - 40);
        this.getButtonBar().getButton(1).setOnAction(e -> ContentPane.getSelf().hideDialog());
        nameField = new GameTextField();
        passWordField = new JFXPasswordField();
        passWordField.getStyleClass().add("game_text_field");
        nameField.setLayoutX(80);
        nameField.setLayoutY(100);
        this.nameField.setPrefWidth(width - 100);
        passWordField.setLayoutX(80);
        passWordField.setLayoutY(180);
        this.passWordField.setPrefWidth(width - 100);
        getChildren().addAll(this.title, this.context, this.nameField, this.passWordField);
    }

    public GameTextField getNameField() {
        return nameField;
    }

    public JFXPasswordField getPassWordField() {
        return passWordField;
    }
}
