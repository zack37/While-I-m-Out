package zsmith.capstone.whileimout;

import zsmith.capstone.whileimout.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class TaskCreateActivity extends SherlockFragmentActivity {

	private final int DEFAULT_VALUE = -1;
	TaskCreateFragment fragment;
	boolean isCreating;
	int position;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_create_edit);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		if (savedInstanceState == null) {
			Bundle arguments = new Bundle();
			isCreating = (position = getIntent().getIntExtra(
					TaskDetailFragment.ARG_ITEM_ID, DEFAULT_VALUE)) == DEFAULT_VALUE;
			fragment = new TaskCreateFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.add(R.id.task_create_container, fragment).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getSupportMenuInflater().inflate(R.menu.edit_action_bar, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpTo(this,
					new Intent(this, TaskListActivity.class));
			break;
		case R.id.cancel_action_bar_button:
			Log.i("TaskCreateActivity",
					"User has chosen to cancel creating a task");
			fragment.cancelCreate();
			break;
		case R.id.accept_action_bar_button:
			if (isCreating) {
				Log.i("TaskCreateActivity", "User has created a task");
				fragment.createNewTask();
			}
			else{
				Log.i("TaskCreateActivity", "User is editing a task");
				fragment.editTask();
			}

			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}

	public int getPosition() {
		return position;
	}

	public boolean isCreating() {
		return isCreating;
	}
}
