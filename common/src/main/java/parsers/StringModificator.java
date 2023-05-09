package parsers;

import general.OsUtilus;

/**
 * Modificator for strings in correct form
 */
public abstract class StringModificator {
    /**
     * Formatting file paths to processing ~ and different slashes (\, /)
     */
    public static String filePathFormat(String filePath) {
        if (filePath.length() > 0) {
            if (filePath.charAt(0) == '~')
                filePath = getHome() + filePath.substring(1);
            filePath = filePath.replaceAll("\\\\", "/");
        }
        return filePath;
    }

    /**
     * Parse homepath, depending on OS
     *
     * @return homepath
     */
    private static String getHome() {
        if (OsUtilus.IsWindows()) {
            String homeDrive = System.getenv("HOMEDRIVE");
            String homePath = System.getenv("HOMEPATH");
            return homeDrive + homePath;
        } else return System.getenv("HOME");
    }
}
