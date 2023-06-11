package GUI;


import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

public class HelpDialog extends JDialog {

    public HelpDialog(MyFrame parent, String title, String message) {
        super(parent, parent.getSettings().getLocale().getResource(title), true);
        add(new DialogPanel(parent, message));
        pack();
        setSize(getWidth() + 20, getHeight());
        Rectangle windowSize = parent.getBounds();
        setLocation(windowSize.x + windowSize.width / 2 - getWidth() / 2, windowSize.y + windowSize.height / 2 - getHeight() / 2);
        setResizable(false);
        setVisible(true);
    }

    private static class MyScrollBarUi extends BasicScrollBarUI implements GoodQuality {

        private final MyFrame parent;

        public MyScrollBarUi(MyFrame parent) {
            this.parent = parent;
        }

        protected JButton createZeroButton() {
            JButton button = new JButton("zero button");
            Dimension zeroDim = new Dimension(0, 0);
            button.setPreferredSize(zeroDim);
            button.setMinimumSize(zeroDim);
            button.setMaximumSize(zeroDim);
            return button;
        }

        @Override
        protected JButton createIncreaseButton(int i) {
            return createZeroButton();
        }

        @Override
        protected JButton createDecreaseButton(int i) {
            return createZeroButton();
        }

        @Override
        protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
            if (thumbBounds.isEmpty() || !scrollbar.isEnabled()) {
                return;
            }

            Graphics2D g2d = setGoodQ(g);
            g2d.setColor(parent.getSettings().getColors().get("secondColor"));
            g2d.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, 10, 10);
        }

        @Override
        protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
            Graphics2D g2d = setGoodQ(g);
            g2d.setColor(parent.getSettings().getColors().get("borderColor"));
            g2d.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
        }
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
            CustomButton button = new CustomButton(parent, CustomButton.Size.SMALL, "all.confirm", false) {
                @Override
                protected void click() {
                    HelpDialog.this.dispose();
                }
            };
            add(button, BorderLayout.SOUTH);
        }

        private void addLabel() {
            JTextArea label = new JTextArea(10, 20);
            label.setText(parent.getSettings().getLocale().getResource(message));
            label.setLineWrap(true);
            label.setEditable(false);
            label.setFont(new Font("Arial", Font.BOLD, (int) (14 * parent.getKf())));
            label.setForeground(parent.getSettings().getColors().get("fontColor"));
            label.setBackground(parent.getSettings().getColors().get("mainColor"));
            add(label, BorderLayout.CENTER);
            JScrollPane pane = new JScrollPane(label, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            pane.getVerticalScrollBar().setUI(new MyScrollBarUi(parent));
            pane.setBorder(BorderFactory.createEmptyBorder());
            add(pane, BorderLayout.EAST);
        }
    }
}
