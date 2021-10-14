package gomoku.ui.frameworks;

import gomoku.kernel.Save;
import javafx.scene.layout.Pane;
import gomoku.ui.Configure;
import gomoku.ui.animations.EasingProperty;
import gomoku.ui.frameworks.dialogs.*;
import gomoku.ui.nodes.IconButton;
import gomoku.ui.nodes.MenuBar;
import gomoku.ui.nodes.TimeDisplay;

import java.security.MessageDigest;
import java.util.List;
import java.util.Objects;

/**
 * 游戏主界面
 */

public class ContentPane extends Pane {
    private BackgroundView bkg;
    private boolean dialogShowed = false;
    private Pane currentDialog;
    private SaveDialog saveDialog;
    private MessageBox exitDialog;
    private GameDisplay gameDisplay;
    private NameInputDialog nameInputDialog;
    private LoginDialog loginDialog;

    private boolean popupShowed = false;
    private PopupBase currentPopup;
    private MenuBar menuBar;
    private IconButton skinButton;
    private IconButton musicButton;
    private SkinSelector skinSelector;
    private TipDisplay tipDisplay;

    private List<Save> saveList;
    private static ContentPane self;
    private String Username = "";

    public ContentPane() {
        self = this;
        getStyleClass().add("content_pane");
        bkg = new BackgroundView();

        tipDisplay = new TipDisplay();
        tipDisplay.setLayoutY(30);
        tipDisplay.setLayoutX(100);

        menuBar = new MenuBar();
        menuBar.addButton("登录");
        menuBar.addButton("游戏");
        menuBar.addButton("存档");
        menuBar.addButton("关于");
        menuBar.addButton("注销");
        menuBar.layoutYProperty().bind(this.heightProperty().subtract(menuBar.prefHeightProperty()));
        menuBar.prefWidthProperty().bind(this.widthProperty());

        loginDialog = new LoginDialog();
        saveDialog = new SaveDialog();
        exitDialog = new MessageBox("确认", "你确认要注销登录吗？");
        exitDialog.getButtonBar().getButton(0).setOnAction(e -> {
            Username = "";
            showTips("注销成功");
            showDialog(getLoginDialog());
        });

        gameDisplay = new GameDisplay();
        nameInputDialog = new NameInputDialog();


        skinSelector = new SkinSelector();
        skinSelector.setLayoutY(20);
        skinButton = new IconButton(Configure.getResource("drawable/icon/skin.png"));
        skinButton.setLayoutX(Configure.viewportWidth - skinButton.getPrefWidth() - 5);
        skinButton.setLayoutY(20);
        skinButton.setOnAction(e -> {
            hidePopup();
            showPopup(skinSelector);
        });


        getChildren().addAll(bkg, menuBar, skinButton);

        menuBar.getButton(0).setOnAction(e -> {
            if (Objects.equals(Username, "")) {
                showDialog(getLoginDialog());
            } else {
                hideDialog();
                hidePopup();
                bkg.getLogoView().unstretch();
            }
        });
        menuBar.getButton(1).setOnAction(e -> {
            if (Objects.equals(Username, "")) {
                showTips("请先登录");
            } else {
                showDialog(getGameDisplay());
            }
        });
        menuBar.getButton(2).setOnAction(e -> {
            if (Objects.equals(Username, "")) {
                showTips("请先登录");
            } else {
                setSaveList(saveDialog.refresh());
                showDialog(getSaveDialog());
            }
        });
        menuBar.getButton(3).setOnAction(e -> {
            if (bkg.getLogoView().isStretched()) {
                bkg.getLogoView().unstretch();
            } else {
                bkg.getLogoView().stretch();
                hideDialog();
                hidePopup();
            }
        });
        menuBar.getButton(4).setOnAction(e -> {
            if (Objects.equals(Username, "")) {
                showTips("请先登录");
            } else {
                showDialog(exitDialog);
            }
        });
        this.setOnMouseClicked(e -> {
            if (popupShowed && currentPopup != tipDisplay) {
                hidePopup();
            }
        });
        menuBar.setOnMouseClicked(e -> {
            if (popupShowed && currentPopup != tipDisplay) {
                hidePopup();
            }
        });

    }

