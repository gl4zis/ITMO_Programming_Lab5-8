package GUI;

import javax.swing.*;
import java.awt.*;

public class MyConsole extends JPanel {
    private final MyFrame parent;
    private final int fontSize = 12;
    private final JTextArea text;
    private static String oldText = "";

    public MyConsole(MyFrame parent) {
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

    public void addText(String newStr) {
        String oldStr = text.getText();
        if (oldStr.isEmpty())
            newStr = "  " + newStr.replaceAll("\n", "\n  ");
        else
            newStr = oldStr + "\n  " + newStr.replaceAll("\n", "\n  ");
        text.setText(newStr);
        oldText = newStr;
        SwingUtilities.invokeLater(text::repaint);
    }

    private void paintTextArea() {
        double k = parent.getKf();
        if (text.getFont().getSize() != (int) (k * fontSize))
            text.setFont(text.getFont().deriveFont((float) (k * fontSize)));
    }
}