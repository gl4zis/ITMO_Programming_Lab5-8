package GUI;

import javax.swing.*;
import java.awt.*;

public class ViewPanel extends BasePanel {
    private final JPanel view;
    private final JScrollPane pane;

    public ViewPanel(MyFrame parent) {
        super(parent);
        view = new JPanel();
        view.setBackground(Color.white);
        view.setPreferredSize(new Dimension(1600, 900));
        pane = new JScrollPane(view, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        pane.setOpaque(false);
        pane.getVerticalScrollBar().setUI(new MyScrollBarUI(parent, false));
        pane.getHorizontalScrollBar().setUI(new MyScrollBarUI(parent, true));
        fill();
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
