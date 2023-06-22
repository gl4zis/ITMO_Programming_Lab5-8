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
        int baseRGB = from.getRGB() << 8;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int color = transpImg.getRGB(i, j);
                if (color >> 24 != 0 && color << 8 == baseRGB) {
                    int newRGB = preferredRGB + (color >> 24 << 24);
                    newImage.setRGB(i, j, newRGB);
                } else if (color >> 24 == 0)
                    newImage.setRGB(i, j, 0);
                else
                    newImage.setRGB(i, j, color);
            }
        }
        return newImage;
    }
}
