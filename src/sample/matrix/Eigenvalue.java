package sample.matrix;

import java.util.ArrayList;
/**
 * Created by golem765 on 18.04.16.
 */
public class Eigenvalue {
    private Integer value;
    private ArrayList<Integer> blocksSize;

    public Eigenvalue(Integer value, ArrayList<Integer> blocksSize) {
        this.value = value;
        this.blocksSize = blocksSize;
    }

    public Integer getValue() {
        return value;
    }

    public ArrayList<Integer> getBlocksSize() {
        return blocksSize;
    }
}
