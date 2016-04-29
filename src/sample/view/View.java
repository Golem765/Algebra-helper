package sample.view;

import javafx.stage.Stage;
import sample.Controller;

/**
 * Created by golem765 on 17.04.16.
 */
public interface View {
    void setController(Controller controller);

    void start(Stage primaryStage)throws Exception;
}
