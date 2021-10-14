package gomoku.ui.nodes;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import gomoku.ui.Configure;
import gomoku.ui.animations.EasingProperty;

/**
 * Logo控件
 */

public class LogoView extends Pane {
    private Pane bkg;
    private Rectangle bkgRec;
    private ImageView logo;
    private Label info;
    private EasingProperty logoAnimation;
    private EasingProperty bkgAnimation;
    public static final String infoStr = "Gomoku \n\n" +
            "作者\t\t王昊\n" +
            "完成日期\t2021年10月17日\n\n";
    public static final double width = 800;
    public static final double height = 500;
    public boolean stretched = false;

    public LogoView() {
        super();
        setPrefWidth(width);
        setPrefHeight(height);
        logo = new ImageView(Configure.getResource("drawable/logo.png"));

        bkg = new Pane();
        bkgRec = new Rectangle(width, height);
        bkgRec.setLayoutX(0);
        bkgRec.setLayoutY(0);
        bkgRec.getStyleClass().add("black_pad_background_rectangle");
        info = new Label(infoStr);
        info.getStyleClass().add("black_pad_text");
        info.setWrapText(true);
        info.setPrefWidth(width - 40);
        info.setLayoutX(20);
        info.setLayoutY(300);
        bkg.setOpacity(0.0);
        logo.setLayoutY(150);
        logoAnimation = new EasingProperty(logo.layoutYProperty());
        bkgAnimation = new EasingProperty(bkg.opacityProperty());
        bkg.getChildren().addAll(bkgRec, info);
        getChildren().addAll(bkg, logo);
    }

    public boolean isStretched() {
        return this.stretched;
    }

    public void stretch() {
        if (!stretched) {
            logoAnimation.setToValue(20);
            bkgAnimation.setToValue(1.0);
            stretched = true;
        }
    }
    //伸展成关于对话框

    public void unstretch() {
        if (stretched) {
            logoAnimation.setToValue(150);
            bkgAnimation.setToValue(0.0);
            stretched = false;
        }
    }
    //收缩成单个Logo
}
