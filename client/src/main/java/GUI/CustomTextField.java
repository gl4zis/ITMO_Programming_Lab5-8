package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class CustomTextField extends JPasswordField implements GoodQuality {
    private final MyFrame parent;
    private final int height;
    private final int fontSize;
    private final String msg;
    private final boolean hidden;
    private String clientMsg;
    private boolean focused;
    private char hideEchoChar;
    private char realEchoChar;

    public CustomTextField(MyFrame parent, Size size, String msg, boolean hidden) {
        super(parent.getSettings().getLocale().getResource(msg));
        this.msg = msg;
        this.hidden = hidden;
        clientMsg = "";
        this.parent = parent;
        this.height = size.height;
        fontSize = size.fontSize;
        updateSize((int) (height * parent.getKf()));
        if (hidden) {
            add(LightDarkResizableIcon.getEyeButton(parent));
            hideEchoChar = getEchoChar();
            realEchoChar = hideEchoChar;
        }
        addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
                focused = true;
                repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                focused = false;
                repaint();
            }
        });
        getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (focused)
                    clientMsg = new String(getPassword());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (focused)
                    clientMsg = new String(getPassword());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if (focused)
                    clientMsg = new String(getPassword());
            }
        });
    }

    @Override
    @Deprecated
    public String getText() {
        return clientMsg;
    }

    private void updateSize(int height) {
        Dimension size = new Dimension(height * 5, height);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
    }

    public void swapHideMode() {
        if (hidden) {
            if (realEchoChar == hideEchoChar) {
                realEchoChar = (char) 0;
            } else {
                realEchoChar = hideEchoChar;
            }
        }
    }

    private void setMsg() {
        setBackground(parent.getSettings().getColors().get("mainColor"));
        setCaretColor(parent.getSettings().getColors().get("fontColor"));
        String newText;
        char newEcho;
        if (clientMsg.isEmpty() && !focused) {
            newEcho = (char) 0;
            newText = parent.getSettings().getLocale().getResource(msg);
            setForeground(parent.getSettings().getColors().get("secondFontColor"));
        } else {
            newEcho = realEchoChar;
            newText = clientMsg;
            setForeground(parent.getSettings().getColors().get("fontColor"));
        }
        if (getEchoChar() != newEcho)
            setEchoChar(newEcho);
        if (!new String(getPassword()).equals(newText))
            setText(newText);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        double k = parent.getKf();
        if (getFont().getSize() != (int) (k * fontSize)) {
            setBorder(new EmptyBorder((int) (10 * k), (int) (10 * k), (int) (10 * k), (int) (10 * k)));
            setFont(new Font("Arial", Font.ITALIC, (int) (k * fontSize)));
        }

        Graphics2D g2D = setGoodQ(g);
        g2D.setColor(parent.getSettings().getColors().get("secondColor"));
        g2D.setStroke(new BasicStroke((int) (k * 2)));
        g2D.drawRoundRect((int) k, (int) k, getWidth() - (int) (k * 2), getHeight() - (int) (k * 2), (int) (k * 6), (int) (k * 6));

        setLayout(new FlowLayout(FlowLayout.RIGHT, 0, (int) (-6 * k)));
        updateSize((int) (height * k));
        setMsg();
    }

    enum Size {
        SMALL(35, 12),
        MEDIUM(40, 14);

        private final int height;
        private final int fontSize;

        Size(int height, int fontSize) {
            this.height = height;
            this.fontSize = fontSize;
        }
    }
}
