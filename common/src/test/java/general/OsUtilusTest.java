package general;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import static org.junit.jupiter.api.Assertions.*;

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