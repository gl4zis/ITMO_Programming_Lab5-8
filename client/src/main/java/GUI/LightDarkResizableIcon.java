package GUI;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;

public class LightDarkResizableIcon extends ResizableIcon {
    protected static final Color DARK_COLOR = new Color(221, 221, 221);

    protected LightDarkResizableIcon(MyFrame parent, String text, URL... images) {
        super(parent, text, images);
        BufferedImage[] newImages = new BufferedImage[this.images.length * 2];
        int counter = 0;
        for (BufferedImage image : this.images) {
            newImages[counter++] = image;
            newImages[counter++] = recolor(image, Color.black, DARK_COLOR);
        }
        this.images = newImages;
    }

    protected LightDarkResizableIcon(MyFrame parent, URL... images) {
        this(parent, null, images);
    }

    public static ResizableIcon getEyeButton(MyFrame parent) {
        return new LightDarkResizableIcon(parent, LIGHT_CLOSE_EYE, LIGHT_OPEN_EYE) {
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

    public static ResizableIcon getUserButton(MyFrame parent) {
        return new LightDarkResizableIcon(parent, "", LIGHT_USER) {
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
        return new LightDarkResizableIcon(parent, "", LIGHT_USER) {
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
        return new LightDarkResizableIcon(parent, LIGHT_HELP) {
            @Override
            protected void click() {
                new HelpDialog(parent, "synopsis.title", "synopsis.text");
            }
        };
    }

    public static ResizableIcon getITMO(MyFrame parent) {
        return new LightDarkResizableIcon(parent, LIGHT_ITMO) {
            {
                scale = 1;
            }
        };
    }

    public static ResizableIcon getDragon(MyFrame parent) {
        return new LightDarkResizableIcon(parent, LIGHT_DRAGON) {
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
