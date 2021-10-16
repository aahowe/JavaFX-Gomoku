package gomoku.ui.nodes;

import javafx.scene.control.ListView;
import gomoku.ConfigService;

/**
 * 悔棋栈列表框（用于实时悔棋）
 */

public class StepListView extends ListView<String> {
    public StepListView() {
        getStylesheets().add(ConfigService.getResource("stylesheet/steplist.css"));
    }
}
