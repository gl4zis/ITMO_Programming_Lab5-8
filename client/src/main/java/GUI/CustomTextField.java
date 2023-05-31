package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class CustomTextField extends JPasswordField implements GoodQuality {
    private final MyFrame parent;
    private final int height;
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
        updateSize(height);
        if (hidden) {
            add(LightDarkResizableIcon.getEyeButton(parent));
            hideEchoChar = getEchoChar();
            realEchoChar = hideEchoChar;
        }
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                focused = true;
            }
        });
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                focused = false;
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
        Dimension size = new Dimension(height * 6, height);
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
        if (clientMsg.isEmpty() && !focused) {
            setEchoChar((char) 0);
            setText(parent.getSettings().getLocale().getResource(msg));
            setForeground(parent.getSettings().getColors().get("secondFontColor"));
        } else {
            setEchoChar(realEchoChar);
            setText(clientMsg);
            setForeground(parent.getSettings().getColors().get("fontColor"));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        double k = parent.getKf();
        setBorder(new EmptyBorder((int) (10 * k), (int) (10 * k), (int) (10 * k), (int) (10 * k)));
        setFont(new Font("Arial", Font.ITALIC, (int) (k * 14)));

        Graphics2D g2D = setGoodQ(g);
        g2D.setColor(parent.getSettings().getColors().get("secondColor"));
        g2D.setStroke(new BasicStroke((int) (k * 2)));
        g2D.drawRoundRect((int) k, (int) k, getWidth() - (int) (k * 2), getHeight() - (int) (k * 2), (int) (k * 6), (int) (k * 6));

        setLayout(new FlowLayout(FlowLayout.RIGHT, 0, (int) (-6 * k)));
        updateSize((int) (height * k));
        setMsg();
    }

    enum Size {
        SMALL(30),
        MEDIUM(35);

        private final int height;

        Size(int height) {
            this.height = height;
        }
    }
}
