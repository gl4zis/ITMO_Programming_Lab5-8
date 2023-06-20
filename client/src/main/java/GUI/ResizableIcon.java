package GUI;

import settings.MyLocale;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public abstract class ResizableIcon extends JLabel implements Recolorable {
    protected static final URL WELCOME;
    protected static final URL LIGHT_DRAGON;
    protected static final URL LIGHT_ITMO;
    protected static final URL CONNECTION;
    protected static final URL NO_CONNECTION;
    protected static final URL LIGHT_HELP;
    protected static final URL LIGHT_THEME;
    protected static final URL DARK_THEME;
    protected static final URL LIGHT_USER;
    protected static final URL LIGHT_HOME;
    protected static final URL LIGHT_VIEW;
    protected static final URL LIGHT_TABLE;
    protected static final URL LIGHT_COMMANDS;
    protected static final URL LIGHT_CLOSE_EYE;
    protected static final URL LIGHT_OPEN_EYE;

    static {
        WELCOME = Objects.requireNonNull(ResizableIcon.class.getResource("/img/welcome.png"));
        LIGHT_DRAGON = Objects.requireNonNull(ResizableIcon.class.getResource("/img/dragon.png"));
        LIGHT_ITMO = Objects.requireNonNull(ResizableIcon.class.getResource("/img/itmo.png"));
        CONNECTION = Objects.requireNonNull(ResizableIcon.class.getResource("/img/connection.png"));
        NO_CONNECTION = Objects.requireNonNull(ResizableIcon.class.getResource("/img/no_connection.png"));
        LIGHT_HELP = Objects.requireNonNull(ResizableIcon.class.getResource("/img/help.png"));
        LIGHT_THEME = Objects.requireNonNull(ResizableIcon.class.getResource("/img/light_theme.png"));
        DARK_THEME = Objects.requireNonNull(ResizableIcon.class.getResource("/img/dark_theme.png"));
        LIGHT_USER = Objects.requireNonNull(ResizableIcon.class.getResource("/img/user.png"));
        LIGHT_HOME = Objects.requireNonNull(ResizableIcon.class.getResource("/img/home.png"));
        LIGHT_VIEW = Objects.requireNonNull(ResizableIcon.class.getResource("/img/view.png"));
        LIGHT_TABLE = Objects.requireNonNull(ResizableIcon.class.getResource("/img/table.png"));
        LIGHT_COMMANDS = Objects.requireNonNull(ResizableIcon.class.getResource("/img/commands.png"));
        LIGHT_CLOSE_EYE = Objects.requireNonNull(ResizableIcon.class.getResource("/img/close_eye.png"));
        LIGHT_OPEN_EYE = Objects.requireNonNull(ResizableIcon.class.getResource("/img/open_eye.png"));
    }

    protected String text;
    protected BufferedImage mainImg;
    protected BufferedImage[] images;
    protected MyFrame parent;
    protected int fontSize;
    protected int defaultFontSize = 14;
    protected double scale = 0.2;
    private double oldKf;
    private MyLocale locale;

    protected ResizableIcon(MyFrame parent, String text, URL... images) {
        this.parent = parent;
        this.text = text;
        locale = parent.getSettings().getLocale();
        if (text != null)
            setText(parent.getSettings().getLocale().getResource(text));
        fontSize = defaultFontSize;
        this.images = new BufferedImage[images.length];
        try {
            this.mainImg = ImageIO.read(images[0]);
            setIcon(new GoodIcon(mainImg.getScaledInstance((int) (mainImg.getWidth() * scale),
                    (int) (mainImg.getHeight() * scale), Image.SCALE_DEFAULT)));
            for (int i = 0; i < images.length; i++) {
                this.images[i] = ImageIO.read(images[i]);
            }
        } catch (IOException | IllegalArgumentException ignored) {
        }
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                click();
            }
        });
    }

    protected ResizableIcon(MyFrame parent, URL... images) {
        this(parent, null, images);
    }

    public static ResizableIcon getThemeButton(MyFrame parent) {
        return new ResizableIcon(parent, LIGHT_THEME, DARK_THEME) {
            @Override
            protected void click() {
                parent.getSettings().changeTheme();
                parent.repaint();
            }

            @Override
            protected void chooseImg() {
                if (parent.getSettings().isDark())
                    mainImg = images[1];
                else
                    mainImg = images[0];
            }
        };
    }

    public static ResizableIcon getBigThemeButton(MyFrame parent) {
        return new ResizableIcon(parent, LIGHT_THEME, DARK_THEME) {
            private final String darkText = "home.darkTheme";
            private final String lightText = "home.lightTheme";

            {
                scale = 0.3;
                defaultFontSize = 20;
                if (!parent.getSettings().isDark()) {
                    text = darkText;
                    setText(parent.getSettings().getLocale().getResource(darkText));
                } else {
                    text = lightText;
                    setText(parent.getSettings().getLocale().getResource(lightText));
                }
            }

            @Override
            protected void click() {
                parent.getSettings().changeTheme();
                if (text.equals(darkText)) {
                    text = lightText;
                    setText(parent.getSettings().getLocale().getResource(lightText));
                } else {
                    text = darkText;
                    setText(parent.getSettings().getLocale().getResource(darkText));
                }
                parent.repaint();
            }

            @Override
            protected void chooseImg() {
                if (parent.getSettings().isDark())
                    mainImg = images[1];
                else
                    mainImg = images[0];
            }
        };
    }

    public static ResizableIcon getWelcome(MyFrame parent) {
        return new ResizableIcon(parent, WELCOME) {
            {
                scale = 0.4;
            }
        };
    }

    public static ResizableIcon getConnButton(MyFrame parent) {
        return new ResizableIcon(parent, CONNECTION, NO_CONNECTION) {
            @Override
            protected void chooseImg() {
                if (parent.getSettings().isConnected())
                    mainImg = images[0];
                else
                    mainImg = images[1];
            }
        };
    }

    protected void click() {
    }

    protected void chooseImg() {
    }

    private void update() {
        Image oldImg = mainImg;
        chooseImg();
        if (oldImg != mainImg || oldKf != parent.getKf()) {
            oldKf = parent.getKf();
            fontSize = (int) (defaultFontSize * oldKf);
            setFont(getFont().deriveFont((float) fontSize));
            int width = (int) (mainImg.getWidth() * oldKf * scale);
            int height = (int) (mainImg.getHeight() * oldKf * scale);
            Icon icon = new GoodIcon(mainImg.getScaledInstance(width, height, Image.SCALE_DEFAULT));
            setIcon(icon);
        }
        if (text != null && !locale.equals(parent.getSettings().getLocale())) {
            locale = parent.getSettings().getLocale();
            setText(parent.getSettings().getLocale().getResource(text));
        }
        Color newForeground;
        if (this instanceof SwitchButton sw && sw.setted) {
            newForeground = parent.getSettings().getColors().get("secondColor");
        } else newForeground = parent.getSettings().getColors().get("fontColor");
        if (!getForeground().equals(newForeground))
            setForeground(newForeground);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        update();
    }
}
