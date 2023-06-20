package GUI;

import java.awt.*;
import java.awt.image.BufferedImage;

public interface Recolorable {
    default BufferedImage recolor(BufferedImage image, Color from, Color to) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage transpImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = transpImg.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();

        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        int preferredRGB = to.getRGB();
        int baseRGB = from.getRGB();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int color = transpImg.getRGB(i, j);
                if (color == baseRGB) {
                    newImage.setRGB(i, j, preferredRGB);
                } else
                    newImage.setRGB(i, j, 0);
            }
        }
        return newImage;
    }
}
