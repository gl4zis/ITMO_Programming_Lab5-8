package GUI;

import parsers.MyDate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.stream.IntStream;

public class TableStreamSorter {

    private final MyTableModel model;
    private final ArrayList<Comparator<Object[]>> comparators;
    private int currentColumnIndex;
    private SortStatus status;

    public TableStreamSorter(MyTableModel model) {
        this.model = model;
        currentColumnIndex = 0;
        status = SortStatus.ASC;
        comparators = new ArrayList<>();
        comparators.add(Comparator.comparingInt(o -> (int) o[0]));
        comparators.add(Comparator.comparing(o -> ((String) o[1])));
        comparators.add(Comparator.comparingDouble(o -> (double) o[2]));
        comparators.add(Comparator.comparingDouble(o -> (float) o[3]));
        comparators.add(Comparator.comparing(o -> ((MyDate) o[4]).getDate()));
        comparators.add(Comparator.comparingLong(o -> (long) o[5]));
        comparators.add(Comparator.comparing(o -> Objects.requireNonNullElse((Integer) o[6], 0)));
        comparators.add(Comparator.comparing(o -> ((String) o[7])));
        comparators.add(Comparator.comparing(o -> ((String) o[8])));
        comparators.add(Comparator.comparingDouble(o -> (float) o[9]));
        comparators.add(Comparator.comparing(o -> ((String) o[10])));
        sort();
    }

    public TableStreamSorter(MyTableModel model, int colIndex, SortStatus status) {
        this(model);
        this.currentColumnIndex = colIndex;
        this.status = status;
        sort();
    }

    public void clickOnColumn(int col) {
        if (col == currentColumnIndex)
            reverse();
        else {
            status = SortStatus.ASC;
            currentColumnIndex = col;
        }
        sort();
    }

    private void reverse() {
        if (status.equals(SortStatus.ASC))
            status = SortStatus.DESC;
        else
            status = SortStatus.ASC;
    }

    public void sort() {
        Object[][] table = Arrays.stream(model.getData()).sorted(comparators.get(currentColumnIndex))
                .toArray(Object[][]::new);
        if (status.equals(SortStatus.ASC)) {
            model.setData(table);
        } else {
            model.setData(IntStream.range(0, table.length)
                    .mapToObj(i -> table[table.length - i - 1]).toArray(Object[][]::new));
        }
    }

    public int getColInd() {
        return currentColumnIndex;
    }

    public SortStatus getStatus() {
        return status;
    }

    enum SortStatus {
        ASC,
        DESC
    }
}
