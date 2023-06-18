package GUI;

import dragons.DragonCharacter;
import settings.MyLocale;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Objects;

import static java.awt.Font.BOLD;

public class CustomComboBox extends JComboBox<String> implements GoodQuality {
    private final MyFrame parent;
    private final JLabel label;

    private CustomComboBox(String[] array, MyFrame parent) {
        super(array);
        this.parent = parent;
        label = new JLabel();
        remove(0);
        add(label);
        setRenderer(new MyBoxRenderer(parent));
    }

    public static CustomComboBox getShortLangBox(MyFrame parent) {
        String[] shortLangs = new String[MyLocale.values().length];
        int counter = 0;
        for (MyLocale locale : MyLocale.values()) {
            shortLangs[counter++] = locale.getShortName();
        }
        return new CustomComboBox(shortLangs, parent) {
            {
                addActionListener(this::changeLang);
                setSelectedIndex(parent.getSettings().getLocale().ordinal());
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setSelectedIndex(parent.getSettings().getLocale().ordinal());
            }
        };
    }

    public static CustomComboBox getLongLangBox(MyFrame parent) {
        String[] langs = new String[MyLocale.values().length];
        int counter = 0;
        for (MyLocale locale : MyLocale.values()) {
            langs[counter++] = locale.getLocaledName();
        }
        return new CustomComboBox(langs, parent) {
            {
                addActionListener(this::changeLang);
                setSelectedIndex(parent.getSettings().getLocale().ordinal());
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setSelectedIndex(parent.getSettings().getLocale().ordinal());
            }
        };
    }

    public static CustomComboBox getCharacterBox(MyFrame parent) {
        String[] characters = new String[DragonCharacter.values().length];
        int counter = 0;
        for (DragonCharacter character : DragonCharacter.values()) {
            characters[counter++] = character.name();
        }
        return new CustomComboBox(characters, parent);
    }

    public static CustomComboBox getColorBox(MyFrame parent) {
        String[] colors = new String[dragons.Color.values().length];
        int counter = 0;
        for (dragons.Color color : dragons.Color.values()) {
            colors[counter++] = color.name();
        }
        return new CustomComboBox(colors, parent);
    }

    private void updateSize(int width, int height) {
        Dimension newSize = new Dimension(width, height);
        setMinimumSize(newSize);
        setPreferredSize(newSize);
        setMaximumSize(newSize);
    }

    protected void changeLang(ActionEvent e) {
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
        if (label.getFont().getSize() != fontSize) {
            label.setFont(new Font("Arial", BOLD, fontSize));
            setFont(new Font("Arial", BOLD, fontSize));
        }
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
    }

    private static class MyBoxRenderer extends JLabel implements ListCellRenderer<String> {

        private final MyFrame parent;

        public MyBoxRenderer(MyFrame parent) {
            this.parent = parent;
            setOpaque(true);
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
