package gomoku.ui.frameworks.dialogs;

import gomoku.database.Operate;
import gomoku.kernel.Controller;
import gomoku.kernel.Save;
import javafx.scene.image.ImageView;
import gomoku.ui.Configure;
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

    public SaveDialog() {
        super(1024, 600, true);
        titleLabel = new TitleLabel("管理存档");

        titleLabel.setGraphic(new ImageView(Configure.getResource("drawable/icon/save.png")));
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
            String name = listView.getSelectionModel().getSelectedItem();
            if (Objects.equals(name, Controller.getSelf().getSave().getSaveName())) {
                System.out.println("存档相同");
                contentPane.showDialog(contentPane.getGameDisplay());
            }
            Controller.getSelf().loadGame(name);
            Controller.getSelf().setCurrentSave(name);
            contentPane.showDialog(contentPane.getGameDisplay());
        });
        getButtonBar().getButton(2).setOnAction(e -> {
            int index = listView.getSelectionModel().getSelectedIndex();
            String name = listView.getItems().get(index);
            Operate.deleteSave(name);
            listView.getItems().remove(index);
        });
        listView.getSelectionModel().selectedIndexProperty().addListener(ov -> {
            boolean disabled = true;
            if (listView.getSelectionModel().getSelectedIndex() >= 0) {
                disabled = false;
            } else {
                disabled = true;
            }
            getButtonBar().getButton(1).setDisable(disabled);
            getButtonBar().getButton(2).setDisable(disabled);
        });


    }

    public SaveListView getListView() {
        return this.listView;
    }
    //取存档列表界面对象

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
    //刷新存档列表

}
