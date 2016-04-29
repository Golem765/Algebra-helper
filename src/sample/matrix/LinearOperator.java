package sample.matrix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by golem765 on 18.04.16.
 */
public class LinearOperator extends Matrix {

    private int[][] jForm;
    private int [][] baseOperator;
    private ArrayList<Eigenvalue> eigenvalues = new ArrayList<>();

    public LinearOperator(int size) {
        super(size, size);
    }

    public LinearOperator(int[][] matrix) {
        super(matrix);
        this.baseOperator = getIndependentInsideMatrix();
    }

    public void initEigenvalues(HashMap<String, String> eigenvalues)throws NumberFormatException
    {
        for(Map.Entry<String, String> pair : eigenvalues.entrySet())
        {
            int value = Integer.parseInt(pair.getKey());
            ArrayList<Integer> chains = new ArrayList<>();
            for(String s : pair.getValue().split(", ?"))
            {
                chains.add(Integer.parseInt(s));
            }
            this.eigenvalues.add(new Eigenvalue(value, chains));
        }
    }

    public void initJordanForm()
    {
        int pos = 0;
        for(Eigenvalue value : eigenvalues) {
            for (int chain : value.getBlocksSize()) {
                if (chain == 1) {
                    matrix[pos][pos] = value.getValue();
                    pos += 1;
                } else {
                    for (int i = pos; i < pos + chain; i++) {
                        for (int j = pos; j < pos + chain; j++) {
                            if (i == j) {
                                matrix[i][j] = value.getValue();
                            } else if (j == (i + 1)) {
                                matrix[i][j] = 1;
                            }
                        }
                    }
                    pos += chain;
                }
            }
        }
        jForm = getIndependentInsideMatrix();
    }

    public int getSize() {
        return getAmountColumns();
    }

    public void resetToJForm() {
        for(int i = 0; i < getAmountRows(); i++) {
            for (int j = 0; j < getAmountColumns(); j++) {
                matrix[i][j] = jForm[i][j];
            }
        }
    }

    public void resetToBaseOperator()
    {
        for(int i = 0; i < getAmountRows(); i++) {
            for (int j = 0; j < getAmountColumns(); j++) {
                matrix[i][j] = getBaseOperator()[i][j];
            }
        }
    }

    public void setBaseOperator(int[][] baseOperator) {
        this.baseOperator = baseOperator;
    }

    public int[][] getBaseOperator() {
        return getIndependentInsideMatrix("base");
    }
    public int[][] getJForm() {
        return getIndependentInsideMatrix("jform");
    }

    public ArrayList<Eigenvalue> getEigenvalues() {
        return eigenvalues;
    }

    private int[][] getIndependentInsideMatrix(String name) {
        int[][] ret = new int[getAmountRows()][getAmountColumns()];
        for(int i = 0; i < getAmountRows(); i++) {
            for (int j = 0; j < getAmountColumns(); j++) {
                switch (name)
                {
                    case "jform" :
                        ret[i][j] = jForm[i][j];
                        break;
                    case "base" :
                        ret[i][j] = baseOperator[i][j];
                        break;
                }
            }
        }
        return ret;
    }

    public LinearOperator substrateEVFromDiagonal(Eigenvalue eigenvalue)
    {
        int[][] ret = getBaseOperator();
        for(int i = 0; i < getAmountRows(); i++) {
            for (int j = 0; j < getAmountColumns(); j++) {
                if(i==j)
                {
                    ret[i][j]-=eigenvalue.getValue();
                }
            }
        }
        return new LinearOperator(ret);
    }


}
