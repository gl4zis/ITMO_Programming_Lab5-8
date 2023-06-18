package GUI;

import dragons.Color;
import dragons.Coordinates;
import dragons.Dragon;
import dragons.DragonCharacter;
import dragons.DragonHead;
import settings.Settings;

import javax.swing.*;
import java.awt.*;
import java.util.Set;

import static dragons.Color.RED;

public class DragonDialog extends JDialog {

    private final Settings settings;
    private boolean canceled = true;
    private final WarningLabel warn;
    private final JLabel label;
    private final JTextField[] fields = new JTextField[6];
    private final CustomComboBox[] boxes = new CustomComboBox[2];

    public DragonDialog(Settings settings) {
        super(settings.getMainWindow(), "Dragon", true);
        this.settings = settings;

        warn = new WarningLabel(settings.getMainWindow(), "Some field is incorrect");
        label = new JLabel("Age can be empty");
        label.setFont(new Font("Aral", Font.BOLD, (int) (14 * settings.getMainWindow().getKf())));
        fields[0] = new CustomTextField(settings.getMainWindow(), CustomTextField.Size.SMALL, "dragon.name", false);
        fields[1] = new CustomTextField(settings.getMainWindow(), CustomTextField.Size.SMALL, "X", false);
        fields[2] = new CustomTextField(settings.getMainWindow(), CustomTextField.Size.SMALL, "Y", false);
        fields[3] = new CustomTextField(settings.getMainWindow(), CustomTextField.Size.SMALL, "dragon.weight", false);
        fields[4] = new CustomTextField(settings.getMainWindow(), CustomTextField.Size.SMALL, "dragon.age", false);
        fields[5] = new CustomTextField(settings.getMainWindow(), CustomTextField.Size.SMALL, "dragon.eyes_count", false);
        boxes[0] = CustomComboBox.getCharacterBox(settings.getMainWindow());
        boxes[1] = CustomComboBox.getColorBox(settings.getMainWindow());

        fill();
        pack();
        Rectangle windowSize = settings.getMainWindow().getBounds();
        setLocation(windowSize.x + windowSize.width / 2 - getWidth() / 2, windowSize.y + windowSize.height / 2 - getHeight() / 2);
        setResizable(false);
        setVisible(true);
    }

    private void fill() {
        Container root = getContentPane();
        root.setLayout(new GridLayout(6, 2));
        root.setBackground(settings.getColors().get("mainColor"));
        root.add(warn);
        root.add(label);
        root.add(fields[0]);
        root.add(fields[4]);
        root.add(fields[1]);
        root.add(boxes[0]);
        root.add(fields[2]);
        root.add(boxes[1]);
        root.add(fields[3]);
        root.add(fields[5]);
        getContentPane().add(new CustomButton(settings.getMainWindow(), CustomButton.Size.SMALL, "all.cancel", false) {
            @Override
            protected void click() {
                dispose();
            }
        });
        getContentPane().add(new CustomButton(settings.getMainWindow(), CustomButton.Size.MEDIUM, "all.confirm", true) {
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
        try {
            if (fields[0].getText().isEmpty())
                return false;
            if (Double.parseDouble(fields[1].getText()) <= -497D)
                return false;
            Float.parseFloat(fields[2].getText());
            if (Long.parseLong(fields[3].getText()) <= 0)
                return false;
            if (!fields[4].getText().isEmpty() && Integer.parseInt(fields[4].getText()) <= 0)
                return false;
            Float.parseFloat(fields[5].getText());
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }

    public Dragon getDragon() {
        if (canceled)
            return null;
        else {
            String name = fields[0].getText();
            double X = Double.parseDouble(fields[1].getText());
            float Y = Float.parseFloat(fields[2].getText());
            Coordinates coordinates = new Coordinates(X, Y);
            long weight = Long.parseLong(fields[3].getText());
            float eyes = Float.parseFloat(fields[5].getText());
            DragonHead head = new DragonHead(eyes);
            DragonCharacter character = DragonCharacter.getByName((String) boxes[0].getSelectedItem());
            Color color = Color.getByName((String) boxes[1].getSelectedItem());

            Dragon dragon = new Dragon(name, coordinates, weight, color, character, head, settings.getUser());

            if (!fields[4].getText().isEmpty())
                dragon.setAge(Integer.parseInt(fields[4].getText()));
            return dragon;
        }
    }
}
