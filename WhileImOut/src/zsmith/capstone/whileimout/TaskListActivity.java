package zsmith.capstone.whileimout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import zsmith.capstone.whileimout.R;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.whileimout.models.Location;
import com.whileimout.models.Task;
import com.whileimout.utilities.DeletePrompt;
import com.whileimout.utilities.FilePaths;
import com.whileimout.utilities.JSONSerializer;

public class TaskListActivity extends SherlockFragmentActivity implements
		TaskListFragment.Callbacks {

	private boolean mTwoPane;
	private List<Task> mTasks;
	private DeletePrompt deletePrompt;
	TaskListFragment fragment;
	ListAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_list);

		deletePrompt = new DeletePrompt(this, new Intent(this,
				TaskListActivity.class));

		// createDummyData();
		if (findViewById(R.id.task_detail_container) != null) {
			mTwoPane = true;
		}
		fragment = (TaskListFragment) getSupportFragmentManager()
				.findFragmentById(R.id.task_list);
		fragment.setActivateOnItemClick(true);
		adapter = (ListAdapter) fragment.getListAdapter();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getSupportMenuInflater().inflate(R.menu.home_action_bar, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.add_new_task_action_bar_button:
			Intent intent = new Intent(this, TaskCreateActivity.class);
			startActivity(intent);
			break;
		case R.id.preferences_action_bar_button:
			startActivity(new Intent(this, Preferences.class));
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);

		android.view.MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.home_floating_context, menu);
	}

	@Override
	public boolean onContextItemSelected(android.view.MenuItem item) {
		android.widget.AdapterView.AdapterContextMenuInfo infor = (android.widget.AdapterView.AdapterContextMenuInfo) item
				.getMenuInfo();
		switch (item.getItemId()) {
		case R.id.edit_task_menu_item:
			startActivity(setClickedItemIntent(TaskCreateActivity.class,
					(int) infor.id));
			finish();
			break;

		case R.id.delete_task_menu_item:
			deletePrompt.deleteTask((int) infor.id);
			break;

		default:
			return super.onContextItemSelected(item);
		}
		return true;
	}

	@Override
	public void onBackPressed() {
		new AlertDialog.Builder(this).setTitle("Exit Notification")
				.setMessage("While I'm Out will remain open in the background")
				.setIcon(R.drawable.ic_action_cancel)
				.setPositiveButton("OK", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						TaskListActivity.this.finish();						
						TaskListActivity.super.onBackPressed();
						TaskListActivity.super.finish();
						moveTaskToBack(true);
					}
				}).create().show();
	}

	@SuppressWarnings("unused")
	private void createDummyData() {
		Location l = new Location("1861 West 7265 South", "West Jordan",
				"Utah", "84084");
		List<Task> dummyTasks = new ArrayList<Task>();
		for (int i = 0; i < 20; i++)
			dummyTasks.add(new Task("Dummy Task: " + i, l, i % 2 == 0,
					"Dummy Data Description: " + i));

		JSONSerializer.serializeToJSON(getExternalFilesDir(null) + "/"
				+ FilePaths.TASKS.toString(), dummyTasks);
	}

	@Override
	protected void onResume() {
		super.onResume();

		mTasks = WhileImOut.getTaskManager().getTasks();
		List<Map<String, String>> itemLists = new ArrayList<Map<String, String>>();
		fillItemLists(itemLists);
		SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),
				itemLists, android.R.layout.simple_list_item_2, new String[] {
						"main", "sub" }, new int[] { android.R.id.text1,
						android.R.id.text2 });
		ListView view = (ListView) findViewById(android.R.id.list);
		view.setAdapter(adapter);
		registerForContextMenu(view);
	}

	private final int Title_Count = 28;
	private final int Description_Count = 80;
	
	private void fillItemLists(List<Map<String, String>> itemLists) {
		if (mTasks != null)
			for (Task t : mTasks) {
				Map<String, String> datum = new HashMap<String, String>();
				String name = t.getName();
				String desc = t.getDescription();
				datum.put("main", breakUpString(name, Title_Count));
				datum.put("sub",
						desc.contains("\n") ? breakUpString(desc, '\n')
								: breakUpString(desc, Description_Count));
				itemLists.add(datum);
			}
	}

	@Override
	public void onItemSelected(int location) {
		if (mTwoPane) {
			Bundle arguments = new Bundle();
			arguments.putInt(TaskDetailFragment.ARG_ITEM_ID, location);
			TaskDetailFragment fragment = new TaskDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.task_detail_container, fragment).commit();

		} else {
			startActivity(setClickedItemIntent(TaskDetailActivity.class,
					location));
		}
	}

	public <T> Intent setClickedItemIntent(Class<T> activity, int position) {
		Intent intent = new Intent(this, activity);
		intent.putExtra(TaskDetailFragment.ARG_ITEM_ID, position);
		return intent;
	}

	private String breakUpString(String s, int length) {
		return s.length() > length ? s.substring(0, length - 3) + "..." : s;
	}

	private String breakUpString(String s, char breakChar) {
		return s.substring(0, s.indexOf(breakChar + "")) + "...";
	}
}
