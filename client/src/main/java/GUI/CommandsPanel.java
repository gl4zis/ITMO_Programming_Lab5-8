package GUI;

import commands.CommandType;

import java.awt.*;

public class CommandsPanel extends BasePanel {
    private final CustomButton[] buttons;
    private final MyConsole console;

    public CommandsPanel(MyFrame parent) {
        super(parent);
        console = new MyConsole(parent);
        buttons = new CustomButton[12];
        int counter = 0;
        for (CommandType commandType : CommandType.values()) {
            if (commandType.haveSelfButton()) {
                buttons[counter++] = new CustomButton(parent, CustomButton.Size.TINY, commandType.getName(), false) {
                    @Override
                    protected void click() {
                        executeCommand(commandType.getName());
                    }
                };
            }
        }
        buttons[11] = new CustomButton(parent, CustomButton.Size.TINY, "execute_script", false) {
            @Override
            protected void click() {
                executeCommand("execute_script");
            }
        };
        setLayout(new GridBagLayout());
        addConsole();
        addButtons();
    }

    private void executeCommand(String commandName) {
        parent.getSettings().getProcessor().execute(commandName, console);
    }

    private void addConsole() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridwidth = 7;
        constraints.gridheight = 9;
        constraints.gridy = 0;
        constraints.gridx = 0;
        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.fill = GridBagConstraints.BOTH;
        add(console, constraints);
    }

    private void addButtons() {
        int count = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 14; j++) {
                if (j > 8 && j % 2 == 0) {
                    if (i < 7 && i % 2 == 1)
                        addComponent(buttons[count++], j, i);
                    else if (i >= 7 && i % 2 == 0)
                        addComponent(buttons[count++], j, i);
                } else if (i >= 7 && j % 2 == 1 && j < 8 && i % 2 == 0)
                    addComponent(buttons[count++], j, i);
                if (i == 9) {
                    if (j <= 8 && j % 2 == 0)
                        addVSpacer(j, i);
                    else if (j > 8 && j % 2 == 1)
                        addVSpacer(j, i);
                }
                if (j == 13) {
                    if (i < 7 && i % 2 == 0)
                        addHSpacer(j, i);
                    else if (i >= 7 && i % 2 == 1)
                        addHSpacer(j, i);
                }
            }
        }
    }

    private void addVSpacer(int row, int column) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = column;
        constraints.gridy = row;
        constraints.weighty = 0.1;
        add(MyFrame.getSpacer(), constraints);
    }

    private void addHSpacer(int row, int column) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = column;
        constraints.gridy = row;
        constraints.weightx = 0.1;
        add(MyFrame.getSpacer(), constraints);
    }

    private void addComponent(Component c, int row, int column) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = column;
        constraints.gridy = row;
        add(c, constraints);
    }
}