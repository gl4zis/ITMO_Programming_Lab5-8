package GUI;

public enum PageStatus {
    AUTHORIZE(AuthorizePanel.class, 0),
    SIGN_IN(SignInPanel.class, 0),
    SIGN_UP(SignUpPanel.class, 0),
    HOME(HomePanel.class, 0),
    CHANGE_PASSW(ChangePasswordPanel.class, 0),
    VIEW(ViewPanel.class, 1),
    TABLE(TablePanel.class, 2),
    COMMAND(CommandsPanel.class, 3);

    private final Class<? extends BasePanel> panelClass;
    private final int buttonIndex;

    PageStatus(Class<? extends BasePanel> panelClass, int buttonIndex) {
        this.panelClass = panelClass;
        this.buttonIndex = buttonIndex;
    }

    public Class<? extends BasePanel> getPanelClass() {
        return panelClass;
    }

    public int getButtonIndex() {
        return buttonIndex;
    }
}
