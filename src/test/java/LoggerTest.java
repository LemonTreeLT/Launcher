import com.lemontree.launcher.utils.LLogger;
import org.junit.jupiter.api.Test;

public class LoggerTest {

    @Test
    public void loggerTest() {
        LLogger logger = new LLogger(this.getClass());

        logger.info("This is a info message");
        logger.warning("This is a warning message");
        logger.severe("This is an error message");

    }
}
