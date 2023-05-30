package GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public abstract class ResizableIcon extends JLabel {
    protected static final URL WELCOME;
    protected static final URL LIGHT_DRAGON;
    protected static final URL DARK_DRAGON;
    protected static final URL LIGHT_ITMO;
    protected static final URL DARK_ITMO;
    protected static final URL CONNECTION;
    protected static final URL NO_CONNECTION;
    protected static final URL LIGHT_HELP;
    protected static final URL DARK_HELP;
    protected static final URL LIGHT_THEME;
    protected static final URL DARK_THEME;
    protected static final URL LIGHT_USER;
    protected static final URL DARK_USER;
    protected static final URL LIGHT_HOME;
    protected static final URL DARK_HOME;
    protected static final URL LIGHT_VIEW;
    protected static final URL DARK_VIEW;
    protected static final URL LIGHT_TABLE;
    protected static final URL DARK_TABLE;
    protected static final URL LIGHT_COMMANDS;
    protected static final URL DARK_COMMANDS;
    protected static final URL LIGHT_CLOSE_EYE;
    protected static final URL DARK_CLOSE_EYE;
    protected static final URL LIGHT_OPEN_EYE;
    protected static final URL DARK_OPEN_EYE;

    static {
        WELCOME = Objects.requireNonNull(ResizableIcon.class.getResource("/img/welcome.png"));
        LIGHT_DRAGON = Objects.requireNonNull(ResizableIcon.class.getResource("/img/light/dragon.png"));
        DARK_DRAGON = Objects.requireNonNull(ResizableIcon.class.getResource("/img/dark/dragon.png"));
        LIGHT_ITMO = Objects.requireNonNull(ResizableIcon.class.getResource("/img/light/itmo.png"));
        DARK_ITMO = Objects.requireNonNull(ResizableIcon.class.getResource("/img/dark/itmo.png"));
        CONNECTION = Objects.requireNonNull(ResizableIcon.class.getResource("/img/connection.png"));
        NO_CONNECTION = Objects.requireNonNull(ResizableIcon.class.getResource("/img/no_connection.png"));
        LIGHT_HELP = Objects.requireNonNull(ResizableIcon.class.getResource("/img/light/help.png"));
        DARK_HELP = Objects.requireNonNull(ResizableIcon.class.getResource("/img/dark/help.png"));
        LIGHT_THEME = Objects.requireNonNull(ResizableIcon.class.getResource("/img/light/theme.png"));
        DARK_THEME = Objects.requireNonNull(ResizableIcon.class.getResource("/img/dark/theme.png"));
        LIGHT_USER = Objects.requireNonNull(ResizableIcon.class.getResource("/img/light/user.png"));
        DARK_USER = Objects.requireNonNull(ResizableIcon.class.getResource("/img/dark/user.png"));
        LIGHT_HOME = Objects.requireNonNull(ResizableIcon.class.getResource("/img/light/home.png"));
        DARK_HOME = Objects.requireNonNull(ResizableIcon.class.getResource("/img/dark/home.png"));
        LIGHT_VIEW = Objects.requireNonNull(ResizableIcon.class.getResource("/img/light/view.png"));
        DARK_VIEW = Objects.requireNonNull(ResizableIcon.class.getResource("/img/dark/view.png"));
        LIGHT_TABLE = Objects.requireNonNull(ResizableIcon.class.getResource("/img/light/table.png"));
        DARK_TABLE = Objects.requireNonNull(ResizableIcon.class.getResource("/img/dark/table.png"));
        LIGHT_COMMANDS = Objects.requireNonNull(ResizableIcon.class.getResource("/img/light/commands.png"));
        DARK_COMMANDS = Objects.requireNonNull(ResizableIcon.class.getResource("/img/dark/commands.png"));
        LIGHT_CLOSE_EYE = Objects.requireNonNull(ResizableIcon.class.getResource("/img/light/close_eye.png"));
        DARK_CLOSE_EYE = Objects.requireNonNull(ResizableIcon.class.getResource("/img/dark/close_eye.png"));
        LIGHT_OPEN_EYE = Objects.requireNonNull(ResizableIcon.class.getResource("/img/light/open_eye.png"));
        DARK_OPEN_EYE = Objects.requireNonNull(ResizableIcon.class.getResource("/img/dark/open_eye.png"));
    }

    protected final String text;
    protected BufferedImage image1;
    protected BufferedImage image2;
    protected MyFrame parent;
    protected int fontSize;
    protected double scale = 0.8;

    protected ResizableIcon(MyFrame parent, URL image1, URL image2, String text) {
        this.parent = parent;
        this.text = text;
        if (text != null)
            setText(parent.getSettings().getLocale().getResource(text));
        fontSize = 14;
        try {
            this.image1 = ImageIO.read(image1);
            setIcon(new ImageIcon(this.image1.getScaledInstance((int) (this.image1.getWidth() * scale),
                    (int) (this.image1.getHeight() * scale), Image.SCALE_DEFAULT)));
            this.image2 = ImageIO.read(image2);
        } catch (IOException | IllegalArgumentException ignored) {
        }
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                click();
            }
        });
    }

    protected ResizableIcon(MyFrame parent, URL image1, URL image2) {
        this(parent, image1, image2, null);
    }

    protected ResizableIcon(MyFrame parent, URL image) {
        this(parent, image, null);
    }

    public static ResizableIcon getWelcome(MyFrame parent) {
        return new ResizableIcon(parent, WELCOME) {
            {
                scale = 0.7;
            }
        };
    }

    public static ResizableIcon getConnButton(MyFrame parent) {
        return new ResizableIcon(parent, CONNECTION, NO_CONNECTION) {
            private boolean isConnected = true;

            @Override
            protected void click() {
                isConnected = !isConnected;
            }

            @Override
            protected BufferedImage chooseImg() {
                if (isConnected)
                    return image1;
                else
                    return image2;
            }
        };
    }

    protected void click() {
    }

    protected BufferedImage chooseImg() {
        return image1;
    }

    private void setImage(BufferedImage mainImg) {
        double k = parent.getKf();
        Dimension imgSize = new Dimension(mainImg.getWidth(), mainImg.getHeight());
        fontSize = (int) (14 * k);
        int width = (int) (imgSize.width * k * scale);
        int height = (int) (imgSize.height * k * scale);
        Image icon = mainImg.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        setIcon(new ImageIcon(icon));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setImage(chooseImg());
        setForeground(parent.getSettings().getColors().get("fontColor"));
        if (text != null)
            setText(parent.getSettings().getLocale().getResource(text));
    }
}
