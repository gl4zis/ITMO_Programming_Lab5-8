package GUI;

import java.awt.*;
import java.net.URL;

public class SwitchButton extends LightDarkResizableIcon {
    protected boolean setted;

    protected SwitchButton(MyFrame parent, String text, URL... images) {
        super(parent, text, images);
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
        if (parent.getSettings().isDark()) {
            if (setted)
                mainImg = recolor(images[1], DARK_COLOR, parent.getSettings().getColors().get("secondColor"));
            else
                mainImg = images[1];
        } else {
            if (setted)
                mainImg = recolor(images[0], Color.black, parent.getSettings().getColors().get("secondColor"));
            else
                mainImg = images[0];
        }
    }
}
