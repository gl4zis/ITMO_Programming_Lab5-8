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
    private double scale;
    private final Color color;
    private BufferedImage[] images;
    private boolean isMoving;
    private Thread moving;
    private double oldKf;
    private int iconInd = 0;
    private Point trueCoords;
    private final ViewPanel view;
    private final Dragon dragon;

    public DragoComp(Dragon dragon, MyFrame parent, ViewPanel view) {
        this.parent = parent;
        this.view = view;
        this.dragon = dragon;
        oldKf = parent.getKf();
        scale = getScale(dragon.getWeight()) * parent.getKf();
        Coordinates c = dragon.getCoordinates();
        trueCoords = new Point((int) c.getX(), (int) c.getY());
        setSize((int) (DEF_WIDTH * scale), (int) (DEF_HEIGHT * scale));
        User user = dragon.getCreator();
        color = getColorByString(user.getLogin());
        setImages();
        setIcon(getIcon(images[0]));
        isMoving = false;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (parent.getSettings().getUser() != null &&
                        parent.getSettings().getUser().equals(dragon.getCreator())) {
                    if (!isMoving) {
                        isMoving = true;
                        moving = new Thread(() -> move());
                        moving.start();
                    } else {
                        isMoving = false;
                        updateDragon();
                    }
                }
            }
        });
    }

    public void updateDragon() {
        moving.interrupt();
        Coordinates newCoords = new Coordinates(trueCoords.x, trueCoords.y);
        dragon.setCoordinates(newCoords);
        int id = dragon.hashCode();
        Request update = new Request(CommandType.UPDATE, id, dragon, parent.getSettings().getUser());
        parent.getSettings().tryConnect(update);
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
            trueCoords = new Point(getX() + getWidth() / 2 - view.getOffsetX(),
                    view.getOffsetY() - getY() - getHeight() / 2);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            iconInd = 0;
            setIcon(getIcon(images[iconInd]));
        }
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

    public boolean isMoving() {
        return isMoving;
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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DragoComp drago) {
            return drago.dragon.hashCode() == this.dragon.hashCode();
        } else
            return false;
    }
}
