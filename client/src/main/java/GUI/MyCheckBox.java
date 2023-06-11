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
        int defaultFontSize = 12;
        if (getFont().getSize() != (int) (k * defaultFontSize))
            setFont(new Font("Arial", Font.PLAIN, (int) (k * defaultFontSize)));
        setBackground(parent.getSettings().getColors().get("mainColor"));
        setForeground(parent.getSettings().getColors().get("fontColor"));
        setText(parent.getSettings().getLocale().getResource(message));
    }
}
