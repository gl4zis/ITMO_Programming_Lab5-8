package GUI;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;

public class SwitchButton extends LightDarkResizableIcon {
    protected boolean setted;

    protected SwitchButton(MyFrame parent, String text, URL... images) {
        super(parent, text, images);
        BufferedImage[] newImages = new BufferedImage[this.images.length * 2];
        int counter = 0;
        for (BufferedImage image : this.images) {
            newImages[counter++] = image;
            if ((counter + 1) % 4 == 0)
                newImages[counter++] = recolor(image, DARK_COLOR, parent.getSettings().getDarkColors().get("secondColor"));
            else
                newImages[counter++] = recolor(image, Color.black, parent.getSettings().getLightColors().get("secondColor"));
        }
        this.images = newImages;
        reset();
    }

    public static SwitchButton getHomeButton(MyFrame parent) {
        return new SwitchButton(parent, "leftPanel.home", LIGHT_HOME) {
            @Override
            protected void click() {
                if (parent.getSettings().getUser() != null)
                    parent.setStatus(PageStatus.HOME);
                else
                    parent.setStatus(PageStatus.AUTHORIZE);
            }
        };
    }

    public static SwitchButton getViewButton(MyFrame parent) {
        return new SwitchButton(parent, "leftPanel.view", LIGHT_VIEW) {
            @Override
            protected void click() {
                parent.setStatus(PageStatus.VIEW);
            }
        };
    }

    public static SwitchButton getTableButton(MyFrame parent) {
        return new SwitchButton(parent, "leftPanel.table", LIGHT_TABLE) {
            @Override
            protected void click() {
                parent.setStatus(PageStatus.TABLE);
            }
        };
    }

    public static SwitchButton getCommandsButton(MyFrame parent) {
        return new SwitchButton(parent, "leftPanel.commands", LIGHT_COMMANDS) {
            @Override
            protected void click() {
                if (parent.getSettings().getUser() != null)
                    parent.setStatus(PageStatus.COMMAND);
            }
        };
    }

    public void set() {
        setted = true;
        setFont(new Font("Arial", Font.BOLD | Font.ITALIC, fontSize));
    }

    public void reset() {
        setted = false;
        setFont(new Font("Arial", Font.BOLD, fontSize));
    }

    @Override
    protected void chooseImg() {
        int ind = 0;
        if (parent.getSettings().isDark()) {
            ind = 2;
        }
        if (setted)
            ind++;
        mainImg = images[ind];
    }
}
