package lnguyen.utils.tabular;

public class Cell {

    private Object content;

    public Cell() {}

    public Cell(Object content) {
        this.content = content;
    }

    public Cell setContent(Object content) {
        this.content = content;
        return this;
    }

    public Object getContent() {
        return content;
    }
}
