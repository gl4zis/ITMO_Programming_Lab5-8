package GUI;

import javax.swing.*;
import java.awt.*;

public class NumberDialog extends JDialog {
    private final WarningLabel warn;
    private final JTextField field;
    private final MyFrame parent;
    private final Class<? extends Number> varType;
    private boolean canceled = true;

    public NumberDialog(MyFrame parent, String varName, Class<? extends Number> varType) {
        super(parent, varName, true);
        this.parent = parent;
        this.varType = varType;

        warn = new WarningLabel(parent, "dialog.incorrect");
        field = new CustomTextField(parent, CustomTextField.Size.MEDIUM, varName, false);

        getContentPane().setBackground(parent.getSettings().getColors().get("mainColor"));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        fill();
        pack();
        Rectangle windowSize = parent.getBounds();
        setLocation(windowSize.x + windowSize.width / 2 - getWidth() / 2, windowSize.y + windowSize.height / 2 - getHeight() / 2);
        setResizable(false);
        setVisible(true);
    }

    private void fill() {
        Container root = getContentPane();
        root.setLayout(new GridLayout(3, 1));
        add(warn);
        add(field);
        add(new CustomButton(parent, CustomButton.Size.SMALL, "all.confirm", true) {
            @Override
            protected void click() {
                if (check()) {
                    canceled = false;
                    dispose();
                } else warn.showMessage();
            }
        });
    }

    private boolean check() {
        String inputted = field.getText();
        try {
            switch (varType.getSimpleName()) {
                case "Integer":
                    Integer.parseInt(inputted);
                case "Long":
                    Long.parseLong(inputted);
            }
        } catch (NullPointerException | NumberFormatException e) {
            return false;
        }
        return true;
    }

    public Number getVar() {
        if (canceled)
            return null;
        else {
            String inputted = field.getText();
            return switch (varType.getSimpleName()) {
                case "Integer" -> Integer.parseInt(inputted);
                case "Long" -> Long.parseLong(inputted);
                default -> null;
            };
        }
    }
}
