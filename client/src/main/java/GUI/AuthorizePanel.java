package GUI;

import java.awt.*;

public class AuthorizePanel extends BasePanel {

    private int row = 0;

    AuthorizePanel(MyFrame parent) {
        super(parent);

        fill();
    }

    private void fill() {
        addSpacer(0.3);
        addWelcome();
        addSpacer(0.3);
        addSignIn();
        addSignUp();
        addSpacer(0.4);
    }

    private void addSpacer(double weight) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridy = row++;
        constraints.gridx = 0;
        constraints.weighty = weight;
        add(MyFrame.getSpacer(), constraints);
    }

    private void addWelcome() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = row++;

        add(ResizableIcon.getWelcome(parent), constraints);
    }

    private void addSignIn() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = row++;
        constraints.insets = new Insets(0, 0, 30, 0);
        CustomButton button = new CustomButton(parent, CustomButton.Size.MEDIUM, "all.signIn", true) {
            @Override
            protected void click() {
                parent.setStatus(PageStatus.SIGN_IN);
            }
        };
        add(button, constraints);
    }

    private void addSignUp() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = row++;
        CustomButton button = new CustomButton(parent, CustomButton.Size.MEDIUM, "all.signUp", false) {
            @Override
            protected void click() {
                parent.setStatus(PageStatus.SIGN_UP);
            }
        };
        add(button, constraints);
    }
}