    public void showDialog(DialogBase dialog) {
        if (dialog == currentDialog && dialogShowed) {
            return;
        }
        if (currentDialog != null) {
            getChildren().remove(currentDialog);
            currentDialog = null;
        }
        TimeDisplay timeDisplay = gameDisplay.getOperationBar().getTimeDisplay();
        if (dialog == gameDisplay && !gameDisplay.isClosed() && gameDisplay.getSave().getSteps().size() > 0) {
            if (!timeDisplay.isStarted()) {
                timeDisplay.start();
            }
        } else if (timeDisplay.isStarted()) {
            gameDisplay.getOperationBar().getTimeDisplay().stop();
        }
        double width = dialog.getPreferredWidth();
        double height = dialog.getPreferredHeight();
        dialog.setPrefWidth(Configure.viewportWidth);
        dialog.setPrefHeight(Configure.viewportHeight);
        dialog.setOpacity(0.0);
        dialog.layoutXProperty().bind(widthProperty().subtract(dialog.prefWidthProperty()).divide(2));
        dialog.layoutYProperty().bind(heightProperty().subtract(menuBar.prefHeightProperty()).subtract(dialog.prefHeightProperty()).divide(2));
        EasingProperty widthAnimation = new EasingProperty(dialog.prefWidthProperty());
        EasingProperty heightAnimation = new EasingProperty(dialog.prefHeightProperty());
        EasingProperty opacityAnimation = new EasingProperty(dialog.opacityProperty());
        widthAnimation.setToValue(width);
        heightAnimation.setToValue(height);
        opacityAnimation.setToValue(1.0);
        currentDialog = dialog;
        bkg.blur();
        getChildren().add(1, dialog);
        currentDialog.setMouseTransparent(false);
        dialogShowed = true;
    }
    //显示指定对话框

    public void hideDialog() {
        if (currentDialog != null) {
            TimeDisplay timeDisplay = gameDisplay.getOperationBar().getTimeDisplay();
            if (currentDialog == gameDisplay && timeDisplay.isStarted()) {
                timeDisplay.stop();
            }
            EasingProperty widthAnimation = new EasingProperty(currentDialog.prefWidthProperty());
            EasingProperty heightAnimation = new EasingProperty(currentDialog.prefHeightProperty());
            EasingProperty opacityAnimation = new EasingProperty(currentDialog.opacityProperty());
            widthAnimation.setToValue(Configure.viewportWidth);
            heightAnimation.setToValue(Configure.viewportHeight);
            opacityAnimation.setToValue(0.0);
            currentDialog.setMouseTransparent(true);
            bkg.unBlur();
            dialogShowed = false;
        }
    }
    //隐藏当前对话框

    public void showPopup(PopupBase popup) {
        if (currentPopup == popup && popupShowed) {
            return;
        }
        if (currentPopup != null) {
            getChildren().remove(currentPopup);
            currentPopup = null;
        }
        popup.setOpacity(0.0);
        double ori = Configure.viewportWidth - popup.getPrefWidth();
        popup.setLayoutX(ori);
        EasingProperty easeX = new EasingProperty(popup.layoutXProperty());
        EasingProperty easeOpacity = new EasingProperty(popup.opacityProperty());
        easeOpacity.setToValue(1.0);
        easeX.setToValue(ori - 100);
        getChildren().add(popup);
        currentPopup = popup;
        currentPopup.setMouseTransparent(false);
        popupShowed = true;

    }
    //显示指定弹窗

    public void hidePopup() {
        if (currentPopup == null) {
            return;
        }
        double toX = Configure.viewportWidth;
        EasingProperty easeX = new EasingProperty(currentPopup.layoutXProperty());
        EasingProperty easeOpacity = new EasingProperty(currentPopup.opacityProperty());
        easeX.setToValue(toX);
        easeOpacity.setToValue(0.0);
        currentPopup.setMouseTransparent(true);
        popupShowed = false;
    }
    //隐藏弹窗

    public void showTips(String context) {
        tipDisplay.setText(context);
        hidePopup();
        showPopup(tipDisplay);
        tipDisplay.startTiming();
    }
    //显示消息提示

    public SaveDialog getSaveDialog() {
        return this.saveDialog;
    }
    //取存档管理对话框

    public NameInputDialog getNameInputDialog() {
        return this.nameInputDialog;
    }
    //取存档名称输入框

    public GameDisplay getGameDisplay() {
        return this.gameDisplay;
    }
    //取棋盘对话框

    public LoginDialog getLoginDialog() {
        return this.loginDialog;
    }
    //取登录对话框

    public void setUsername(String name) {
        Username = name;
    }

    public static ContentPane getSelf() {
        return self;
    }
    //由于该类只会创建一个对象，故使用一个静态变量来保存唯一的对象，以便于外界访问

    public String getUsername() {
        return Username;
    }

    public List<Save> getSaveList() {
        return saveList;
    }

    public void setSaveList(List<Save> saveList) {
        this.saveList = saveList;
    }
}
