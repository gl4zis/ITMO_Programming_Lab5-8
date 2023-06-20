package GUI;

import dragons.Dragon;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

public class ViewPanel extends BasePanel {
    private final JPanel view;
    private final JScrollPane pane;
    private final DragoComp[] dragons;
    private final int offsetX = 500;
    private int offsetY;

    public ViewPanel(MyFrame parent) {
        super(parent);
        parent.getSettings().loadCollection();
        dragons = parseCollection();
        view = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2D = setGoodQ(g);
                setPreferredSize(getViewSize());

                g2D.setStroke(new BasicStroke(3));
                g2D.setColor(Color.BLACK);
                g2D.drawLine(0, offsetY, getWidth(), offsetY);
                g2D.drawLine(offsetX, 0, offsetX, getHeight());
                g2D.setFont(new Font("Arial", Font.BOLD, 20));
                g2D.drawString("0", offsetX - 15, offsetY + 20);
            }
        };
        view.setBackground(Color.white);
        view.setLayout(null);
        setOffsetY();
        pane = new JScrollPane(view, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        pane.setOpaque(false);
        pane.getVerticalScrollBar().setUI(new MyScrollBarUI(parent, false));
        pane.getHorizontalScrollBar().setUI(new MyScrollBarUI(parent, true));
        fill();
        CustomButton goToZero = new CustomButton(parent, CustomButton.Size.SMALL, "Go to 00", true) {
            {
                setLocation(pane.getWidth() - getWidth(), pane.getHeight() - getHeight());
            }

            @Override
            protected void click() {
                pane.getVerticalScrollBar().setValue((view.getHeight() - pane.getHeight()) / 2);
                pane.getHorizontalScrollBar().setValue((view.getWidth() - pane.getWidth()) / 2);
            }
        };
        showDragons();
        view.add(goToZero);
    }

    private DragoComp[] parseCollection() {
        Collection<Dragon> coll = parent.getSettings().getCollection();
        if (coll == null)
            return new DragoComp[0];
        else {
            DragoComp[] dragons = new DragoComp[coll.size()];
            int counter = 0;
            for (Dragon dragon : coll) {
                dragons[counter++] = new DragoComp(dragon, parent, this);
            }
            return dragons;
        }
    }

    private Dimension getViewSize() {
        int maxX = 0;
        int minX = -500;
        int minY = 0;
        int maxY = 500;
        for (DragoComp dragon : dragons) {
            if (dragon.getTrueX() > maxX)
                maxX = dragon.getTrueX();
            if (dragon.getTrueY() > maxY)
                maxY = dragon.getTrueY();
            if (dragon.getTrueY() < minY)
                minY = dragon.getTrueY();
        }
        return new Dimension(maxX - minX + 500, maxY - minY + 500);
    }

    private void setOffsetY() {
        int maxY = 500;
        for (DragoComp dragon : dragons) {
            if (dragon.getTrueY() > maxY)
                maxY = dragon.getY();
        }
        offsetY = maxY;
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

    private void showDragons() {
        for (DragoComp dragon : dragons) {
            dragon.setLocation(dragon.getTrueX() + offsetX, offsetY - dragon.getTrueY());
            view.add(dragon);
        }
    }

    public int getOffsetX() {
        return offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }
}
