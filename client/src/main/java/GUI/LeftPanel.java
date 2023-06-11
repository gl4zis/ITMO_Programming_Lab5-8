package GUI;

import javax.swing.*;
import java.awt.*;

public class LeftPanel extends JPanel implements GoodQuality {

    private final MyFrame parent;
    private final SwitchButton[] buttons;
    private int row = 0;

    public LeftPanel(MyFrame parent) {
        this.parent = parent;
        setPreferredSize(new Dimension(160, 385));
        setLayout(new GridBagLayout());
        buttons = new SwitchButton[]{
                SwitchButton.getHomeButton(parent),
                SwitchButton.getViewButton(parent),
                SwitchButton.getTableButton(parent),
                SwitchButton.getCommandsButton(parent)
        };

        fill();
    }

    private void fill() {
        addComponent(buttons[0]);
        addSpacer(0.1);
        addComponent(buttons[1]);
        addSpacer(0.1);
        addComponent(buttons[2]);
        addSpacer(0.1);
        addComponent(buttons[3]);
        addSpacer(0.5);
        addLogo();
        addSpacer(0.2);
    }

    private void addSpacer(double weight) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = row++;
        constraints.weighty = weight;
        add(MyFrame.getSpacer(), constraints);
    }

    private void addComponent(Component c) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 0, 0, 0);
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridx = 0;
        constraints.gridy = row++;
        add(c, constraints);
    }

    private void addLogo() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = row++;
        add(LightDarkResizableIcon.getDragon(parent), constraints);
    }

    public void setButton(int index) {
        buttons[index].set();
    }

    public void resetButtons() {
        for (SwitchButton button : buttons) {
            button.reset();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setPreferredSize(new Dimension(parent.getWidth() / 5, parent.getHeight() * 6 / 7));
        double k = parent.getKf();

        Graphics2D g2D = setGoodQ(g);
        setBackground(parent.getSettings().getColors().get("mainColor"));
        Dimension panelSize = getSize();
        g2D.setColor(parent.getSettings().getColors().get("secondColor"));
        g2D.setStroke(new BasicStroke((float) k * 4));
        g2D.drawLine(panelSize.width - (int) k, 0, panelSize.width - (int) k, panelSize.height);

        //Drawing part of border
        g2D.setColor(parent.getSettings().getColors().get("borderColor"));
        g2D.setStroke(new BasicStroke((float) k * 8));
        g2D.drawLine((int) (k * 4), 0, (int) (k * 4), panelSize.height);
        g2D.drawLine(0, panelSize.height - (int) (k * 4), panelSize.width, panelSize.height - (int) (k * 4));
        g2D.drawArc((int) (k * 4), panelSize.height - (int) (k * 34), (int) (k * 30), (int) (k * 30), 180, 90);
    }
}
