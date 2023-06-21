package GUI;

import commands.CommandType;
import dragons.Coordinates;
import dragons.Dragon;
import network.Request;
import user.User;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class DragoComp extends JLabel implements Recolorable {

    private static final URL DINO_STAY = DragoComp.class.getResource("/img/dino_stay.png");
    private static final URL DINO_LEFT = DragoComp.class.getResource("/img/dino_left.png");
    private static final URL DINO_RIGHT = DragoComp.class.getResource("/img/dino_right.png");
    private static final int DEF_HEIGHT = 22;
    private static final int DEF_WIDTH = 20;
    private static final Color BASE_COLOR = Color.black;


    private final MyFrame parent;
    private final double scale;
    private final Color color;
    private BufferedImage[] images;
    private Thread moving;
    private volatile Point trueCoords;
    private final ViewPanel view;
    private final Dragon dragon;

    public DragoComp(Dragon dragon, MyFrame parent, ViewPanel view) {
        this.parent = parent;
        this.view = view;
        this.dragon = dragon;
        moving = new Thread(this::move);
        scale = getScale(dragon.getWeight());
        Coordinates c = dragon.getCoordinates();
        trueCoords = new Point((int) c.getX(), (int) c.getY());
        setSize((int) (DEF_WIDTH * scale), (int) (DEF_HEIGHT * scale));
        User user = dragon.getCreator();
        color = getColorByString(user.getLogin());
        setImages();
        setIcon(getIcon(images[0]));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (parent.getSettings().getUser() != null &&
                        parent.getSettings().getUser().equals(dragon.getCreator())) {
                    if (!moving.isAlive()) {
                        moving = new Thread(() -> move());
                        moving.start();
                    } else {
                        updateDragon();
                    }
                }
            }
        });
    }

    public void updateDragon() {
        if (moving.isAlive()) {
            moving.interrupt();
            Coordinates newCoords = new Coordinates(trueCoords.x, trueCoords.y);
            dragon.setCoordinates(newCoords);
            int id = dragon.hashCode();
            Request update = new Request(CommandType.UPDATE, id, dragon, parent.getSettings().getUser());
            parent.getSettings().tryConnect(update);
        }
    }

    private void move() {
        boolean leftIcon = true;
        int counter = 0;
        while (!Thread.currentThread().isInterrupted()) {
            try {
                if (counter++ % 2 == 0) {
                    if (leftIcon) {
                        leftIcon = false;
                        setIcon(getIcon(images[1]));
                    } else {
                        leftIcon = true;
                        setIcon(getIcon(images[2]));
                    }
                }
                setLocation(getX() + 5, getY());
                trueCoords = new Point(getX() + getWidth() / 2 - view.getOffsetX(),
                        view.getOffsetY() - getY() - getHeight() / 2);
                view.setMaxX(trueCoords.x);
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        setIcon(getIcon(images[0]));
    }

    private void setImages() {
        images = new BufferedImage[3];
        try {
            BufferedImage image = ImageIO.read(DINO_STAY);
            images[0] = recolor(image, BASE_COLOR, color);
            image = ImageIO.read(DINO_LEFT);
            images[1] = recolor(image, BASE_COLOR, color);
            image = ImageIO.read(DINO_RIGHT);
            images[2] = recolor(image, BASE_COLOR, color);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private double getScale(long x) {
        if (x <= 0)
            return 1;
        return Math.log(x) / 3 + 2;
    }

    private Color getColorByString(String str) {
        return new Color(str.hashCode());
    }

    private Icon getIcon(BufferedImage image) {
        return new GoodIcon(image.getScaledInstance((int) (scale * DEF_WIDTH), (int) (scale * DEF_HEIGHT), Image.SCALE_SMOOTH));
    }

    public int getTrueX() {
        return trueCoords.x;
    }

    public int getTrueY() {
        return trueCoords.y;
    }

    public double getScale() {
        return scale;
    }

    public User getCreator() {
        return dragon.getCreator();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DragoComp drago) {
            return drago.dragon.hashCode() == this.dragon.hashCode();
        } else
            return false;
    }
}
