package sample;

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
            int i = (int)(Math.random()*matrix.getSize());
            int j = (int)(Math.random()*matrix.getSize());
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

    public Matrix getModifiedMatrix() {
        return matrix;
    }
}
