package GUI;

import commands.CommandType;
import dragons.Coordinates;
import dragons.Dragon;
import network.Request;
import user.User;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.TableHeaderUI;
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
    private static final URL FIRE1 = DragoComp.class.getResource("/img/fire1.png");
    private static final URL FIRE2 = DragoComp.class.getResource("/img/fire2.png");
    private static final URL FIRE3 = DragoComp.class.getResource("/img/fire3.png");
    private static final int DEF_HEIGHT = 22;
    private static final int DEF_WIDTH = 20;
    private static final int ANIM_WIDTH = 60;
    private static final Color BASE_COLOR = Color.black;


    private final MyFrame parent;
    private final double scale;
    private final Color color;
    private final ViewPanel view;
    private final Dragon dragon;
    private BufferedImage[] images;
    private BufferedImage[] animation;
    private Thread moving;
    private volatile Point trueCoords;

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
        setToolTipText("<html>" + dragon.toString().replaceAll("\n", "<br>") + "</html>");
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (parent.getSettings().getUser() != null &&
                        parent.getSettings().getUser().equals(dragon.getCreator())) {
                    if (moving.isAlive())
                        new Thread(() -> updateDragon()).start();
                    else {
                        moving = new Thread(() -> move());
                        moving.start();
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
            setToolTipText("<html>" + dragon.toString().replaceAll("\n", "<br>") + "</html>");
            Request update = new Request(CommandType.UPDATE, id, dragon, parent.getSettings().getUser());
            do {
                parent.getSettings().tryConnect(update);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            } while (!parent.getSettings().isConnected());
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
        animation = new BufferedImage[3];
        try {
            images[0] = recolor(ImageIO.read(DINO_STAY), BASE_COLOR, color);
            images[1] = recolor(ImageIO.read(DINO_LEFT), BASE_COLOR, color);
            images[2] = recolor(ImageIO.read(DINO_RIGHT), BASE_COLOR, color);
            animation[0] = recolor(ImageIO.read(FIRE1), BASE_COLOR, color);
            animation[1] = recolor(ImageIO.read(FIRE2), BASE_COLOR, color);
            animation[2] = recolor(ImageIO.read(FIRE3), BASE_COLOR, color);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startAnimation() {
        setSize((int) (ANIM_WIDTH * scale), (int) (DEF_HEIGHT * scale));
        setIcon(getAnim(animation[0]));
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        for (int i = 0; i < 10; i++) {
            setIcon(getAnim(animation[1]));
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            setIcon(getAnim(animation[2]));
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        setIcon(getAnim(animation[0]));
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        setSize((int) (DEF_WIDTH * scale), (int) (DEF_HEIGHT * scale));
        setIcon(getIcon(images[0]));
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

    private Icon getAnim(BufferedImage image) {
        return new GoodIcon(image.getScaledInstance((int) (scale * ANIM_WIDTH), (int) (scale * DEF_HEIGHT), Image.SCALE_SMOOTH));
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

    @Override
    public JToolTip createToolTip() {
        JToolTip tooltip = super.createToolTip();
        tooltip.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        tooltip.setBackground(Color.white);
        return tooltip;
    }
}
