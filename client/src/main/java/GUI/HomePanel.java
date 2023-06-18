package GUI;

import javax.swing.*;
import java.awt.*;

public class HomePanel extends BasePanel {

    private final JLabel user;
    private final JComboBox<String> locale;
    private final JLabel theme;
    private int row = 0;

    public HomePanel(MyFrame parent) {
        super(parent);

        user = LightDarkResizableIcon.getBigUserButton(parent);
        locale = CustomComboBox.getLongLangBox(parent);
        theme = LightDarkResizableIcon.getBigThemeButton(parent);

        fill();
    }

    private void fill() {
        addSpacer(0.1);
        addComponent(user);
        addSpacer(0.05);
        addComponent(locale);
        addSpacer(0.05);
        addComponent(theme);
        addSpacer(0.05);
        addButtons();
        addSpacer(0.1);
    }

    private void addSpacer(double weight) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = row++;
        constraints.weighty = weight;
        add(MyFrame.getSpacer(), constraints);
    }

    private void addComponent(Component c) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = row++;
        add(c, constraints);
    }

    private void addButtons() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = row++;
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setBackground(parent.getSettings().getColors().get("mainColor"));
            }
        };
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 0));
        panel.add(new CustomButton(parent, CustomButton.Size.SMALL, "home.changePassword", false) {
            @Override
            protected void click() {
                parent.setStatus(PageStatus.CHANGE_PASSW);
            }
        });
        panel.add(new CustomButton(parent, CustomButton.Size.SMALL, "home.signOut", true) {
            @Override
            protected void click() {
                parent.getSettings().setUser(null);
                parent.setStatus(PageStatus.AUTHORIZE);
                parent.repaint();
            }
        });
        add(panel, constraints);
    }
}
