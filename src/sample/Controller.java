package sample;

import javafx.stage.Stage;
import sample.matrix.Eigenvalue;
import sample.matrix.LinearOperator;
import sample.matrix.Matrix;
import sample.model.Model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by golem765 on 17.04.16.
 */
public class Controller {
    private Model model;

    public Controller(Model model) {
        this.model = model;
    }

    public void changeMatrix(int amount)
    {
        model.changeMatrix(amount);
    }

    public void initEigenvalues(HashMap<String, String> eigenvalues)throws NumberFormatException
    {
        model.initEigenvalues(eigenvalues);
    }

    public HashMap<Eigenvalue, LinearOperator> createEDependentMatrixs()
    {
        return model.createEDependentMatrixs();
    }
    public void initMatrix(int size)
    {
        model.initMatrix(size);
    }

    public void resetMatrix()
    {
        model.resetMatrix();
    }
    public void resetOperator(){model.resetOperator();}

    public void powerMatrix(int n)throws CloneNotSupportedException
    {
        model.powerMatrix(n);
    }

    public Matrix getMatrix()
    {
        return model.getMatrix();
    }
    public void showMatrix(Stage stage)
    {
        model.showMatrix(stage);
    }
}
