package zsmith.capstone.whileimout;

import zsmith.capstone.whileimout.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.whileimout.utilities.DeletePrompt;

public class TaskDetailActivity extends SherlockFragmentActivity {

	DeletePrompt deletePrompt;
	int position;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_detail);

		ActionBar actionbar = getSupportActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);

		deletePrompt = new DeletePrompt(this, new Intent(this,
				TaskListActivity.class));
		if (savedInstanceState == null) {
			Bundle arguments = new Bundle();
			position = getIntent().getIntExtra(TaskDetailFragment.ARG_ITEM_ID,
					0);
			arguments.putInt(TaskDetailFragment.ARG_ITEM_ID, getIntent()
					.getIntExtra(TaskDetailFragment.ARG_ITEM_ID, 0));
			TaskDetailFragment fragment = new TaskDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.add(R.id.task_detail_container, fragment).commit();
		}
	}

	@Override
	public boolean onOptionsItemSelected(
			com.actionbarsherlock.view.MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpTo(this,
					new Intent(this, TaskListActivity.class));
			break;
		case R.id.edit_action_bar_button:
			startActivity(setEditClickedIntent(TaskCreateActivity.class,
					position));
			finish();
			break;
		case R.id.delete_action_bar_button:
			deletePrompt.deleteTask(position);
			break;
		case R.id.details_preferences_action_bar_button:
			startActivity(new Intent(this, Preferences.class));
			finish();
			break;
		default:
			return super.onOptionsItemSelected(item);
		}

		return true;
	}

	public <T> Intent setEditClickedIntent(Class<T> activity, int position) {
		Intent intent = new Intent(this, activity);
		intent.putExtra(TaskDetailFragment.ARG_ITEM_ID, position);
		return intent;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getSupportMenuInflater().inflate(R.menu.details_action_bar,
				menu);
		return true;
	}
}
