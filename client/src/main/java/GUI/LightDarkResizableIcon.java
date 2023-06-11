package GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class LightDarkResizableIcon extends ResizableIcon {
    protected LightDarkResizableIcon(MyFrame parent, String text, URL... images) {
        super(parent, text, images);
    }

    protected LightDarkResizableIcon(MyFrame parent, URL... images) {
        super(parent, images);
    }

    public static ResizableIcon getEyeButton(MyFrame parent) {
        return new LightDarkResizableIcon(parent, LIGHT_CLOSE_EYE, DARK_CLOSE_EYE, LIGHT_OPEN_EYE, DARK_OPEN_EYE) {
            private boolean isOpen = false;

            @Override
            public void chooseImg() {
                if (parent.getSettings().isDark()) {
                    if (isOpen)
                        mainImg = images[3];
                    else
                        mainImg = images[1];
                } else {
                    if (isOpen)
                        mainImg = images[2];
                    else
                        mainImg = images[0];
                }
            }

            @Override
            protected void click() {
                Container field = getParent();
                if (field instanceof CustomTextField ctf) {
                    ctf.swapHideMode();
                }
                isOpen = !isOpen;
                repaint();
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
        return new LightDarkResizableIcon(parent, "home.darkTheme", LIGHT_THEME, DARK_THEME) {
            {
                scale = 0.3;
                defaultFontSize = 20;
            }

            @Override
            protected void click() {
                parent.getSettings().changeTheme();
                parent.repaint();
            }
        };
    }

    public static ResizableIcon getUserButton(MyFrame parent) {
        return new LightDarkResizableIcon(parent, "", LIGHT_USER, DARK_USER) {
            @Override
            protected void click() {
                if (parent.getSettings().getUser() != null)
                    parent.setStatus(PageStatus.HOME);
            }

            private void checkUsr() {
                if (parent.getSettings().getUser() == null && !getText().isEmpty()) {
                    setText("");
                } else if (getText().isEmpty() && parent.getSettings().getUser() != null)
                    setText(parent.getSettings().getUser().getLogin());
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                checkUsr();
            }
        };
    }

    public static ResizableIcon getBigUserButton(MyFrame parent) {
        return new LightDarkResizableIcon(parent, "", LIGHT_USER, DARK_USER) {
            {
                scale = 0.4;
                defaultFontSize = 20;
            }

            private void checkUsr() {
                if (parent.getSettings().getUser() == null && !getText().isEmpty()) {
                    setText("");
                } else if (getText().isEmpty() && parent.getSettings().getUser() != null)
                    setText(parent.getSettings().getUser().getLogin());
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                checkUsr();
            }
        };
    }

    public static ResizableIcon getHelpButton(MyFrame parent) {
        return new LightDarkResizableIcon(parent, LIGHT_HELP, DARK_HELP) {
            @Override
            protected void click() {
                new HelpDialog(parent, "synopsis.title", "synopsis.text");
            }
        };
    }

    public static ResizableIcon getITMO(MyFrame parent) {
        return new LightDarkResizableIcon(parent, LIGHT_ITMO, DARK_ITMO) {
            {
                scale = 1;
            }
        };
    }

    public static ResizableIcon getDragon(MyFrame parent) {
        return new LightDarkResizableIcon(parent, LIGHT_DRAGON, DARK_DRAGON) {
            {
                scale = 1;
            }
        };
    }

    @Override
    protected void chooseImg() {
        if (parent.getSettings().isDark())
            mainImg = images[1];
        else
            mainImg = images[0];
    }
}
