package zsmith.capstone.whileimout;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utilities.FilePaths;
import utilities.JSONSerializer;

import models.Location;
import models.Task;
import android.R;
import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleAdapter;

public class HomeActivity extends ListActivity {

	String taskPath;
	List<Task> tasks;

	@Override
	protected void onStart() {
		super.onStart();
		taskPath = FilePaths.TASKS.toString();
		File f = new File(getExternalFilesDir(null), taskPath);
		try {
			if (!f.exists())
				f.createNewFile();
			tasks = JSONSerializer.deserializeFromJSON(f.getAbsolutePath(), Task.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Log.e("Tasks Count", tasks.size() + "");
		final String[] taskNames = new String[tasks.size()];
		ArrayList<Map<String, String>> listItems = new ArrayList<Map<String, String>>();
		for (Task t : tasks) {
			Map<String, String> datum = new HashMap<String, String>(
					tasks.size());
			datum.put("main", t.getName());
			datum.put("sub", t.getDescription());
			listItems.add(datum);
		}
		for (int i = 0; i < tasks.size(); i++)
			taskNames[i] = tasks.get(i).getName();
		SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),
				listItems, R.layout.simple_list_item_2, new String[] { "main",
						"sub" }, new int[] { android.R.id.text1,
						android.R.id.text2 });

		setListAdapter(adapter);

		getListView().setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				String content = taskNames[position];
				Intent showContent = new Intent(getApplicationContext(),
						TutViewerActivity.class);
				showContent.setData(Uri.parse(content));
				startActivity(showContent);
			}
		});
	}

	/*
	 * // public void onCreate(Bundle savedInstanceState){ //
	 * super.onCreate(savedInstanceState); ////
	 * setListAdapter(ArrayAdapter.createFromResource(getApplicationContext(),
	 * //// R.array.tut_titles, R.layout.list_item )); //// final String[] links
	 * = getResources().getStringArray(R.array.tut_links); ////
	 * getListView().setOnItemClickListener(new OnItemClickListener(){ //////
	 * @Override //// public void onItemClick(AdapterView<?> parent, View view,
	 * int position, long id){ //// String content = links[position]; ////
	 * Intent showContent = new Intent(getApplicationContext(),
	 * TutViewerActivity.class); //// showContent.setData(Uri.parse(content));
	 * //// startActivity(showContent); //// } //// }); // }
	 */
}
