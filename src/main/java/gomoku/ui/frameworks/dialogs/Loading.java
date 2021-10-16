package gomoku.ui.frameworks.dialogs;

import com.jfoenix.controls.JFXSpinner;
import gomoku.ui.frameworks.ContentPane;
import gomoku.ui.nodes.RedButton;
import javafx.scene.control.Label;

public class Loading extends DialogBase{

    Label loadingLabel;
    JFXSpinner spinner = new JFXSpinner();

    public Loading() {
        super(640, 350,true);
        loadingLabel=new Label("正在连接至服务器");
        loadingLabel.setLayoutX(0);
        loadingLabel.setLayoutY(0);
        spinner.setLayoutX(0);
        spinner.setLayoutY(0);
        getButtonBar().addButton(new RedButton("取消"));
        getButtonBar().getButton(0).setOnAction(e -> ContentPane.getSelf().hideDialog());
        getChildren().addAll(spinner,loadingLabel);
    }


}
