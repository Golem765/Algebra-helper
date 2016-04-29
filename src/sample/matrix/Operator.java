package sample.matrix;

import javafx.stage.Stage;

/**
 * Created by golem765 on 16.04.16.
 */
public class Operator {
    private Matrix matrix;

    public Operator(Matrix matrix) {
        this.matrix = matrix;
    }

    public void PerformOperations(int amount)
    {
        for(int k = 0; k < amount; k++)
        {
            int multiplier = (int)(Math.random()*3)+1;
            int i = (int)(Math.random()*matrix.getAmountRows());
            int j = (int)(Math.random()*matrix.getAmountColumns());
            if(i == j)
                continue;
            int choose = (int)(Math.random()*2);
            switch (choose)
            {
                case 0:
                    OperationAddLineToLine(i, j, multiplier);
                    break;
                case 1:
                    OperationSwapLines(i, j);
                    break;
            }
        }
        if(matrix instanceof LinearOperator)
        {
            ((LinearOperator) matrix).setBaseOperator(matrix.getIndependentInsideMatrix());
        }
    }

    private void OperationAddLineToLine(int i, int j, int multiplier)
    {
        matrix.addLine_i_ToLine_j_WithMultiplier(i, j, multiplier);
        matrix.addColumn_i_ToColumn_j_WithMultiplier(j, i, -multiplier);
    }

    private void OperationSwapLines(int i, int j)
    {
        matrix.SwapLines(i, j);
        matrix.SwapColumns(j, i);
    }

    public void powerMatrix(int n)throws CloneNotSupportedException
    {
        Matrix tmp = (Matrix)matrix.clone();
        tmp.matrix = this.matrix.getIndependentInsideMatrix();
        for(int i = 1; i < n; i++)
        {
            matrix.multiplyRight(tmp);
        }
    }
}
