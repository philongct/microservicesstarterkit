package lnguyen.utils.tabular.gen;

import java.util.ArrayList;
import java.util.List;

import lnguyen.utils.tabular.Matrix;
import lnguyen.utils.tabular.Row;

public class TabularContentGenerator {
    private List<DataGenerator<?>> columns = new ArrayList<>();

    private Matrix matrix;

    public TabularContentGenerator(Matrix matrix) {
        this.matrix = matrix;
    }

    public TabularContentGenerator addColumn(DataGenerator<?> dataGenerator) {
        columns.add(dataGenerator);
        return this;
    }

    public void nextLine() {
        Row row = matrix.newRow();
        columns.forEach(gen -> row.newCell().setContent(gen.nextValue()));
    }
}
