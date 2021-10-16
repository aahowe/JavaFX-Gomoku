package gomoku.ui.frameworks;

import gomoku.ui.nodes.LogoView;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.Pane;
import gomoku.ConfigService;
import gomoku.ui.animations.EasingProperty;
import gomoku.ui.elements.FadingCircle;
import gomoku.ui.nodes.BackgroundChannel;
import gomoku.ui.nodes.MenuBar;

/**
 * 背景图显示器，负责显示和切换背景图、管理背景图动画
 */

public class BackgroundView extends Pane {
    private GaussianBlur blurEffect;
    private EasingProperty blurAnimation;
    private EasingProperty opacityAnimation;
    private LogoView logoView;
    public static BackgroundView self;

    public BackgroundView() {
        getStyleClass().add("background_view");

        logoView = new LogoView();
        logoView.setLayoutX((ConfigService.viewportWidth - LogoView.width) / 2);
        logoView.setLayoutY((ConfigService.viewportHeight - MenuBar.height - LogoView.height) / 2);
        getChildren().addAll(logoView);

        blurEffect = new GaussianBlur(0.0);
        blurAnimation = new EasingProperty(blurEffect.radiusProperty());
        opacityAnimation = new EasingProperty(this.opacityProperty());
        this.setEffect(blurEffect);
        setOnMouseClicked(e -> getChildren().add(new FadingCircle(e.getX(), e.getY())));
        self = this;
    }

    public void addBackgroundChannel(String url) {
        BackgroundChannel img = new BackgroundChannel(url);
        getChildren().add(getChildren().size() - 1, img);
        img.show();
        if (img.getImage().getWidth() > ConfigService.viewportWidth) {
            img.startRolling();
        }
    }
    //增加背景图

    public static BackgroundView getSelf() {
        return self;
    }
    //由于该类只会创建一个对象，故使用一个静态变量来保存唯一的对象，以便于外界访问

    public LogoView getLogoView() {
        return this.logoView;
    }
    //取背景图上的Logo对象

    public void blur() {
        blurAnimation.setToValue(40.0);
        opacityAnimation.setToValue(0.5);
        setDisable(true);
    }
    //模糊背景图（用于显示对话框）

    public void unBlur() {
        blurAnimation.setToValue(0.0);
        opacityAnimation.setToValue(1.0);
        setDisable(false);
    }
    //取消背景图模糊
}
