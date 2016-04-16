package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Main extends Application {
    private static Matrix matrix;
    private static Stage JForm;
    private static Stage MM;
    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Algebra helper");
        JForm = new Stage();
        MM = new Stage();
        GridPane rootSP = new GridPane();
        rootSP.setAlignment(Pos.CENTER);
        rootSP.setHgap(10);
        rootSP.setVgap(10);
        rootSP.setPadding(new Insets(25, 25, 25, 25));
        Button btn = new Button();
        Button btn1 = new Button();
        btn1.setVisible(false);
        btn1.setText("Change matrix");
        Text welcome = new Text("Welcome");
        welcome.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        rootSP.add(welcome, 0, 0, 2, 1);
        Label size = new Label("Enter size of matrix you will be using: ");
        rootSP.add(size, 0, 1);
        final TextField sizeI = new TextField();
        rootSP.add(sizeI, 1, 1);
        Label eigenvalue = new Label("Enter matrix' eigenvalue: ");
        rootSP.add(eigenvalue, 0, 2);
        final TextField input1 = new TextField();
        rootSP.add(input1, 1, 2);
        Label jordanBlocks = new Label("Enter length of jordan chains divided by ',': ");
        rootSP.add(jordanBlocks, 0, 3);
        final TextField input2 = new TextField();
        rootSP.add(input2, 1, 3);
        btn.setText("Build Jordan normal form");
        final Text success = new Text();
        Label amountOperations = new Label("Enter amount of changes applied to matrix: ");
        final TextField operations = new TextField(60+"");
        amountOperations.setVisible(false);
        operations.setVisible(false);
        rootSP.add(amountOperations, 0, 4);
        rootSP.add(operations, 1, 4);
        rootSP.add(success, 1, 7);
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                btn1.setText("Change matrix");
                success.setText("");
                try {
                    MM.close();
                    int size = Integer.parseInt(sizeI.getText());
                    if(size <= 0)
                    {
                        throw new NumberFormatException();
                    }
                    matrix = new Matrix(size);
                    matrix.initJordanForm(Double.parseDouble(input1.getText()), parseInput(input2.getText()));
                    matrix.matrixView(JForm);
                    btn1.setVisible(true);
                    amountOperations.setVisible(true);
                    operations.setVisible(true);
                }
                catch (NumberFormatException e)
                {
                    success.setFill(Color.TOMATO);
                    success.setText("Bad input. Please, try again.");
                }
                catch (IllegalArgumentException ie)
                {
                    success.setFill(Color.TOMATO);
                    success.setText("Wrong amount of Jordan chains. Please, try again.");
                }

            }
        });
        btn1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Operator operator = new Operator(matrix);
                try {
                    operator.PerformOperations(Integer.parseInt(operations.getText()));
                    matrix.matrixView(MM);
                    btn1.setText("Change matrix again");
                    btn.setText("Reset matrix, or build another");
                }
                catch (NumberFormatException e)
                {
                    success.setFill(Color.TOMATO);
                    success.setText("Wrong amount of operations, please, try again");
                }
                matrix.resetToJForm();
            }
        });
        primaryStage.setScene(new Scene(rootSP, 600, 300));
        rootSP.add(btn, 1, 5);
        rootSP.add(btn1, 1, 6);
        primaryStage.show();

    }

    public static Stage getJForm() {
        return JForm;
    }

    public static Stage getMM() {
        return MM;
    }

    public static void main(String[] args)
    {
        launch(args);
    }

    int[] parseInput(String text)
    {
        String[] tm = text.split(", ?");
        int[] ret = new int[tm.length];
        for(int i = 0; i < tm.length; i++)
        {
            ret[i] = Integer.parseInt(tm[i]);
        }
        return ret;
    }
}
