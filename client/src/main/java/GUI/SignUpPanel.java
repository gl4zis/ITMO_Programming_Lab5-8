package GUI;

import user.User;

import javax.swing.*;
import java.awt.*;

public class SignUpPanel extends BasePanel {
    private final WarningLabel loginMsg;
    private final JTextField login;
    private final WarningLabel loginCol;
    private final JTextField password;
    private final JTextField repPassword;
    private final WarningLabel unmatchPass;
    private final MyCheckBox save;
    private int row = 0;

    public SignUpPanel(MyFrame parent) {
        super(parent);
        loginMsg = new WarningLabel(parent, "signUp.loginMsg");
        login = new CustomTextField(parent, CustomTextField.Size.MEDIUM, "all.login", false);
        loginCol = new WarningLabel(parent, "signUp.loginCol");
        password = new CustomTextField(parent, CustomTextField.Size.MEDIUM, "all.password", true);
        repPassword = new CustomTextField(parent, CustomTextField.Size.MEDIUM, "signUp.repPassword", true);
        unmatchPass = new WarningLabel(parent, "signUp.notMatch");
        save = new MyCheckBox(parent, "signIn.saveMe");

        fill();
    }

    private void fill() {
        addSpacer(0.2);
        addComponent(loginMsg);
        addComponent(login);
        addComponent(loginCol);
        addSpacer(0.1);
        addComponent(password);
        addSpacer(0.1);
        addComponent(repPassword);
        addComponent(unmatchPass);
        addSpacer(0.03);
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
        panel.add(new CustomButton(parent, CustomButton.Size.SMALL, "all.signUp", true) {
            @Override
            protected void click() {
                if (checkInfo()) {
                    parent.getSettings().setUser(User.signUp(login.getText(), password.getText()));
                    parent.setStatus(PageStatus.HOME);
                } else if (parent.getSettings().getConnection().connected)
                    loginCol.showMessage();
            }
        });
        add(panel, constraints);
    }

    private boolean checkInfo() {
        String login = this.login.getText();
        String password = this.password.getText();
        String repPassword = this.repPassword.getText();
        parent.getSettings().saveUser(save.isSelected());
        User newUser = User.signUp(login, password);

        loginCol.hideMessage();

        boolean right = true;
        if (!password.equals(repPassword)) {
            unmatchPass.showMessage();
            right = false;
        } else
            unmatchPass.hideMessage();
        if (!login.matches("\\w+")) {
            loginMsg.showMessage();
            right = false;
        } else
            loginMsg.hideMessage();
        if (password.isEmpty())
            right = false;
        if (right) {
            right = parent.getSettings().getConnection().signUp(newUser);
            parent.checkConnect();
        }
        return right;
    }
}