package GUI;

import java.awt.*;
import java.net.URL;

public class SwitchButton extends LightDarkResizableIcon {
    private boolean setted;

    protected SwitchButton(MyFrame parent, URL image1, URL image2, String text) {
        super(parent, image1, image2, text);
        reset();
    }

    public static SwitchButton getHomeButton(MyFrame parent) {
        return new SwitchButton(parent, LIGHT_HOME, DARK_HOME, "leftPanel.home") {
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
        return new SwitchButton(parent, LIGHT_VIEW, DARK_VIEW, "leftPanel.view") {
            @Override
            protected void click() {
                parent.setStatus(PageStatus.VIEW);
            }
        };
    }

    public static SwitchButton getTableButton(MyFrame parent) {
        return new SwitchButton(parent, LIGHT_TABLE, DARK_TABLE, "leftPanel.table") {
            @Override
            protected void click() {
                parent.setStatus(PageStatus.TABLE);
            }
        };
    }

    public static SwitchButton getCommandsButton(MyFrame parent) {
        return new SwitchButton(parent, LIGHT_COMMANDS, DARK_COMMANDS, "leftPanel.commands") {
            @Override
            protected void click() {
                if (parent.getSettings().getUser() != null)
                    parent.setStatus(PageStatus.COMMAND);
            }
        };
    }

    public void set() {
        setted = true;
        repaint();
    }

    public void reset() {
        setted = false;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (setted) {
            setFont(new Font("Arial", Font.BOLD | Font.ITALIC, fontSize));
            setForeground(parent.getSettings().getColors().get("secondColor"));
        } else {
            setFont(new Font("Arial", Font.BOLD, fontSize));
            setForeground(parent.getSettings().getColors().get("fontColor"));
        }
    }
}
