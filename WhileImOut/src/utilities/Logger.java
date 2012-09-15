package utilities;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class Logger {

	private static FileHandler logger;

	public static void Log(String fileName, String message, Level level)
			throws IOException {
		logger = new FileHandler(fileName, true);

		logger.publish(new LogRecord(level, message));
		logger.close();
	}

}
