package sample;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 * Created by golem765 on 16.04.16.
 */
class Matrix {
    private double matrix[][];
    private double jForm[][];
    private int size;

    Matrix(int n) {
        this.matrix = new double[n][n];
        this.jForm = new double[n][n];
        this.size = n;
        reset();
    }

    void initJordanForm(double ownValue, int... jSize)
    {
        int possible = 0;
        for(int i : jSize)
        {
            possible += i;
        }
        if(possible!=getSize())
        {
            throw new IllegalArgumentException();
        }
        int pos = 0;
        for(int jLength : jSize) {
            if(jLength == 1)
            {
                matrix[pos][pos] = ownValue;
                pos += 1;
            }
            else {
                for (int i = pos; i < pos + jLength; i++) {
                    for (int j = pos; j < pos + jLength; j++) {
                        if (i == j) {
                            matrix[i][j] = ownValue;
                        } else if (j == (i + 1)) {
                            matrix[i][j] = 1;
                        }
                    }
                }
                pos += jLength;
            }
        }
        for(int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                jForm[i][j] = matrix[i][j];
            }
        }
    }

    void addLine_i_ToLine_j_WithMultiplier(int i, int j, int multiplier)
    {
        for(int k = 0; k < getSize(); k++)
        {
            matrix[i][k] += (matrix[j][k]*multiplier);
        }
    }

    void addColumn_i_ToColumn_j_WithMultiplier(int i, int j, int multiplier)
    {
        for(int k = 0; k < getSize(); k++)
        {
            matrix[k][i] += (matrix[k][j]*multiplier);
        }
    }

    void SwapLines(int i, int j)
    {
        for(int k = 0; k < getSize(); k++)
        {
            double tmp = matrix[i][k];
            matrix[i][k] = matrix[j][k];
            matrix[j][k] = tmp;
        }
    }

    void SwapColumns(int i, int j)
    {
        for(int k = 0; k < getSize(); k++)
        {
            double tmp = matrix[k][i];
            matrix[k][i] = matrix[k][j];
            matrix[k][j] = tmp;
        }
    }

    int getSize() {
        return size;
    }

    void matrixView(Stage stage)
    {
        stage.setTitle("Matrix");
        Text[][] values = new Text[getSize()][getSize()];
        GridPane pane = new GridPane();
        pane.setVgap(10);
        pane.setHgap(20);
        pane.setPadding(new Insets(20, 20, 20, 20));
        for(int i = 0; i < getSize(); i++)
        {
            for(int j = 0; j < getSize(); j++)
            {
                values[i][j] = new Text(String.format("%.0f",matrix[i][j]));
                pane.add(values[i][j], j, i);
            }
        }
        stage.setScene(new Scene(pane));
        stage.show();
    }

    private void reset()
    {
        for(int i = 0; i < getSize(); i++)
        {
            for(int j = 0; j < getSize(); j++)
            {
                matrix[i][j] = 0;
            }
        }
    }

    void resetToJForm() {
        for(int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = jForm[i][j];
            }
        }
    }
}
