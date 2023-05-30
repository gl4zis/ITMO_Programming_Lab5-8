package GUI;

import javax.swing.*;
import java.awt.*;

public class WarningLabel extends JLabel {

    private final MyFrame parent;
    private final String message;

    public WarningLabel(MyFrame parent, String message) {
        this.parent = parent;
        this.message = message;
        setText(parent.getSettings().getLocale().getResource(message));
        setVisible(false);
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
        setFont(new Font("Arial", Font.BOLD, (int) (k * 14)));
        setForeground(parent.getSettings().getColors().get("warningFontColor"));
        setText(parent.getSettings().getLocale().getResource(message));
    }
}
