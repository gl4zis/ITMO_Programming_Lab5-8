package GUI;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class LightDarkResizableIcon extends ResizableIcon {
    protected LightDarkResizableIcon(MyFrame parent, URL image1, URL image2, String text) {
        super(parent, image1, image2, text);
    }

    protected LightDarkResizableIcon(MyFrame parent, URL image1, URL image2) {
        super(parent, image1, image2);
    }

    public static ResizableIcon getEyeButton(MyFrame parent) {
        return new LightDarkResizableIcon(parent, LIGHT_CLOSE_EYE, DARK_CLOSE_EYE) {
            private BufferedImage image3;
            private BufferedImage image4;

            {
                try {
                    image3 = ImageIO.read(LIGHT_OPEN_EYE);
                    image4 = ImageIO.read(DARK_OPEN_EYE);
                } catch (IOException ignored) {
                }
            }

            private void swapImages() {
                BufferedImage buffer = image1;
                image1 = image3;
                image3 = buffer;
                buffer = image2;
                image2 = image4;
                image4 = buffer;
            }

            @Override
            protected void click() {
                Container field = getParent();
                if (field instanceof CustomTextField) {
                    ((CustomTextField) field).swapHideMode();
                }
                swapImages();
            }
        };
    }

    public static ResizableIcon getThemeButton(MyFrame parent) {
        return new LightDarkResizableIcon(parent, LIGHT_THEME, DARK_THEME) {
            @Override
            protected void click() {
                parent.getSettings().changeTheme();
                parent.repaint();
            }
        };
    }

    public static ResizableIcon getBigThemeButton(MyFrame parent) {
        return new LightDarkResizableIcon(parent, LIGHT_THEME, DARK_THEME, "home.darkTheme") {
            {
                scale = 1.2;
            }

            @Override
            protected void click() {
                parent.getSettings().changeTheme();
                parent.repaint();
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setFont(new Font("Arial", Font.BOLD, (int) (fontSize * 1.5)));
                if (parent.getSettings().isDark())
                    setText(parent.getSettings().getLocale().getResource("home.lightTheme"));
                else
                    setText(parent.getSettings().getLocale().getResource("home.darkTheme"));
            }
        };
    }

    public static ResizableIcon getUserButton(MyFrame parent) {
        return new LightDarkResizableIcon(parent, LIGHT_USER, DARK_USER, "") {
            @Override
            protected void click() {
                if (parent.getSettings().getUser() != null)
                    parent.setStatus(PageStatus.HOME);
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setFont(new Font("Arial", Font.BOLD, fontSize));
                if (parent.getSettings().getUser() == null)
                    setText("");
                else
                    setText(parent.getSettings().getUser().getLogin());
            }
        };
    }

    public static ResizableIcon getBigUserButton(MyFrame parent) {
        return new LightDarkResizableIcon(parent, LIGHT_USER, DARK_USER, "") {
            {
                scale = 2;
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setFont(new Font("Arial", Font.BOLD, (int) (fontSize * 1.5)));
                if (parent.getSettings().getUser() == null)
                    setText("");
                else
                    setText(parent.getSettings().getUser().getLogin());
            }
        };
    }

    public static ResizableIcon getHelpButton(MyFrame parent) {
        return new LightDarkResizableIcon(parent, LIGHT_HELP, DARK_HELP);
    }

    public static ResizableIcon getITMO(MyFrame parent) {
        return new LightDarkResizableIcon(parent, LIGHT_ITMO, DARK_ITMO) {
            {
                scale = 1;
            }
        };
    }

    public static ResizableIcon getDragon(MyFrame parent) {
        return new LightDarkResizableIcon(parent, LIGHT_DRAGON, DARK_DRAGON);
    }

    @Override
    protected BufferedImage chooseImg() {
        if (parent.getSettings().isDark())
            return image2;
        else return image1;
    }
}
