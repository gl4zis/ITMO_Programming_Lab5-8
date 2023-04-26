package general;

/**
 * Class, determining OS type
 */
public abstract class OsUtilus {

    /**
     * Returns name of OS
     */
    private static String getOsName() {
        return System.getProperty("os.name");
    }

    /**
     * True if app has started on Windows
     */
    public static boolean IsWindows() {
        return getOsName().startsWith("Windows");
    }
}
