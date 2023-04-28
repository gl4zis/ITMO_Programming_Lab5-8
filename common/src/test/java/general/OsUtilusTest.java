package general;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OsUtilusTest {

    @Test
    @DisabledOnOs(OS.WINDOWS)
    void isWindowsFalse() {
        assertFalse(OsUtilus.IsWindows());
    }

    @Test
    @DisabledOnOs(OS.LINUX)
    void isWindowsTrue() {
        assertTrue(OsUtilus.IsWindows());
    }
}