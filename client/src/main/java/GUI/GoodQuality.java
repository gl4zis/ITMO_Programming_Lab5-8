package GUI;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public interface GoodQuality {

    default Graphics2D setGoodQ(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        Map<RenderingHints.Key, Object> map = new HashMap<>();
        map.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        map.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        map.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        RenderingHints renderHints = new RenderingHints(map);
        g2d.setRenderingHints(renderHints);
        return g2d;
    }
}
