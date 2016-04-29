package sample.matrix;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.helpmath.Fraction;
import sample.helpmath.HelpMath;

import java.util.ArrayList;


/**
 * Created by golem765 on 16.04.16.
 */
public class Matrix implements Cloneable{
    protected int matrix[][];
    protected int amountColumns;
    protected int amountRows;

    public Matrix(int rows, int columns) {
        amountRows = rows;
        amountColumns = columns;
        matrix = new int[rows][columns];
        reset();
    }

    public Matrix(int[][] matrix)
    {
        this.matrix = matrix;
        this.amountRows = matrix.length;
        this.amountColumns = matrix[1].length;
    }

    public int getAmountColumns() {
        return amountColumns;
    }

    public int getAmountRows() {
        return amountRows;
    }

    public void addLine_i_ToLine_j_WithMultiplier(int i, int j, double multiplier)
    {
        for(int k = 0; k < getAmountColumns(); k++)
        {
            matrix[i][k] += (matrix[j][k]*multiplier);
        }
    }

    public void addColumn_i_ToColumn_j_WithMultiplier(int i, int j, double multiplier)
    {
        for(int k = 0; k < getAmountRows(); k++)
        {
            matrix[k][i] += (matrix[k][j]*multiplier);
        }
    }

    public void SwapLines(int i, int j)
    {
        for(int k = 0; k < getAmountRows(); k++)
        {
            int tmp = matrix[i][k];
            matrix[i][k] = matrix[j][k];
            matrix[j][k] = tmp;
        }
    }

    public void SwapColumns(int i, int j)
    {
        for(int k = 0; k < getAmountColumns(); k++)
        {
            int tmp = matrix[k][i];
            matrix[k][i] = matrix[k][j];
            matrix[k][j] = tmp;
        }
    }

    public void matrixView(Stage stage, Button... buttons)
    {
        GridPane pane = new GridPane();
        pane.setVgap(10);
        pane.setHgap(20);
        pane.setPadding(new Insets(20, 20, 20, 20));
        stage.setTitle("Matrix");
        Text[][] values = new Text[getAmountRows()][getAmountColumns()];
        for(int i = 0; i < getAmountRows(); i++)
        {
            for(int j = 0; j < getAmountColumns(); j++)
            {
                values[i][j] = new Text(String.format("%d",matrix[i][j]));
                pane.add(values[i][j], j, i);
            }
        }
        int amount = getAmountColumns()+1;
        int i = 0;
        for(Button button : buttons)
        {
            pane.add(button, amount, i);
            i++;
        }
        stage.setScene(new Scene(pane));
        stage.show();
    }

    private void reset()
    {
        for(int i = 0; i < getAmountRows(); i++)
        {
            for(int j = 0; j < getAmountColumns(); j++)
            {
                matrix[i][j] = 0;
            }
        }
    }

    public int[][] getMatrix()
    {
        return matrix;
    }

    public void multiplyRight(Matrix other)
    {
        int[][] ret = new int[other.getAmountRows()][other.getAmountColumns()];
        if(other.getAmountRows()!=getAmountColumns())
            throw new IllegalArgumentException("Incompatible matrix' size");
        for(int i = 0; i < getAmountRows(); i++)
        {
            for(int j = 0; j < other.getAmountColumns(); j++)
            {
                int value = 0;
                for(int j1 = 0; j1 < getAmountColumns(); j1++)
                {
                    value += matrix[i][j1]*other.getMatrix()[j1][j];
                }
                ret[i][j] = value;
            }
        }
        this.matrix = ret;
    }

    /**
     * This is probably works wrong.
     * @param other
     */
    public void multiplyRight(int[][] other)
    {
        int[][] ret = new int[getAmountRows()][getAmountColumns()];
        if(other.length!=getAmountColumns())
            throw new IllegalArgumentException("Incompatible matrix' size");
        for(int i = 0; i < getAmountRows(); i++)
        {
            for(int j = 0; j < getAmountColumns(); j++)
            {
                int value = 0;
                for(int j1 = 0; j1 < getAmountColumns(); j1++)
                {
                    value += matrix[i][j1]*other[j1][j];
                }
                ret[i][j] = value;
            }
        }
        this.matrix = ret;
    }

    public int getRank()
    {
        int rank = getAmountRows();
        Fraction[][] tmpf = new Fraction[getAmountRows()][getAmountColumns()];
        int[][] tmp = getIndependentInsideMatrix();
        for(int i = 0; i < getAmountRows(); i++)
        {
            for(int j = 0; j < getAmountColumns(); j++)
            {
                tmpf[i][j] = new Fraction(tmp[i][j]);
            }
        }
        tmpf = getRowEchelonForm(tmpf);
        for(int i = 0; i < getAmountRows(); i++)
        {
            if(tmpf[i][i].toInt()==0)
                rank--;
        }
        return rank;
    }

