package utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

public class JSONSerializer {

	private static Gson gson;

	public static <T> boolean serializeToJSON(String fileName,
			List<T> toSerialize) {

		gson = new Gson();

		File file = new File(fileName);
		try {
			if (!file.exists())
				file.createNewFile();

			FileWriter writer = new FileWriter(file);
			BufferedWriter out = new BufferedWriter(writer);
			for (T obj : toSerialize) {
				String json = gson.toJson(obj);				
				out.write(json);
				out.newLine();
			}
			out.flush();
			out.close();

			return true;
		} catch (IOException e) {
			Logger.log(FilePaths.ERROR.toString(),
					"Error serializing object to JSON");
		}
		return false;
	}

	public static <T> void testMethod() {
		return;
	}

	public static <T> List<T> deserializeFromJSON(String fileName,
			Class<T> classType) throws IOException {

		gson = new Gson();
		File file = new File(fileName);
		ArrayList<T> objs = new ArrayList<T>();
		if (file.exists()) {
			FileReader reader = new FileReader(file);
			BufferedReader in = new BufferedReader(reader);
			String line;
			while ((line = in.readLine()) != null) {
				objs.add(gson.fromJson(line, classType));
			}
			in.close();
		}
		return objs;
	}
}
