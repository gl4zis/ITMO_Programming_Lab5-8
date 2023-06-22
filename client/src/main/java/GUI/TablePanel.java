package GUI;

import commands.CommandType;
import exceptions.IncorrectInputException;
import network.Request;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TablePanel extends BasePanel {
    private final JScrollPane pane;
    private final int fontSize = 12;
    private final JTable table;
    private final JTextField filterField;
    private final WarningLabel warn;
    private final TableStreamFilter filter;
    private TableStreamSorter sorter;

    public TablePanel(MyFrame parent) {
        super(parent);
        table = new JTable(new MyTableModel(parent));
        sorter = new TableStreamSorter((MyTableModel) table.getModel());
        filter = new TableStreamFilter();
        table.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, fontSize));
        table.getTableHeader().setOpaque(false);
        table.setFillsViewportHeight(true);
        pane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        pane.getVerticalScrollBar().setUI(new MyScrollBarUI(parent, false));
        pane.setOpaque(false);
        filterField = new CustomTextField(parent, CustomTextField.Size.SMALL, "table.filter", false);
        warn = new WarningLabel(parent, "Incorrect statement!");
        table.getColumnModel().getColumn(7).setCellEditor(new DefaultCellEditor(CustomComboBox.getColorBox(parent)));
        table.getColumnModel().getColumn(8).setCellEditor(new DefaultCellEditor(CustomComboBox.getCharacterBox(parent)));

        table.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int ind = table.getTableHeader().columnAtPoint(e.getPoint());
                sorter.clickOnColumn(ind);
            }
        });
        fill();
        setWidth();
    }

    private void fill() {
        JPanel southPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setBackground(parent.getSettings().getColors().get("mainColor"));
            }
        };
        fillSouthPanel(southPanel);

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

    private void fillSouthPanel(JPanel southPanel) {
        CustomButton refresh = new CustomButton(parent, CustomButton.Size.SMALL, "table.refresh", true) {
            @Override
            protected void click() {
                refresh();
            }
        };
        CustomButton remove = new CustomButton(parent, CustomButton.Size.TINY, "table.remove", false) {
            @Override
            protected void click() {
                removeRow();
            }
        };
        southPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 0.4;
        southPanel.add(MyFrame.getSpacer(), constraints);
        constraints.weightx = 0;
        constraints.weighty = 0.1;
        southPanel.add(MyFrame.getSpacer(), constraints);
        constraints.weighty = 0;
        constraints.gridy = 1;
        constraints.gridx = 1;
        southPanel.add(warn, constraints);
        constraints.gridy = 2;
        constraints.gridx = 1;
        southPanel.add(filterField, constraints);
        constraints.gridy = 3;
        constraints.gridx = 1;
        constraints.weighty = 0.1;
        southPanel.add(MyFrame.getSpacer(), constraints);
        constraints.gridx = 2;
        constraints.weighty = 0;
        constraints.weightx = 0.1;
        southPanel.add(MyFrame.getSpacer(), constraints);
        constraints.weightx = 0;
        constraints.gridy = 1;
        constraints.gridx = 3;
        constraints.gridheight = 2;
        southPanel.add(refresh, constraints);
        constraints.gridheight = 1;
        constraints.gridx = 4;
        constraints.weightx = 0.1;
        southPanel.add(MyFrame.getSpacer(), constraints);
        constraints.gridx = 5;
        constraints.gridy = 2;
        constraints.weightx = 0;
        southPanel.add(remove, constraints);
        constraints.gridx = 6;
        constraints.weightx = 0.4;
        southPanel.add(MyFrame.getSpacer(), constraints);
    }


    private void setWidth() {
        FontMetrics metrics = table.getFontMetrics(table.getFont());
        for (int col = 0; col < table.getColumnCount(); col++) {
            TableColumn column = table.getColumn(((MyTableModel) table.getModel()).getHeader()[col]);
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

    private void refresh() {
        table.setModel(new MyTableModel(parent));
        filter();
        sorter = new TableStreamSorter((MyTableModel) table.getModel(), sorter.getColInd(), sorter.getStatus());
    }

    private void removeRow() {
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

    private void filter() {
        try {
            if (!filterField.getText().trim().isEmpty())
                filter.filter(filterField.getText(), (MyTableModel) table.getModel());
            warn.hideMessage();
        } catch (IncorrectInputException e) {
            warn.showMessage();
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
}
