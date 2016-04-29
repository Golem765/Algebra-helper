package sample;

import javafx.application.Application;
import javafx.stage.Stage;
import sample.model.Model;
import sample.view.MatrixView;
import sample.view.View;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        View view = new MatrixView();
        Model model = new Model(view);
        Controller controller = new Controller(model);
        view.setController(controller);
        view.start(primaryStage);

    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
