package utilities;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {

	public static boolean log(String fileName, String message) {
		try {

			FileWriter out = new FileWriter(fileName, true);
			BufferedWriter writer = new BufferedWriter(out);

			writer.write(message);
			writer.newLine();
			writer.flush();
			writer.close();
			return true;

		} catch (IOException e) {
			try {
				BufferedWriter error = new BufferedWriter(new FileWriter(
						"./error.log", true));
				Messages errorMessage = Messages.ERROR;
				errorMessage.setMessage("Error: " + e.toString());
				error.write(errorMessage.toString());
				error.newLine();
				error.flush();
				error.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return false;

	}

}
