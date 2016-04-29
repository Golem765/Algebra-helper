package sample.model;

import javafx.stage.Stage;
import sample.matrix.Eigenvalue;
import sample.matrix.LinearOperator;
import sample.matrix.Operator;
import sample.view.View;

import java.util.HashMap;

/**
 * Created by golem765 on 17.04.16.
 */
public class Model {
    private View view;
    private LinearOperator matrix;
    private Operator operator;

    public Model(View view)
    {
        this.view = view;
    }

    public void changeMatrix(int amount)
    {
        operator.PerformOperations(amount);
    }

    public void initMatrix(int size)
    {
        matrix = new LinearOperator(size);
        operator = new Operator(matrix);
    }

    public HashMap<Eigenvalue, LinearOperator> createEDependentMatrixs()
    {
        HashMap<Eigenvalue, LinearOperator> retM = new HashMap<>();
        for(Eigenvalue eigenvalue : matrix.getEigenvalues())
        {
            retM.put(eigenvalue, matrix.substrateEVFromDiagonal(eigenvalue));
        }
        return retM;
    }

    public void resetMatrix()
    {
        matrix.resetToJForm();
    }
    public void resetOperator(){matrix.resetToBaseOperator();}

    public void powerMatrix(int n)throws CloneNotSupportedException
    {
        operator.powerMatrix(n);
    }

    public void showMatrix(Stage stage)
    {
        matrix.matrixView(stage);
    }

    public void initEigenvalues(HashMap<String, String> eigenvalues)throws NumberFormatException
    {
        matrix.initEigenvalues(eigenvalues);
        matrix.initJordanForm();
    }

}
