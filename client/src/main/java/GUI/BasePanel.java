package GUI;

import javax.swing.*;
import java.awt.*;

public abstract class BasePanel extends JPanel implements GoodQuality {

    protected final MyFrame parent;

    public BasePanel(MyFrame parent) {
        this.parent = parent;
        setLayout(new GridBagLayout());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(parent.getSettings().getColors().get("mainColor"));
        Dimension panelSize = getSize();
        double k = parent.getKf();

        Graphics2D g2D = setGoodQ(g);

        //Drawing part of border
        g2D.setStroke(new BasicStroke((float) k * 8));
        g2D.setColor(parent.getSettings().getColors().get("borderColor"));
        g2D.drawLine(0, panelSize.height - (int) (k * 4), panelSize.width, panelSize.height - (int) (k * 4));
        g2D.drawLine(panelSize.width - (int) (k * 4), panelSize.height, panelSize.width - (int) (k * 4), 0);
        g2D.drawArc(panelSize.width - (int) (k * 34), panelSize.height - (int) (k * 34), (int) (k * 30), (int) (k * 30), 270, 90);
    }
}
