package tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import junit.framework.TestCase;
import utilities.Logger;
import utilities.Messages;

public class LoggingTest extends TestCase {

	private String fileName;

	public LoggingTest(String name) {
		super(name);
		fileName = "./activitylog.log";
	}

	public void testLoggingCapability() {
		new File(fileName).delete();
		Random rnd = new Random();
		Messages[] msgs = Messages.values();
		for (int i = 0; i < 100; i++)
			assertTrue(Logger.log(fileName,
					msgs[rnd.nextInt(msgs.length)].toString()));
		int count = 0;
		try {
			FileReader reader = new FileReader(fileName);
			BufferedReader in = new BufferedReader(reader);
			while (in.readLine() != null) {
				count++;
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue(count == 100);
	}

	public void testLoggingErrors() {
		new File("./errors.log").delete();

		// No file path
		assertFalse(Logger.log("", "This should fail due to no path specified"));

		// Invalid file format
		assertFalse(Logger.log("///",
				"This should fail due to invalid file format"));
	}

}
