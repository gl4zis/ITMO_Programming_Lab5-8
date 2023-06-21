package GUI;

import commands.CommandType;
import dragons.*;
import network.Request;
import parsers.MyDate;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.Collection;
import java.util.Date;

public class MyTableModel extends AbstractTableModel {

    private final String[] header = new String[]{
            "ID",
            "Name",
            "X",
            "Y",
            "Creation date",
            "Weight",
            "Age",
            "Color",
            "Character",
            "Eyes count",
            "Owner"
    };
    private final MyFrame parent;
    private Object[][] data;

    public MyTableModel(MyFrame parent) {
        this.parent = parent;
        parseCollection();
    }

    private void parseCollection() {
        parent.getSettings().loadCollection();
        if (parent.getSettings().isConnected()) {
            Collection<Dragon> collection = parent.getSettings().getCollection();
            data = new Object[Math.max(collection.size(), 1)][11];
            int counter = 0;
            for (Dragon dragon : collection) {

                data[counter][0] = dragon.hashCode();
                data[counter][1] = dragon.getName();
                data[counter][2] = dragon.getCoordinates().getX();
                data[counter][3] = dragon.getCoordinates().getY();
                data[counter][4] = new MyDate(dragon.getCreationDate());
                data[counter][5] = dragon.getWeight();
                int age = dragon.getAge();
                if (age != -1)
                    data[counter][6] = age;
                else
                    data[counter][6] = null;
                data[counter][7] = dragon.getColor().name();
                data[counter][8] = dragon.getDragonCharacter().name();
                data[counter][9] = dragon.getDragonHead().getEyesCount();
                data[counter][10] = dragon.getCreator().getLogin();
                counter++;
            }
        } else data = new Object[1][11];

    }

    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public int getColumnCount() {
        return header.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }

    @Override
    public String getColumnName(int col) {
        return header[col];
    }

    @Override
    public Class getColumnClass(int col) {
        return switch (col) {
            case 0, 6 -> Integer.class;
            case 1, 10 -> String.class;
            case 2 -> Double.class;
            case 3, 9 -> Float.class;
            case 4 -> MyDate.class;
            case 5 -> Long.class;
            case 7, 8 -> JComboBox.class;
            default -> Object.class;
        };
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return col != 0 && col != 4 && col != 10;
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        if (parent.getSettings().getUser() != null &&
                parent.getSettings().getUser().getLogin().equals(data[row][10])) {
            if (col != 6 && value.toString().trim().isEmpty())
                return;
            if (col == 2 && (Double) value <= -497)
                return;
            if (col == 5 && (Long) value <= 0)
                return;
            if (col == 6 && value != null && (Integer) value <= 0)
                return;
            if (col == 9 && (Float) value < 0)
                return;
            data[row][col] = value;
            Object oldValue = data[row][col];

            int id = (Integer) getValueAt(row, 0);
            Dragon dragon = getDragon(row);

            Request request = new Request(CommandType.UPDATE, id, dragon, parent.getSettings().getUser());
            parent.getSettings().tryConnect(request);
            if (!parent.getSettings().isConnected()) {
                data[row][col] = oldValue;
                return;
            }
            fireTableCellUpdated(row, col);
        }
    }

    private Dragon getDragon(int row) {
        int id = (Integer) getValueAt(row, 0);
        String name = (String) getValueAt(row, 1);
        double x = (Double) getValueAt(row, 2);
        float y = (Float) getValueAt(row, 3);
        Date date = ((MyDate) getValueAt(row, 4)).getDate();
        long weight = (Long) getValueAt(row, 5);
        Color color = Color.getByName((String) getValueAt(row, 7));
        DragonCharacter character = DragonCharacter.getByName((String) getValueAt(row, 8));
        float eyes = (Float) getValueAt(row, 9);
        Coordinates coordinates = new Coordinates(x, y);
        DragonHead head = new DragonHead(eyes);
        Dragon dragon = new Dragon(id, name, coordinates, date, weight, color, character, head, parent.getSettings().getUser());

        if (getValueAt(row, 6) != null)
            dragon.setAge((Integer) getValueAt(row, 6));

        return dragon;
    }

    public Object[] getHeader() {
        return header;
    }

    public Object[][] getData() {
        return data;
    }

    public void setData(Object[][] newData) {
        data = newData;
        fireTableDataChanged();
    }
}
