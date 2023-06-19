package GUI;


import javax.swing.*;
import java.awt.*;

public class HelpDialog extends JDialog {

    public HelpDialog(MyFrame parent, String title, String message) {
        super(parent, parent.getSettings().getLocale().getResource(title), true);
        add(new DialogPanel(parent, message));
        pack();
        Rectangle windowSize = parent.getBounds();
        setLocation(windowSize.x + windowSize.width / 2 - getWidth() / 2, windowSize.y + windowSize.height / 2 - getHeight() / 2);
        setResizable(false);
        setVisible(true);
    }

    class DialogPanel extends JPanel implements GoodQuality {
        private final MyFrame parent;
        private final String message;

        public DialogPanel(MyFrame parent, String message) {
            this.parent = parent;
            this.message = message;
            setLayout(new BorderLayout());
            setBackground(parent.getSettings().getColors().get("mainColor"));
            addLabel();
            addButton();
        }

        private void addButton() {
            JPanel spacer = MyFrame.getSpacer();
            spacer.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
            spacer.add(MyFrame.getSpacer());
            CustomButton button = new CustomButton(parent, CustomButton.Size.SMALL, "all.confirm", false) {
                @Override
                protected void click() {
                    HelpDialog.this.dispose();
                }
            };
            spacer.add(button);
            spacer.add(MyFrame.getSpacer());
            add(spacer, BorderLayout.SOUTH);
        }

        private void addLabel() {
            JTextArea label = new JTextArea(10, 20);
            label.setText(' ' + parent.getSettings().getLocale().getResource(message));
            label.setLineWrap(true);
            label.setEditable(false);
            label.setBorder(BorderFactory.createEmptyBorder());
            label.setFont(new Font("Arial", Font.BOLD, (int) (14 * parent.getKf())));
            label.setForeground(parent.getSettings().getColors().get("fontColor"));
            label.setBackground(parent.getSettings().getColors().get("mainColor"));
            JScrollPane pane = new JScrollPane(label, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            pane.getVerticalScrollBar().setUI(new MyScrollBarUI(parent));
            pane.setBorder(BorderFactory.createEmptyBorder());
            add(pane, BorderLayout.CENTER);
        }
    }
}
