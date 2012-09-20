package zsmith.capstone.whileimout;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Task;
import utilities.FilePaths;
import utilities.JSONSerializer;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.actionbarsherlock.app.SherlockFragmentActivity;

public class TaskListActivity extends SherlockFragmentActivity implements
		TaskListFragment.Callbacks {

	private boolean mTwoPane;
	private List<Task> mTasks;
	private File mFile;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_list);

		if (findViewById(R.id.task_detail_container) != null) {
			mTwoPane = true;
			((TaskListFragment) getSupportFragmentManager().findFragmentById(
					R.id.task_list)).setActivateOnItemClick(true);

			File f = new File(getExternalFilesDir(null),
					FilePaths.TASKS.toString());
			try {
				if (!f.exists())
					f.createNewFile();
			} catch (Exception e) {
			}
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		try {
			if (BuildConfig.DEBUG)
				Log.d("File", mFile.getAbsolutePath());
			mTasks = JSONSerializer.deserializeFromJSON(mFile.getAbsolutePath(), Task.class);
			List<Map<String, String>> itemLists = new ArrayList<Map<String, String>>();
			for (Task t : mTasks) {
				Map<String, String> datum = new HashMap<String, String>();
				datum.put("main", t.getName());
				datum.put("sub", t.getDescription());
				itemLists.add(datum);
			}
			SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), itemLists, android.R.layout.simple_list_item_2, new String[]{"main", "sub"}, new int[]{android.R.id.text1, android.R.id.text2});
			ListView view = (ListView)findViewById(android.R.id.list);
			view.setAdapter(adapter);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onItemSelected(String id) {
		if (mTwoPane) {
			Bundle arguments = new Bundle();
			arguments.putString(TaskDetailFragment.ARG_ITEM_ID, id);
			TaskDetailFragment fragment = new TaskDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.task_detail_container, fragment).commit();

		} else {
			Intent detailIntent = new Intent(this, TaskDetailActivity.class);
			detailIntent.putExtra(TaskDetailFragment.ARG_ITEM_ID, id);
			startActivity(detailIntent);
		}
	}
}
