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

	public static <T> void serializeToJSON(String filePath, List<T> toSerialize)
			throws IOException {

		gson = new Gson();

		File file = new File(filePath);
		if (!file.exists())
			file.createNewFile();

		FileWriter writer = new FileWriter(file);
		BufferedWriter out = new BufferedWriter(writer);
		for (T obj : toSerialize) {
			String json = gson.toJson(obj);
			out.write(json);
		}
	}

	public static <T> List<T> deserializeFromJSON(String filePath, Class<T> classType)
			throws IOException {

		gson = new Gson();
		File file = new File(filePath);
		ArrayList<T> objs = new ArrayList<T>();
		if (file.exists()) {
			FileReader reader = new FileReader(file);
			BufferedReader in = new BufferedReader(reader);
			String line;
			while ((line = in.readLine()) != null) {
				objs.add(gson.fromJson(line, classType));
			}
		}
		return objs;
	}
}
