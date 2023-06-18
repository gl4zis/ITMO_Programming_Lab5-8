package GUI;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

public class MyScrollBarUI extends BasicScrollBarUI implements GoodQuality {

    private final MyFrame parent;

    public MyScrollBarUI(MyFrame parent) {
        this.parent = parent;
    }

    protected JButton createZeroButton() {
        JButton button = new JButton();
        Dimension zeroDim = new Dimension(0, 0);
        button.setPreferredSize(zeroDim);
        button.setMinimumSize(zeroDim);
        button.setMaximumSize(zeroDim);
        return button;
    }

    @Override
    protected JButton createIncreaseButton(int i) {
        return createZeroButton();
    }

    @Override
    protected JButton createDecreaseButton(int i) {
        return createZeroButton();
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        Graphics2D g2d = setGoodQ(g);
        g2d.setColor(parent.getSettings().getColors().get("mainColor"));
        g2d.fillRect(getTrackBounds().x - 1, getTrackBounds().y, getTrackBounds().width + 1, getTrackBounds().height);
        super.paint(g, c);
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        if (thumbBounds.isEmpty() || !scrollbar.isEnabled()) {
            return;
        }

        Graphics2D g2d = setGoodQ(g);
        g2d.setColor(parent.getSettings().getColors().get("secondColor"));
        g2d.fillRoundRect(thumbBounds.x + thumbBounds.width / 4, thumbBounds.y, thumbBounds.width / 2, thumbBounds.height,
                thumbBounds.width / 2, thumbBounds.width / 2);
    }

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        Graphics2D g2d = setGoodQ(g);
        g2d.setColor(parent.getSettings().getColors().get("borderColor"));
        g2d.fillRect(trackBounds.x + trackBounds.width / 4, trackBounds.y, trackBounds.width / 2, trackBounds.height);
    }
}
