package GUI;

import settings.MyLocale;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Objects;

import static java.awt.Font.BOLD;

public class LangBox extends JComboBox<String> implements GoodQuality {
    private final JLabel label;
    private final MyFrame parent;

    private LangBox(String[] langs, MyFrame parent) {
        super(langs);
        this.parent = parent;
        label = new JLabel((String) getSelectedItem(), SwingConstants.CENTER);
        add(label, 0);
        remove(1);
        addActionListener(this::changeLang);
        setSelectedIndex(parent.getSettings().getLocale().ordinal());
        setRenderer(new LangBoxRenderer(parent));
        setEditable(true);
    }

    public static LangBox getShortBox(MyFrame parent) {
        String[] shortLangs = new String[MyLocale.values().length];
        int counter = 0;
        for (MyLocale locale : MyLocale.values()) {
            shortLangs[counter++] = locale.getShortName();
        }
        return new LangBox(shortLangs, parent);
    }

    public static LangBox getLongBox(MyFrame parent) {
        String[] langs = new String[MyLocale.values().length];
        int counter = 0;
        for (MyLocale locale : MyLocale.values()) {
            langs[counter++] = locale.getLocaledName();
        }
        return new LangBox(langs, parent);
    }

    private void updateSize(int width, int height) {
        Dimension newSize = new Dimension(width, height);
        setMinimumSize(newSize);
        setPreferredSize(newSize);
        setMaximumSize(newSize);
    }

    private void changeLang(ActionEvent e) {
        MyLocale newLocale = Objects.requireNonNull(MyLocale.getByName((String) getSelectedItem()));
        if (!parent.getSettings().getLocale().equals(newLocale)) {
            parent.getSettings().setLocale(newLocale);
            parent.repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        double k = parent.getKf();
        int fontSize = (int) (k * 16);
        if (label.getFont().getSize() != fontSize)
            label.setFont(new Font("Arial", BOLD, fontSize));
        label.setText((String) getSelectedItem());
        label.setForeground(parent.getSettings().getColors().get("fontColor"));

        Graphics2D g2D = setGoodQ(g);
        g2D.setColor(parent.getSettings().getColors().get("mainColor"));
        g2D.setFont(label.getFont());
        g2D.fillRect(0, 0, getWidth(), getHeight());
        g2D.setColor(parent.getSettings().getColors().get("secondColor"));
        g2D.setStroke(new BasicStroke((int) (k * 2)));
        g2D.drawRoundRect((int) k, (int) k, getWidth() - (int) (k * 2), getHeight() - (int) (k * 2), (int) (k * 6), (int) (k * 6));

        int width = (int) (g2D.getFontMetrics().stringWidth((String) Objects.requireNonNull(getSelectedItem())) * 1.6);
        int height = fontSize * 2;
        setLayout(new FlowLayout(FlowLayout.LEFT, (int) (width * 0.15), (int) (fontSize / 2.3)));
        updateSize(width, height);
        setSelectedIndex(parent.getSettings().getLocale().ordinal());
    }

    private static class LangBoxRenderer extends JLabel implements ListCellRenderer<String> {

        private final MyFrame parent;
        private double oldKf;

        public LangBoxRenderer(MyFrame parent) {
            this.parent = parent;
            setOpaque(true);
            setSettings();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            setSettings();
        }

        private void setSettings() {
            if (oldKf != parent.getKf()) {
                oldKf = parent.getKf();
                setFont(new Font("Arial", BOLD, (int) (parent.getKf() * 16)));
            }
            Color newBackground = parent.getSettings().getColors().get("mainColor");
            Color newForeground = parent.getSettings().getColors().get("fontColor");
            if (!getForeground().equals(newForeground))
                setForeground(newForeground);
            if (!getBackground().equals(newBackground))
                setBackground(newBackground);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends String> list, String value,
                                                      int index, boolean isSelected, boolean cellHasFocus) {
            Color background;
            if (isSelected)
                background = parent.getSettings().getColors().get("secondColor");
            else
                background = parent.getSettings().getColors().get("mainColor");

            setBackground(background);
            setForeground(parent.getSettings().getColors().get("fontColor"));
            setText(value);
            return this;
        }
    }

}