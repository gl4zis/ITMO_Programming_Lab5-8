package parsers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;

/**
 * Class, working with script file
 */
public abstract class ScriptParser {
    private static final Logger LOGGER = LogManager.getLogger(ScriptParser.class);

    /**
     * Convert script to the InputStream by its filepath
     *
     * @return InputStream
     * @throws FileNotFoundException if file is not exists
     * @throws SecurityException     if permission denied
     */
    public static InputStream readLines(String filePath) throws FileNotFoundException {
        File script = new File(filePath);
        FileInputStream in = new FileInputStream(script);
        InputStreamReader reader = new InputStreamReader(in);

        StringBuilder lines = new StringBuilder();
        try {
            int nextSym = reader.read();
            while (nextSym != -1) {
                lines.append((char) nextSym);
                nextSym = reader.read();
            }
        } catch (IOException e) {
            LOGGER.error("Something wrong with script file =(");
            return null;
        }
        String processedLines = lines.toString() + '\n';
        processedLines = processedLines.replaceAll("\r\n", "\n")
                .replaceAll("\s*//.*\n", "\n").replaceAll("\n\n", "\n");
        return new ByteArrayInputStream(processedLines.getBytes());
    }
}