    public int[] getRow(int i)
    {
        int[] ret = new int[getAmountColumns()];
        for(int i1 = 0; i1 < getAmountColumns(); i1++)
        {
            ret[i1] = matrix[i][i1];
        }
        return ret;
    }

    /**
     * This is not working, only for the easiest matrix but not for all
     * @return rank of this matrix
     */
    public int getRank0()
    {
        int[][] tmp = mostPossibelR_EForm();
        int rank = getAmountRows();
        for(int i = 0; i < getAmountRows(); i++)
        {
            int[] iRow = tmp[i];
            if(HelpMath.isNullRow(iRow))
            {
                rank--;
                continue;
            }
            for(int i1 = i+1; i1 < getAmountRows(); i1++)
            {
                int[] i1Row = tmp[i1];
                HelpMath.normalize(iRow);
                HelpMath.normalize(i1Row);
                boolean ran = true;
                for(int j = 0; j < iRow.length; j++)
                {
                    if(iRow[j]!=i1Row[j])
                    {
                        ran = false;
                    }
                }
                if(ran)
                {
                    rank--;
                    break;
                }
            }
        }
        return rank;
    }

    /**
     * Need to deeply change it in order to make public
     * so only private now.
     * Also speed is very-very bad.
     * @param tmpf - actually matrix from which we make this r-e form.
     * @return r-e form of given matrix.
     */
    private Fraction[][] getRowEchelonForm(Fraction[][] tmpf)
    {
        for(int i = 0; i < getAmountRows(); i++)
        {
            for(int j = 0; j < getAmountRows(); j++)
            {
                if(i==j)
                    continue;
                if(tmpf[j][i].isNull())
                    continue;
                if(tmpf[i][i].isNull())
                    continue;
                Fraction diag = tmpf[i][i];
                Fraction koef = tmpf[j][i].divide(diag);
                for(int j1 = 0; j1 < getAmountColumns(); j1++)
                {
                    tmpf[j][j1]=tmpf[j][j1].substrate(koef.multiply(tmpf[i][j1]));
                    tmpf[i][j1]=tmpf[i][j1].divide(diag);
                    Fraction.normalize(tmpf[j][j1]);
                    Fraction.normalize(tmpf[i][j1]);
                }
            }
        }
        for(int i = 0; i < getAmountRows(); i++)
        {
            if(tmpf[i][i].isInt()&&tmpf[i][i].toInt()==-1) {
                for (int j = 0; j < getAmountColumns(); j++) {
                    tmpf[i][j] = tmpf[i][j].multiply(-1);
                }
            }
        }
        for(int i = 0; i < getAmountRows(); i++)
        {
            for (int j = 0; j < getAmountColumns(); j++) {
                System.out.print(tmpf[i][j] + "  ");
            }
            System.out.println();
        }
        return tmpf;
    }

    private int[][] mostPossibelR_EForm()
    {
        int[][] tmp = getIndependentInsideMatrix();
        for(int i = 0; i < getAmountRows(); i++)
        {
            HelpMath.normalize(tmp[i]);
        }
        ArrayList<Integer> exclude = new ArrayList<>();
        int am = 0;
        do {
            for (int i = 0; i < getAmountRows(); i++) {
                if (exclude.contains(i))
                    continue;
                for (int j = 0; j < getAmountColumns(); j++) {
                    if (tmp[i][j] == 1) {
                        exclude.add(i);
                        for (int i1 = 0; i1 < getAmountRows(); i1++) {
                            int koef = tmp[i1][j];
                            for (int j1 = 0; j1 < getAmountColumns(); j1++) {
                                if (i1 != i) {
                                    tmp[i1][j1] -= tmp[i][j1] * koef;
                                }
                            }
                        }
                    }
                }
            }
        }
        while(exclude.size()>=getAmountRows()||++am > 2);
        return tmp;
    }
    public int[][] getIndependentInsideMatrix()
    {
        int[][] ret = new int[getAmountRows()][getAmountColumns()];
        for(int i = 0; i < getAmountRows(); i++) {
            for (int j = 0; j < getAmountColumns(); j++) {
                ret[i][j] = matrix[i][j];
            }
        }
        return ret;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        super.clone();
        Matrix ret = new Matrix(getAmountRows(), getAmountColumns());
        ret.matrix = getIndependentInsideMatrix();
        return ret;
    }
}
