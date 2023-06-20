package GUI;

import dragons.Coordinates;
import dragons.Dragon;
import user.User;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class DinoComp extends JLabel {

    private static final URL DINO_STAY = DinoComp.class.getResource("/img/dino_stay.png");
    private static final URL DINO_LEFT = DinoComp.class.getResource("/img/dino_left.png");
    private static final URL DINO_RIGHT = DinoComp.class.getResource("/img/dino_right.png");
    private static final int DEF_HEIGHT = 22;
    private static final int DEF_WIDTH = 20;
    private static final Color BASE_COLOR = Color.black;


    private final MyFrame parent;
    private double scale;
    private final Color color;
    private BufferedImage[] images;
    private boolean isMoving;
    private Thread moving;
    private double oldKf;
    private int iconInd = 0;

    public DinoComp(Dragon dragon, MyFrame parent) {
        this.parent = parent;
        oldKf = parent.getKf();
        scale = getScale(dragon.getWeight()) * parent.getKf();
        Coordinates c = dragon.getCoordinates();
        setBounds((int) c.getX(), (int) c.getY(), (int) (DEF_WIDTH * scale), (int) (DEF_HEIGHT * scale));
        User user = dragon.getCreator();
        color = getColorByString(user.getLogin());
        setImages();
        setIcon(getIcon(images[0]));
        isMoving = false;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!isMoving) {
                    isMoving = true;
                    moving = new Thread(() -> move());
                    moving.start();
                } else {
                    isMoving = false;
                    moving.interrupt();
                }
            }
        });
    }

    private void move() {
        boolean leftIcon = true;
        int counter = 0;
        while (!Thread.currentThread().isInterrupted()) {
            if (leftIcon) {
                iconInd = 1;
                if (counter % 10 == 0)
                    leftIcon = false;
            } else {
                iconInd = 2;
                if (counter % 10 == 0)
                    leftIcon = true;
            }
            setIcon(getIcon(images[iconInd]));
            setLocation(getX() + 1, getY());
            counter++;
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            iconInd = 0;
            setIcon(getIcon(images[0]));
        }
    }

    private void setImages() {
        images = new BufferedImage[3];
        try {
            BufferedImage image = ImageIO.read(DINO_STAY);
            images[0] = recolor(image);
            image = ImageIO.read(DINO_LEFT);
            images[1] = recolor(image);
            image = ImageIO.read(DINO_RIGHT);
            images[2] = recolor(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private BufferedImage recolor(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage transpImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = transpImg.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();

        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        int preferredRGB = color.getRGB();
        int baseRGB = BASE_COLOR.getRGB();
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

    private double getScale(long x) {
        if (x <= 0)
            return 1;
        return Math.log(x) / 3 + 1;
    }

    private Color getColorByString(String str) {
        return new Color(str.hashCode());
    }

    private Icon getIcon(BufferedImage image) {
        return new GoodIcon(image.getScaledInstance((int) (scale * DEF_WIDTH), (int) (scale * DEF_HEIGHT), Image.SCALE_SMOOTH));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (oldKf != parent.getKf()) {
            scale = scale / oldKf * parent.getKf();
            oldKf = parent.getKf();
            setIcon(getIcon(images[iconInd]));
        }
    }
}
