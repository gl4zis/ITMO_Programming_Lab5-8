package GUI;

import settings.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.Constructor;
import java.util.Objects;

public class MyFrame extends JFrame {

    private final Settings settings;
    private final LeftPanel leftPanel;
    private final Header header;
    private BasePanel base;

    public MyFrame(String title, Settings settings) {
        super(title);
        setIconImage(new ImageIcon(Objects.requireNonNull(
                getClass().getResource("/img/light/dragon.png"))).getImage());
        this.settings = settings;
        header = new Header(this);
        leftPanel = new LeftPanel(this);
        if (settings.getUser() == null)
            setStatus(PageStatus.AUTHORIZE);
        else
            setStatus(PageStatus.HOME);
        setLayout(new BorderLayout());
        add(header, BorderLayout.NORTH);
        add(leftPanel, BorderLayout.WEST);
        add(base);

        pack();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        Dimension windowSize = getSize();
        setLocation(screenSize.width / 2 - windowSize.width / 2, screenSize.height / 2 - windowSize.height / 2);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setMinimumSize(new Dimension(windowSize.width + 80, windowSize.height + 45));
        setPreferredSize(new Dimension(800, 450));
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                settings.save();
            }
        });
    }

    public static JPanel getSpacer() {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
            }
        };
    }

    public void checkConnect() {
        header.repaint();
    }

    public void setStatus(PageStatus status) {
        if (base != null) remove(base);
        try {
            Constructor<? extends BasePanel> cons = status.getPanelClass().getDeclaredConstructor(MyFrame.class);
            base = cons.newInstance(this);
            add(base, BorderLayout.CENTER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        leftPanel.resetButtons();
        leftPanel.setButton(status.getButtonIndex());
    }

    public double getKf() {
        return Math.min((double) getWidth() / (double) getPreferredSize().width,
                (double) getHeight() / (double) getPreferredSize().height);
    }

    public Settings getSettings() {
        return settings;
    }
}
