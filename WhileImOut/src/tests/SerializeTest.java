package tests;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import models.Location;
import models.Task;
import utilities.FilePaths;
import utilities.JSONSerializer;

public class SerializeTest extends TestCase {

	String fileName;

	public SerializeTest() {
		fileName = FilePaths.TASKS.toString();
	}

	public void testSerializeToFile() {
		ArrayList<Task> tasks = new ArrayList<Task>();
		for (int i = 0; i < 100; i++) {
			Location l = new Location("1861 West 7265 South", "West Jordan",
					"Utah", 84084);
			Task t = new Task("Test Task", l, true);
			tasks.add(t);
		}
		assertTrue(tasks.size() == 100);
		assertTrue(JSONSerializer.serializeToJSON(fileName, tasks));
		String pattern = "{\"name\":\"Test Task\",\"location\":{\"streetAddress\":\"1861 West 7265 South\",\"city\":\"West Jordan\",\"state\":\"Utah\",\"zipCode\":84084},\"proximityMode\":true,\"description\":\"\"}";
		System.out.println(pattern);
		assertTrue(checkFileContents(pattern));
	}

	private boolean checkFileContents(String match) {
		try {
			FileReader in = new FileReader(fileName);
			BufferedReader read = new BufferedReader(in);
			String line;
			while ((line = read.readLine()) != null) {
				if (!line.equals(match))
					return false;
			}

			in.close();
			read.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return true;
	}

	public void testDeserialzeToObject() {
		try {
			List<Task> tasks = JSONSerializer.deserializeFromJSON(fileName, Task.class);
			System.out.println(tasks.size());
			Location l = new Location("1861 West 7265 South", "West Jordan",
					"Utah", 84084);
			Task t = new Task("Test Task", l, true);
			for(Task task : tasks)
				assertTrue(task.equals(t));
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
