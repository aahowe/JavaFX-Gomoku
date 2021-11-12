package gomoku.ui.frameworks.dialogs;

import gomoku.database.Operate;
import gomoku.kernel.Controller;
import gomoku.kernel.Save;
import javafx.scene.image.ImageView;
import gomoku.ConfigService;
import gomoku.ui.frameworks.ContentPane;
import gomoku.ui.nodes.*;

import java.util.List;
import java.util.Objects;

/**
 * 存档管理器对话框
 */

public class SaveDialog extends DialogBase {
    private TitleLabel titleLabel;
    private SaveListView listView;
    //进入存档对话框要做的事情
    private int type;

    public SaveDialog() {
        super(1024, 600, true);
        type = 1;
        titleLabel = new TitleLabel("您的存档");

        titleLabel.setGraphic(new ImageView(ConfigService.getResource("drawable/icon/save.png")));
        titleLabel.setLayoutX(20);
        titleLabel.setLayoutY(10);
        listView = new SaveListView();
        listView.setLayoutY(90);
        listView.setLayoutX(20);
        listView.prefWidthProperty().bind(this.widthProperty().subtract(40));
        listView.prefHeightProperty().bind(this.heightProperty().subtract(200));
        getChildren().addAll(titleLabel, listView);
        refresh();
        getButtonBar().addButton(new GameButton("刷新"), new BlueButton("载入"), new RedButton("删除"));
        ContentPane contentPane = ContentPane.getSelf();
        getButtonBar().getButton(1).setDisable(true);
        getButtonBar().getButton(2).setDisable(true);
        getButtonBar().getButton(0).setOnAction(e -> refresh());
        getButtonBar().getButton(1).setOnAction(e -> {
            if (type == 1) {
                String name = listView.getSelectionModel().getSelectedItem();
                if (Objects.equals(name, Controller.getSelf().getSave().getSaveName())) {
                    contentPane.showDialog(contentPane.getGameDisplay());
                }
                Controller.getSelf().loadGame(name);
                contentPane.showDialog(contentPane.getGameDisplay());
            } else if (type == 2) {
                String name = listView.getSelectionModel().getSelectedItem();
                if (Objects.equals(name, Controller.getSelf().getSave().getSaveName())) {
                    contentPane.showDialog(contentPane.getGameDisplay());
                }
                Controller.getSelf().replay(name);
                GameDisplay.getSelf().getOperationBar().getOperationButtons()[1].setDisable(true);
                contentPane.showDialog(contentPane.getGameDisplay());
            }
        });
        getButtonBar().getButton(2).setOnAction(e -> {
            int index = listView.getSelectionModel().getSelectedIndex();
            String name = listView.getItems().get(index);
            Operate.deleteSave(name);
            listView.getItems().remove(index);
        });
        listView.getSelectionModel().selectedIndexProperty().addListener(ov -> {
            if (type==1){
                boolean disabled = listView.getSelectionModel().getSelectedIndex() < 0;
                getButtonBar().getButton(1).setDisable(disabled);
                getButtonBar().getButton(2).setDisable(disabled);
            }else if (type==2){
                boolean disabled = listView.getSelectionModel().getSelectedIndex() < 0;
                getButtonBar().getButton(1).setDisable(disabled);
                getButtonBar().getButton(2).setDisable(true);
            }
        });


    }

    //刷新存档列表
    public List<Save> refresh() {
        listView.getItems().clear();
        List<Save> saveList = Operate.ConnectSaveDB(ContentPane.getSelf().getUsername());
        if (saveList != null) {
            for (Save s : saveList) {
                listView.getItems().add(s.getSaveName());
            }
        }
        return saveList;
    }

    //设置进入存档对话框需要进行的操作
    public void setType(int type) {
        this.type = type;
    }
}
