package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public abstract class CustomButton extends JPanel implements GoodQuality {

    private final boolean isFilled;
    private final MyFrame parent;
    private final JLabel name;
    private final int height;
    private final int fontSize;
    private final String message;
    private boolean pressed = false;

    public CustomButton(MyFrame parent, Size size, String message, boolean isFilled) {
        this.isFilled = isFilled;
        this.parent = parent;
        this.message = message;
        height = size.height;
        fontSize = size.fontSize;
        setLayout(new GridBagLayout());
        setOpaque(false);
        updateSize(height);

        name = new JLabel(parent.getSettings().getLocale().getResource(message), SwingConstants.CENTER);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        add(name, constraints);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                pressed = true;
                repaint();
            }
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                pressed = false;
                repaint();
                click();
            }
        });
    }

    private void updateSize(int height) {
        Dimension size = new Dimension((int) (height * 4.7), height);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
    }

    protected abstract void click();

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        double k = parent.getKf();
        Graphics2D g2D = setGoodQ(g);

        g2D.setColor(parent.getSettings().getColors().get("secondColor"));
        g2D.setStroke(new BasicStroke((int) (k * 2)));

        if (isFilled) {
            g2D.fillRoundRect(0, 0, getWidth(), getHeight(), (int) (k * height * 3 / 4), (int) (k * height * 3 / 4));
            name.setForeground(parent.getSettings().getColors().get("mainColor"));
        } else {
            g2D.drawRoundRect((int) k, (int) k, getWidth() - (int) (k * 2), getHeight() - (int) (k * 2),
                    (int) (k * height * 3 / 4), (int) (k * height * 3 / 4));
            name.setForeground(parent.getSettings().getColors().get("fontColor"));
        }
        if (pressed)
            name.setForeground(parent.getSettings().getColors().get("secondColor"));

        name.setText(parent.getSettings().getLocale().getResource(message));
        if (name.getFont().getSize() != (int) (k * fontSize))
            name.setFont(new Font("Arial", Font.BOLD, (int) (k * fontSize)));
        updateSize((int) (k * height));
    }

    public String getMessage() {
        return message;
    }

    enum Size {
        TINY(25, 10),
        SMALL(30, 13),
        MEDIUM(40, 16);

        private final int height;
        private final int fontSize;

        Size(int height, int fontSize) {
            this.height = height;
            this.fontSize = fontSize;
        }
    }
}
