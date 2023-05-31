package GUI;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class GoodIcon implements Icon {
    private final Image image;

    public GoodIcon(Image image) {
        this.image = image;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2d = (Graphics2D) g;

        Map<RenderingHints.Key, Object> map = new HashMap<>();
        map.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        map.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        map.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        RenderingHints renderHints = new RenderingHints(map);
        g2d.setRenderingHints(renderHints);

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
