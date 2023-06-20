package GUI;

import commands.CommandType;
import dragons.Color;
import dragons.*;
import network.Request;
import parsers.MyDate;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.Collection;
import java.util.Date;

public class TablePanel extends BasePanel {
    private final JScrollPane pane;
    private final int fontSize = 12;
    private final JTable table;
    private final JTextField filter;

    public TablePanel(MyFrame parent) {
        super(parent);
        table = new JTable(new MyTableModel(parent));
        table.setAutoCreateRowSorter(true);
        table.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, fontSize));
        table.getTableHeader().setOpaque(false);
        table.setFillsViewportHeight(true);
        pane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        pane.getVerticalScrollBar().setUI(new MyScrollBarUI(parent, false));
        pane.setOpaque(false);
        filter = new CustomTextField(parent, CustomTextField.Size.MEDIUM, "table.filter", false);
        fill();
        setWidth();
    }

    private void setWidth() {
        FontMetrics metrics = table.getFontMetrics(table.getFont());
        for (int col = 0; col < table.getColumnCount(); col++) {
            TableColumn column = table.getColumn(((MyTableModel) table.getModel()).header[col]);
            String str = column.getHeaderValue().toString();
            int maxWidth = metrics.stringWidth(str);
            for (int row = 0; row < Math.min(table.getRowCount(), 20); row++) {
                Object value = table.getModel().getValueAt(row, col);
                if (value != null) {
                    str = value.toString();
                    int width = metrics.stringWidth(str);
                    if (maxWidth < width)
                        maxWidth = width;
                }
            }
            column.setPreferredWidth((int) (maxWidth * 1.3));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        double k = parent.getKf();
        if (table.getFont().getSize() != (int) (k * fontSize)) {
            table.setFont(table.getFont().deriveFont((float) (k * fontSize)));
            table.getTableHeader().setFont(table.getFont().deriveFont((float) (k * fontSize)));
        }
        pane.setBackground(parent.getSettings().getColors().get("mainColor"));
        table.setRowHeight((int) (fontSize * 1.5 * k));
        table.setBackground(parent.getSettings().getColors().get("mainColor"));
        table.setForeground(parent.getSettings().getColors().get("fontColor"));
        table.getTableHeader().setBackground(parent.getSettings().getColors().get("mainColor"));
        table.getTableHeader().setForeground(parent.getSettings().getColors().get("fontColor"));

    }

    private void refresh() {
        parent.setStatus(PageStatus.TABLE);
    }

    private void fill() {
        JPanel southPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setBackground(parent.getSettings().getColors().get("mainColor"));
            }
        };
        southPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 20));
        southPanel.add(filter);
        southPanel.add(new CustomButton(parent, CustomButton.Size.MEDIUM, "table.refresh", true) {
            @Override
            protected void click() {
                refresh();
            }
        });
        southPanel.add(new CustomButton(parent, CustomButton.Size.SMALL, "table.remove", false) {
            @Override
            protected void click() {
                int row = table.getSelectedRow();
                if (row > -1) {
                    String owner = (String) table.getValueAt(row, 10);
                    if (parent.getSettings().getUser() != null &&
                            owner.equals(parent.getSettings().getUser().getLogin())) {
                        int id = (Integer) table.getValueAt(row, 0);
                        Request removeId = new Request(CommandType.REMOVE_BY_ID, id,
                                null, parent.getSettings().getUser());
                        parent.getSettings().tryConnect(removeId);
                        refresh();
                    }
                }
            }
        });

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 0.98;
        constraints.weighty = 0.94;
        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.fill = GridBagConstraints.BOTH;
        add(pane, constraints);
        constraints.weighty = 0.05;
        constraints.gridy = 1;
        constraints.gridx = 0;
        add(southPanel, constraints);
        constraints.gridy = 2;
        constraints.gridx = 0;
        constraints.weighty = 0.01;
        constraints.weightx = 0;
        add(MyFrame.getSpacer(), constraints);
        constraints.gridy = 0;
        constraints.gridx = 1;
        constraints.weighty = 0;
        constraints.weightx = 0.02;
        add(MyFrame.getSpacer(), constraints);
    }

    class MyTableModel extends AbstractTableModel {

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
                case 1, 7, 8, 10 -> String.class;
                case 2 -> Double.class;
                case 3, 9 -> Float.class;
                case 4 -> MyDate.class;
                case 5 -> Long.class;
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
                if (col == 7) {
                    boolean flag = true;
                    for (Color color : Color.values())
                        if (value.equals(color.name())) {
                            flag = false;
                            break;
                        }
                    if (flag)
                        return;
                }
                if (col == 8) {
                    boolean flag = true;
                    for (DragonCharacter character : DragonCharacter.values())
                        if (value.equals(character.name())) {
                            flag = false;
                            break;
                        }
                    if (flag)
                        return;
                }
                data[row][col] = value;
                Object oldValue = data[row][col];

                int id = (Integer) data[row][0];
                String name = (String) data[row][1];
                double x = (Double) data[row][2];
                float y = (Float) data[row][3];
                Date date = ((MyDate) data[row][4]).getDate();
                long weight = (Long) data[row][5];
                Color color = Color.getByName((String) data[row][7]);
                DragonCharacter character = DragonCharacter.getByName((String) data[row][8]);
                float eyes = (Float) data[row][9];
                Coordinates coordinates = new Coordinates(x, y);
                DragonHead head = new DragonHead(eyes);
                Dragon dragon = new Dragon(id, name, coordinates, date, weight, color, character, head, parent.getSettings().getUser());

                if (data[row][6] != null)
                    dragon.setAge((Integer) data[row][6]);
                Request request = new Request(CommandType.UPDATE, id, dragon, parent.getSettings().getUser());
                parent.getSettings().tryConnect(request);
                if (!parent.getSettings().isConnected()) {
                    data[row][col] = oldValue;
                    return;
                }
                fireTableCellUpdated(row, col);
            }
        }
    }
}
