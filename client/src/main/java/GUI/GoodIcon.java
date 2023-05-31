package GUI;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class GoodIcon implements Icon, GoodQuality {
    private final Image image;

    public GoodIcon(Image image) {
        this.image = image;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2d = setGoodQ(g);

        g2d.drawImage(image, x, y, null);
    }

    @Override
    public int getIconWidth() {
        return image.getWidth(null);
    }

    @Override
    public int getIconHeight() {
        return image.getHeight(null);
    }
}
