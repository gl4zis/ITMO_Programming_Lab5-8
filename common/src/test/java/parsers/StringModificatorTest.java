package parsers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StringModificatorTest {
    @Test
    @EnabledOnOs(OS.WINDOWS)
    void filePathFormat() {
        String path = "~\\someDir/anotherDir\\oneMoreDir/file.txt";
        String rightPath = "C:/Users/Roma/someDir/anotherDir/oneMoreDir/file.txt";
        assertEquals(rightPath, StringModificator.filePathFormat(path));
    }
}