package gomoku;

import gomoku.ui.frameworks.ContentPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Launcher extends Application {
    private Scene contentScene;
    private ContentPane contentPane;
    private static Launcher self;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        ConfigService.configLoad();
        System.out.println(" _____                       _          \n" +
                "|  __ \\                     | |         \n" +
                "| |  \\/ ___  _ __ ___   ___ | | ___   _ \n" +
                "| | __ / _ \\| '_ ` _ \\ / _ \\| |/ / | | |                 __      __        __    /     \n" +
                "| |_\\ \\ (_) | | | | | | (_) |   <| |_| |           |\\ | /  \\    |__) |  | / _`  /    \n" +
                " \\____/\\___/|_| |_| |_|\\___/|_|\\_\\\\__,_|           | \\| \\__/    |__) \\__/ \\__> .             v1.1");
        self = this;
        this.primaryStage = primaryStage;
        primaryStage.setWidth(ConfigService.config.getViewPortWidth());
        primaryStage.setHeight(ConfigService.config.getViewPortHeight());
        primaryStage.setTitle("Gomoku");
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image(ConfigService.getResource("drawable/icon.png")));
        primaryStage.show();
        showContentPane();
    }
    //start入口函数

    public void showContentPane() {
        contentPane = new ContentPane();
        contentScene = new Scene(contentPane, ConfigService.viewportWidth, ConfigService.viewportHeight);
        contentScene.getStylesheets().add(ConfigService.getResource("stylesheet/root.css"));
        primaryStage.setScene(contentScene);
    }
    //播放完片头动画后调用此函数来显示游戏主界面

    public static Launcher getSelf() {
        return self;
    }
    //由于该类只会创建一个对象，故使用一个静态变量来保存唯一的对象，以便于外界访问

    public static void main(String[] args) {
        Application.launch(args);
    }
    //main入口函数
}
