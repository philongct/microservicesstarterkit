package lnguyen.utils.tabular;

import java.util.ArrayList;
import java.util.List;

public class CsvMatrix implements Matrix {

    private List<Row> rows = new ArrayList<>();

    private String delimiter;

    public CsvMatrix(String delimiter) {
        this.delimiter = delimiter;
    }

    @Override
    public CsvMatrix addRow(Row row) {
        rows.add(row);
        return this;
    }

    @Override
    public Row newRow() {
        Row row = new Row();
        addRow(row);
        return row;
    }

    @Override
    public String getContent() {
        StringBuilder sb = new StringBuilder();
        rows.forEach(row -> {
            row.getCells().forEach(cell -> sb.append(cell.getContent()).append(delimiter));
            sb.setLength(sb.length() - 2);
            sb.append("\r\n");
        });

        return sb.toString();
    }
}
