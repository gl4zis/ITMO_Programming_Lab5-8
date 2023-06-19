package GUI;

import javax.swing.*;
import java.awt.*;

public class WarningLabel extends JLabel {

    private final MyFrame parent;
    private final String message;
    private int fontSize;

    public WarningLabel(MyFrame parent, String message) {
        this.parent = parent;
        this.message = message;
        fontSize = 14;
        setText(parent.getSettings().getLocale().getResource(message));
        setVisible(false);
    }

    public WarningLabel(MyFrame parent, String message, int fontSize) {
        this(parent, message);
        this.fontSize = fontSize;
    }

    public void showMessage() {
        setVisible(true);
    }

    public void hideMessage() {
        setVisible(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        double k = parent.getKf();
        setFont(new Font("Arial", Font.BOLD, (int) (k * fontSize)));
        setForeground(parent.getSettings().getColors().get("warningFontColor"));
        setText(parent.getSettings().getLocale().getResource(message));
    }
}
