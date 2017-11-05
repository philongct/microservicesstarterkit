package lnguyen.utils.tabular;

import java.util.ArrayList;
import java.util.List;

public interface Matrix {
    Matrix addRow(Row row);
    Row newRow();

    String getContent();
}
