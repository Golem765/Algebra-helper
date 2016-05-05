package sample;

import sample.matrix.Matrix;

/**
 * Created by Golem765 on 05.05.2016.
 */
public class ConsoleHelper {
    public static void printToConsole(Matrix matrix)
    {
        for(int i = 0; i < matrix.getAmountRows(); i++) {
            for (int j = 0; j < matrix.getAmountColumns(); j++) {
                if (j == 0)
                    System.out.print(matrix.getMatrix()[i][j]);
                else
                    System.out.print(" & " + matrix.getMatrix()[i][j]);
            }
            System.out.print(" \\\\ ");
        }
        System.out.println();
    }
}
