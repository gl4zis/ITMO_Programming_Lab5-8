package GUI;

import user.User;

import javax.swing.*;
import java.awt.*;

public class SignInPanel extends BasePanel {

    private final JTextField login;
    private final JTextField password;
    private final WarningLabel warning;
    private final JCheckBox save;
    private int row = 0;

    public SignInPanel(MyFrame parent) {
        super(parent);
        login = new CustomTextField(parent, CustomTextField.Size.MEDIUM, "all.login", false);
        password = new CustomTextField(parent, CustomTextField.Size.MEDIUM, "all.password", true);
        warning = new WarningLabel(parent, "signIn.warning");
        save = new MyCheckBox(parent, "signIn.saveMe");

        fill();
    }

    private void fill() {
        addSpacer(0.2);
        addComponent(login);
        addSpacer(0.05);
        addComponent(warning);
        addSpacer(0.05);
        addComponent(password);
        addSpacer(0.05);
        addComponent(save);
        addSpacer(0.05);
        addButtons();
        addSpacer(0.2);
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
        panel.add(new CustomButton(parent, CustomButton.Size.SMALL, "all.cancel", false) {
            @Override
            protected void click() {
                parent.setStatus(PageStatus.AUTHORIZE);
            }
        });
        panel.add(new CustomButton(parent, CustomButton.Size.SMALL, "all.signIn", true) {
            @Override
            protected void click() {
                if (check()) {
                    parent.getSettings().setUser(User.signUp(login.getText(), password.getText()));
                    parent.setStatus(PageStatus.HOME);
                    parent.getSettings().saveUser(save.isSelected());
                } else if (parent.getSettings().getConnection().connected)
                    warning.showMessage();
                else parent.checkConnect();
            }
        });
        add(panel, constraints);
    }

    private boolean check() {
        String login = this.login.getText();
        String password = this.password.getText();
        User newUser = User.signUp(login, password);

        return parent.getSettings().getConnection().signIn(newUser);
    }
}
