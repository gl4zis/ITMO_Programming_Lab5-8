package GUI;

import dragons.Dragon;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class ViewPanel extends BasePanel {
    private final JPanel view;
    private final JScrollPane pane;
    private ArrayList<DragoComp> dragons;
    private final int offsetX = 500;
    private int offsetY;
    private int counterKostil = 0;
    private final Thread refreshing;
    private final CustomButton goToStart;

    public ViewPanel(MyFrame parent) {
        super(parent);
        parent.getSettings().loadCollection();
        dragons = parseCollection();
        goToStart = new CustomButton(parent, CustomButton.Size.TINY, "view.start", true) {
            @Override
            protected void click() {
                pane.getVerticalScrollBar().setValue(offsetY - pane.getHeight() / 2);
                pane.getHorizontalScrollBar().setValue(offsetX - pane.getWidth() / 2);
            }
        };
        view = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2D = setGoodQ(g);
                setPreferredSize(getViewSize());

                if (counterKostil < 2) {
                    pane.getVerticalScrollBar().setValue(offsetY - pane.getHeight() / 2);
                    pane.getHorizontalScrollBar().setValue(offsetX - pane.getWidth() / 2);
                    counterKostil++;
                }

                goToStart.setSize(goToStart.getPreferredSize());
                goToStart.setLocation(pane.getWidth() - (int) (goToStart.getWidth() * 1.2) +
                                pane.getHorizontalScrollBar().getValue(),
                        pane.getHeight() - goToStart.getHeight() * 2 + pane.getVerticalScrollBar().getValue());

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
        view.add(goToStart);
        showDragons();
        refreshing = new Thread(this::refresh);
        refreshing.start();
    }

    private void refresh() {
        while (!Thread.currentThread().isInterrupted()) {
            parent.getSettings().loadCollection();
            ArrayList<DragoComp> newList = parseCollection();
            showDragons();
            dragons = newList;
            view.repaint();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }


    public void updateAllDragons() {
        for (DragoComp dragon : dragons) {
            if (dragon.isMoving())
                dragon.updateDragon();
        }
        refreshing.interrupt();
    }

    private ArrayList<DragoComp> parseCollection() {
        Collection<Dragon> coll = parent.getSettings().getCollection();
        ArrayList<DragoComp> dragons = new ArrayList<>();
        for (Dragon dragon : coll) {
            dragons.add(new DragoComp(dragon, parent, this));
        }
        return dragons;
    }

    private Dimension getViewSize() {
        int maxX = 0;
        int minX = -500;
        int minY = 0;
        int maxY = 300;
        for (DragoComp dragon : dragons) {
            if (dragon.getTrueX() > maxX)
                maxX = dragon.getTrueX();
            if (dragon.getTrueY() > maxY)
                maxY = dragon.getTrueY();
            if (dragon.getTrueY() < minY)
                minY = dragon.getTrueY();
        }
        return new Dimension(maxX - minX + 500, maxY - minY + 300);
    }

    private void setOffsetY() {
        int maxY = 300;
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
        for (Component comp : view.getComponents()) {
            if (comp instanceof DragoComp dragon) {
                if (!dragons.contains(dragon))
                    view.remove(dragon);
            }
        }

        for (DragoComp dragon : dragons) {
            if (!Arrays.asList(view.getComponents()).contains(dragon)) {
                dragon.setLocation(dragon.getTrueX() + offsetX - dragon.getWidth() / 2,
                        offsetY - dragon.getTrueY() - dragon.getHeight() / 2);
                view.add(dragon);
            }
        }
    }

    public int getOffsetX() {
        return offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }
}
