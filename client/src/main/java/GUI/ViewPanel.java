package GUI;

import dragons.Dragon;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

public class ViewPanel extends BasePanel {
    private final JPanel view;
    private final JScrollPane pane;
    private final DragoComp[] dragons;

    public ViewPanel(MyFrame parent) {
        super(parent);
        parent.getSettings().loadCollection();
        dragons = parseCollection();
        view = new JPanel();
        view.setBackground(Color.white);
        view.setPreferredSize(getViewSize());
        pane = new JScrollPane(view, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        pane.setOpaque(false);
        pane.getVerticalScrollBar().setUI(new MyScrollBarUI(parent, false));
        pane.getHorizontalScrollBar().setUI(new MyScrollBarUI(parent, true));
        fill();
    }

    private DragoComp[] parseCollection() {
        Collection<Dragon> coll = parent.getSettings().getCollection();
        if (coll == null)
            return new DragoComp[0];
        else {
            DragoComp[] dragons = new DragoComp[coll.size()];
            int counter = 0;
            for (Dragon dragon : coll) {
                dragons[counter++] = new DragoComp(dragon, parent);
            }
            return dragons;
        }
    }

    private Dimension getViewSize() {
        for (DragoComp dragon : dragons) {
           
        }
        return new Dimension(1000, 1000);
    }

    private void fill() {
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 0.98;
        constraints.weighty = 0.98;
        add(pane, constraints);
        constraints.weightx = 0.02;
        constraints.weighty = 0;
        add(MyFrame.getSpacer(), constraints);
        constraints.weighty = 0.02;
        constraints.weightx = 0;
        constraints.gridx = 0;
        constraints.gridy = 1;
        add(MyFrame.getSpacer(), constraints);
    }
}
