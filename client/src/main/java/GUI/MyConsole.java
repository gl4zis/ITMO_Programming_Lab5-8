package GUI;

import javax.swing.*;
import java.awt.*;

public class MyConsole extends JPanel {
    private static String oldText = "";
    private final MyFrame parent;
    private final int fontSize = 12;
    private final JTextArea text;

    private MyConsole(MyFrame parent) {
        this.parent = parent;

        setLayout(new BorderLayout());
        text = new JTextArea() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                paintTextArea();
            }
        };
        text.setBorder(BorderFactory.createEmptyBorder());
        text.setForeground(new Color(221, 221, 221));
        text.setBackground(new Color(43, 43, 43));
        text.setEditable(false);
        text.setEditable(false);
        text.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, fontSize));
        JScrollPane pane = new JScrollPane(text, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        pane.getVerticalScrollBar().setUI(new MyScrollBarUI(parent, false));
        pane.setBorder(BorderFactory.createEmptyBorder());
        add(pane, BorderLayout.CENTER);
        text.setText(oldText);
    }

    public static void reset() {
        oldText = "";
    }

    public static void initialize(MyFrame parent) {
        if (Holder.INSTANCE == null)
            Holder.INSTANCE = new MyConsole(parent);
    }

    public static MyConsole getInstance() {
        return Holder.INSTANCE;
    }

    public void addText(String newStr) {
        String oldStr = text.getText();
        if (oldStr.isEmpty())
            newStr = "  " + newStr.replaceAll("\n", "\n  ");
        else
            newStr = oldStr + "\n  " + newStr.replaceAll("\n", "\n  ");
        text.setText(newStr);
        oldText = newStr;
    }

    private void paintTextArea() {
        double k = parent.getKf();
        if (text.getFont().getSize() != (int) (k * fontSize))
            text.setFont(text.getFont().deriveFont((float) (k * fontSize)));
    }

    private static class Holder {
        public static MyConsole INSTANCE;
    }
}