package lnguyen.utils.tabular;

import java.util.ArrayList;
import java.util.List;

public class Row {
    private List<Cell> cells = new ArrayList<>();

    public void addCell(Cell cell) {
        cells.add(cell);
    }

    public Cell newCell() {
        Cell cell = new Cell();
        addCell(cell);

        return cell;
    }

    public List<Cell> getCells() {
        return cells;
    }
}
