package sample.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.Controller;
import sample.matrix.Eigenvalue;
import sample.matrix.LinearOperator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by golem765 on 17.04.16.
 */
public class MatrixView implements View {

    private Controller controller;
    private static final Text COPYRIGHT = new Text("made by Golem765");

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    private Stage JForm;
    private Stage MM = new Stage();
    private final HashMap<Eigenvalue, Stage> stages = new HashMap<>();

    @Override
    public void start(Stage primaryStage)throws Exception
    {
        final Text success = new Text();
        primaryStage.setTitle("Algebra helper");
        JForm = new Stage();
        GridPane rootSP = new GridPane();
        rootSP.setAlignment(Pos.CENTER);
        rootSP.setHgap(15);
        rootSP.setVgap(15);
        rootSP.setPadding(new Insets(25, 25, 25, 25));
        Button changem = new Button("Change");
        Button eigen = new Button("Input eigenvalues");
        Button btn1 = new Button();
        /*Button power = new Button("Power");
        Label powerM = new Label("Enter number you wish to power matrix to:");
        TextField powerm = new TextField();
        powerM.setVisible(false);
        powerm.setVisible(false);
        power.setVisible(false);
        rootSP.add(power, 2, 5);
        rootSP.add(powerM, 0, 5);
        rootSP.add(powerm, 1, 5);
        */
        btn1.setVisible(false);
        btn1.setText("Change matrix");
        Text welcome = new Text("Welcome");
        welcome.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        Label changeOffer = new Label("Change matrix into smth more complicated: ");
        rootSP.add(changeOffer, 0, 4);
        changeOffer.setVisible(false);
        changem.setVisible(false);
        rootSP.add(welcome, 0, 0, 2, 1);
        Label size = new Label("Enter size of matrix you will be using: ");
        rootSP.add(size, 0, 1);
        final TextField sizeI = new TextField();
        rootSP.add(sizeI, 1, 1);
        Label eigenvalue = new Label("Enter amount of matrix' eigenvalue: ");
        rootSP.add(eigenvalue, 0, 2);
        final TextField input1 = new TextField();
        rootSP.add(input1, 1, 2);
        Label amountOperations = new Label("Enter amount of changes applied to matrix: ");
        final TextField operations = new TextField(60+"");
        amountOperations.setVisible(false);
        operations.setVisible(false);
        rootSP.add(eigen, 2, 2);
        eigen.setOnAction(event -> {
            changem.setText("Change");
            controller.initMatrix(Integer.parseInt(sizeI.getText()));
            initEigenvalueWindow(Integer.parseInt(input1.getText()), JForm);
            changeOffer.setVisible(true);
            changem.setVisible(true);
            amountOperations.setVisible(true);
            operations.setVisible(true);
        });
        rootSP.add(amountOperations, 0, 3);
        rootSP.add(operations, 1, 3);
        rootSP.add(success, 1, 7);
        changem.setOnAction(event -> {
            if(changem.getText().equals("Reset and change"))
                controller.resetMatrix();
            success.setText("");
            MM.close();
            controller.changeMatrix(Integer.parseInt(operations.getText()));
            controller.showMatrix(MM);
            changem.setText("Reset and change");
            int amount = 0;
            int i = 0;
            for(Button button : getMatrixs(stages))
            {
                rootSP.add(button, amount, 5+i);
                amount++;
                if(amount > 3) {
                    amount = 0;
                    i++;
                }
            }
            /*
            powerM.setVisible(true);
            powerm.setVisible(true);
            power.setVisible(true);
            */
        });
        /*
        power.setOnAction(event->
        {
            try {
                MM.close();
                controller.powerMatrix(Integer.parseInt(powerm.getText()));
                controller.showMatrix(MM);
            }
            catch (Exception e)
            {
                success.setText("Error occurred on trying to power matrix.");
            }
            controller.resetOperator();
        });
        */
        primaryStage.setScene(new Scene(rootSP));
        rootSP.add(changem, 1, 4);
        rootSP.add(btn1, 1, 6);
        primaryStage.show();
    }

    private int[] parseInput(String text)
    {
        String[] tm = text.split(", ?");
        int[] ret = new int[tm.length];
        for(int i = 0; i < tm.length; i++)
        {
            ret[i] = Integer.parseInt(tm[i]);
        }
        return ret;
    }

    private void initEigenvalueWindow(int amount, Stage stage)
    {
        stage.setTitle("Building Jordan Normal Form");
        GridPane pane = new GridPane();
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setPadding(new Insets(25, 25, 25, 25));
        Text text = new Text("Please, input eigenvalues in the left fields,\nand length of it's chains or block in the right one divided by ','");
        text.setFont(new Font("Arial", 15));
        pane.add(text, 0, 0);
        Text e = new Text("eigenvalue:");
        Text c = new Text("it's chains length:");
        pane.add(e, 0, 2);
        pane.add(c, 1, 2);
        stage.setScene(new Scene(pane));
        HashMap<TextField, TextField> ret = new HashMap<>();
        for(int i = 0; i < amount; i++)
        {
            TextField field = new TextField();
            TextField field2 = new TextField();
            pane.add(field, 0, 3 + i);
            pane.add(field2, 1, 3 + i);
            ret.put(field, field2);
        }
        Button ok = new Button("OK");
        pane.add(ok, 0, 3 + amount + 2);
        HashMap<String, String> eigenvalues = new HashMap<>();
        ok.setOnAction(event -> {
            for(Map.Entry<TextField, TextField> pair : ret.entrySet())
            {
                String s = pair.getKey().getText();
                String s1 = pair.getValue().getText();
                eigenvalues.put(s, s1);

            }
            controller.initEigenvalues(eigenvalues);
            controller.showMatrix(MM);
            stage.close();
        });
        stage.show();
    }

    private ArrayList<Button> getMatrixs(HashMap<Eigenvalue, Stage> stages)
    {
        ArrayList<Button> ret = new ArrayList<>();
        stages.clear();
        for(Map.Entry<Eigenvalue, LinearOperator> pair :controller.createEDependentMatrixs().entrySet())
        {
            stages.put(pair.getKey(), new Stage());
            Button btn = new Button("λ = "+pair.getKey().getValue());
            Button multiply = new Button("Power");
            Button reset = new Button("Reset");
            Button rank = new Button("Get rank");
            rank.setOnAction(event ->
            {
                int r = pair.getValue().getRank0();
                rank.setText(r+"");
            });
            reset.setOnAction(event ->
            {
                stages.get(pair.getKey()).close();
                pair.getValue().resetToBaseOperator();
                pair.getValue().matrixView(stages.get(pair.getKey()), multiply, reset, rank);
                rank.setText("Get rank");
            });
            multiply.setOnAction(event ->{
                stages.get(pair.getKey()).close();
                pair.getValue().multiplyRight(pair.getValue().getBaseOperator());
                pair.getValue().matrixView(stages.get(pair.getKey()), multiply, reset, rank);
                rank.setText("Get rank");
            });
            btn.setOnAction(event -> pair.getValue().matrixView(stages.get(pair.getKey()), multiply, reset, rank));
            ret.add(btn);
        }
        return ret;
    }
}
