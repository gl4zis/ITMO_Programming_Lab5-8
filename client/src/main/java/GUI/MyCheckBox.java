package GUI;

import javax.swing.*;
import java.awt.*;

public class MyCheckBox extends JCheckBox {

    private final MyFrame parent;
    private final String message;

    public MyCheckBox(MyFrame parent, String message) {
        super(parent.getSettings().getLocale().getResource(message));
        this.parent = parent;
        this.message = message;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        double k = parent.getKf();
        setFont(new Font("Arial", Font.PLAIN, (int) (k * 12)));
        setBackground(parent.getSettings().getColors().get("mainColor"));
        setForeground(parent.getSettings().getColors().get("fontColor"));
        setText(parent.getSettings().getLocale().getResource(message));
    }
}
