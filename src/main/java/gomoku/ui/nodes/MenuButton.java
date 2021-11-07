package gomoku.ui.nodes;

import com.jfoenix.controls.JFXButton;
import javafx.scene.control.ContentDisplay;

/**
 * 导航栏按钮
 */

public class MenuButton extends JFXButton {
    /*Rectangle rcBkg;
    EasingProperty opacityAnimation;*/
    public MenuButton(String title) {
        super(title);
        getStyleClass().add("menu_button");
        /*rcBkg = new Rectangle(180,60);
        rcBkg.setArcWidth(10);
        rcBkg.setArcHeight(10);
        rcBkg.getStyleClass().add("menu_button_background");
        rcBkg.setOpacity(0.0);
        opacityAnimation = new EasingProperty(rcBkg.opacityProperty());*/
        setContentDisplay(ContentDisplay.CENTER);
        /*setGraphic(rcBkg);
        this.setOnMouseEntered(e -> {
            opacityAnimation.setToValue(0.2);
        });
        this.setOnMouseExited(e -> {
            opacityAnimation.setToValue(0.0);
        });*/
    }
}
