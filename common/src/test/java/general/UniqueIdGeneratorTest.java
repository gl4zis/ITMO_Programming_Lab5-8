package general;

import exceptions.IncorrectInputException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UniqueIdGeneratorTest {

    @Test
    void getIntId() throws InterruptedException {
        int id = UniqueIdGenerator.getIntId();
        Thread.sleep(10);
        assertNotEquals(id, UniqueIdGenerator.getIntId());
    }
}