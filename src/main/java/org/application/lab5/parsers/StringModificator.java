package org.application.lab5.parsers;

/**
 * Modificator for strings in correct form
 */
public abstract class StringModificator {
    /**
     * Formatting file paths to processing ~ and different slashes (\, /)
     *
     * @return formatted filePath
     */
    public static String filePathFormat(String filePath) {
        if (filePath.length() > 0) {
            if (filePath.charAt(0) == '~')
                filePath = System.getenv("HOME") + filePath.substring(1);
            filePath = filePath.replaceAll("\\\\", "/");
        }
        return filePath;
    }
}
