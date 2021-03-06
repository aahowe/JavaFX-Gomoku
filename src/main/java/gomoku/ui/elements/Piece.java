package gomoku.ui.elements;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import gomoku.kernel.PieceType;
import gomoku.ConfigService;
import gomoku.ui.animations.EasingProperty;

/**
 * 棋子类（界面层）
 */

public class Piece extends Pane {
    public static final double width = 44;
    public static final double height = 44;
    private Label num;

    private int currentType = PieceType.EMPTY;
    private ImageView currentImage;
    private FadingCircle circle;
    private EasingProperty currentScaleAnimation;
    private EasingProperty currentOpacityAnimation;

    private ImageView pieceWhite;
    private ImageView pieceBlack;
    private EasingProperty whiteScaleAnimation;
    private EasingProperty whiteOpacityAnimation;
    private EasingProperty blackScaleAnimation;
    private EasingProperty blackOpacityAnimation;

    public Piece() {
        super();
        this.setOpacity(0.8);
        setPrefSize(width, height);
        pieceWhite = new ImageView(ConfigService.getResource("drawable/piece_white.png"));
        pieceBlack = new ImageView(ConfigService.getResource("drawable/piece_black.png"));

        pieceWhite.layoutXProperty().bind(this.widthProperty().subtract(pieceWhite.fitWidthProperty()).divide(2));
        pieceWhite.layoutYProperty().bind(this.heightProperty().subtract(pieceWhite.fitHeightProperty()).divide(2));
        pieceBlack.layoutXProperty().bind(this.widthProperty().subtract(pieceBlack.fitWidthProperty()).divide(2));
        pieceBlack.layoutYProperty().bind(this.heightProperty().subtract(pieceBlack.fitHeightProperty()).divide(2));


        whiteScaleAnimation = new EasingProperty(pieceWhite.fitWidthProperty());
        pieceWhite.fitHeightProperty().bind(pieceWhite.fitWidthProperty());
        whiteOpacityAnimation = new EasingProperty(pieceWhite.opacityProperty());
        blackScaleAnimation = new EasingProperty(pieceBlack.fitWidthProperty());
        blackOpacityAnimation = new EasingProperty(pieceBlack.opacityProperty());
        pieceBlack.fitHeightProperty().bind(pieceBlack.fitWidthProperty());
        pieceBlack.setEffect(blackScaleAnimation.getBlur());
        pieceWhite.setEffect(whiteScaleAnimation.getBlur());

    }

    public void push(int type, Integer number) {
        if (currentType == type) {
            return;
        }
        getChildren().clear();
        switch (type) {
            case PieceType.BLACK:
                currentImage = pieceBlack;
                currentScaleAnimation = blackScaleAnimation;
                currentOpacityAnimation = blackOpacityAnimation;
                currentType = PieceType.BLACK;
                num = new Label(number.toString());
                num.setStyle("-fx-text-fill: white");
                break;
            case PieceType.WHITE:
                currentImage = pieceWhite;
                currentScaleAnimation = whiteScaleAnimation;
                currentOpacityAnimation = whiteOpacityAnimation;
                currentType = PieceType.WHITE;
                num = new Label(number.toString());
                num.setStyle("-fx-text-fill: black");
                break;
            default:
                return;
        }
        currentScaleAnimation.setValueImmediately(width * 2.2);
        currentScaleAnimation.setToValue(width);
        currentOpacityAnimation.setValueImmediately(0.0);
        currentOpacityAnimation.setToValue(1.0);
        circle = new FadingCircle(width / 2, height / 2);
        num.setLayoutX(14);
        num.setLayoutY(11);
        setMouseTransparent(true);
        currentType = type;
        getChildren().addAll(circle, currentImage, num);
    }
    //显示指定类型的棋子（黑或白）

    public int getCurrentType() {
        return currentType;
    }
    //取当前显示的棋子类型

    public void pop() {
        if (currentType == PieceType.EMPTY) {
            return;
        }
        num.setText("");
        currentScaleAnimation.setToValue(width * 1.5);
        currentOpacityAnimation.setToValue(0.0);
        currentType = PieceType.EMPTY;
        if (circle != null) {
            getChildren().remove(circle);
        }
        setMouseTransparent(false);
    }
    //取消棋子的显示

}
