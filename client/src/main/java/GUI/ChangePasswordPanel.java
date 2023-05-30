package GUI;

import user.User;

import javax.swing.*;
import java.awt.*;

public class ChangePasswordPanel extends BasePanel {

    private final JLabel user;
    private final JTextField oldPass;
    private final WarningLabel incOld;
    private final JTextField newPass;
    private final JTextField repPass;
    private final WarningLabel notMatch;
    private int row = 0;

    public ChangePasswordPanel(MyFrame parent) {
        super(parent);
        user = LightDarkResizableIcon.getBigUserButton(parent);
        oldPass = new CustomTextField(parent, CustomTextField.Size.MEDIUM, "chPass.old", true);
        incOld = new WarningLabel(parent, "chPass.incOld");
        newPass = new CustomTextField(parent, CustomTextField.Size.MEDIUM, "chPass.new", true);
        repPass = new CustomTextField(parent, CustomTextField.Size.MEDIUM, "signUp.repPassword", true);
        notMatch = new WarningLabel(parent, "signUp.notMatch");

        fill();
    }

    private void fill() {
        addSpacer(0.1);
        addComponent(user);
        addSpacer(0.05);
        addComponent(oldPass);
        addComponent(incOld);
        addSpacer(0.05);
        addComponent(newPass);
        addSpacer(0.05);
        addComponent(repPass);
        addComponent(notMatch);
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
        panel.add(new CustomButton(parent, CustomButton.Size.SMALL, "all.cancel", false) {
            @Override
            protected void click() {
                parent.setStatus(PageStatus.HOME);
            }
        });
        panel.add(new CustomButton(parent, CustomButton.Size.SMALL, "all.confirm", true) {
            @Override
            protected void click() {
                if (check()) {
                    parent.getSettings().setUser(User.signUp(parent.getSettings().getUser().getLogin(), newPass.getText()));
                    parent.setStatus(PageStatus.HOME);
                }
            }
        });
        add(panel, constraints);
    }

    private boolean check() {
        String oldHashedPasswd = User.hashPasswd(oldPass.getText(), 500);
        String newPassw = newPass.getText();
        String repPassw = repPass.getText();
        String newHashedPasswd = User.hashPasswd(newPassw, 500);

        boolean right = true;

        if (!newPassw.equals(repPassw)) {
            notMatch.showMessage();
            right = false;
        } else
            notMatch.hideMessage();
        if (!parent.getSettings().getUser().getHashedPasswd().equals(oldHashedPasswd)) {
            incOld.showMessage();
            right = false;
        } else
            incOld.hideMessage();

        if (right) {
            right = parent.getSettings().getConnection().changePasswd(parent.getSettings().getUser(),
                    newHashedPasswd);
        }
        return right;
    }
}
