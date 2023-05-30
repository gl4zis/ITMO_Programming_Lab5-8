package GUI;

import javax.swing.*;
import java.awt.*;

public class Header extends JPanel {

    private final MyFrame parent;
    private final ResizableIcon connIcon;
    private final ResizableIcon themeButton;
    private final ResizableIcon userButton;
    private final ResizableIcon helpButton;
    private final LangBox lang;

    public Header(MyFrame parent) {
        this.parent = parent;
        setPreferredSize(new Dimension(800, 65));
        setLayout(new GridBagLayout());

        connIcon = ResizableIcon.getConnButton(parent);
        themeButton = LightDarkResizableIcon.getThemeButton(parent);
        userButton = LightDarkResizableIcon.getUserButton(parent);
        helpButton = LightDarkResizableIcon.getHelpButton(parent);
        lang = LangBox.getShortBox(parent);

        fill();
    }

    private void fill() {
        addSpacer(0.05);
        addComponent(connIcon);
        addSpacer(0.2);
        addComponent(themeButton);
        addSpacer(0.4);
        addITMOLabel();
        addSpacer(0.4);
        addComponent(userButton);
        addSpacer(0.05);
        addComponent(helpButton);
        addSpacer(0.05);
        addComponent(lang);
        addSpacer(0.05);
    }

    private void addSpacer(double weight) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = weight;
        add(MyFrame.getSpacer(), constraints);
    }

    private void addComponent(Component c) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(2, 0, 0, 0);
        add(c, constraints);
    }

    private void addITMOLabel() {
        add(LightDarkResizableIcon.getITMO(parent));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setPreferredSize(new Dimension(parent.getWidth(), parent.getHeight() / 7));
        double k = parent.getKf();

        Graphics2D g2D = (Graphics2D) g;
        setBackground(parent.getSettings().getColors().get("mainColor"));
        Dimension panelSize = getSize();
        g2D.setColor(parent.getSettings().getColors().get("secondColor"));
        g2D.setStroke(new BasicStroke((int) (k * 4)));
        g2D.drawLine(0, panelSize.height - 2, panelSize.width, panelSize.height - 2);

        //Drawing part of border
        g2D.setColor(parent.getSettings().getColors().get("borderColor"));
        g2D.setStroke(new BasicStroke((int) (k * 8)));
        g2D.drawLine(0, (int) (k * 4), panelSize.width, (int) (k * 4));
        g2D.drawLine((int) (k * 4), 0, (int) (k * 4), panelSize.height);
        g2D.drawLine(panelSize.width - (int) (k * 4), 0, panelSize.width - (int) (k * 4), panelSize.height);
        g2D.drawArc((int) (k * 4), (int) (k * 4), (int) (k * 30), (int) (k * 30), 90, 90);
        g2D.drawArc(panelSize.width - (int) (k * 34), (int) (k * 4), (int) (k * 30), (int) (k * 30), 0, 90);
    }
}
