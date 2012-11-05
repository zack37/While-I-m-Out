package com.whileimout.utilities;

import java.util.UUID;

import zsmith.capstone.whileimout.WhileImOut;

import com.whileimout.managers.TaskManager;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.util.Log;

public class DeletePrompt {

	AlertDialog.Builder alert;
	Intent intent;
	Activity context;

	public DeletePrompt(Activity context, Intent intent) {
		this.intent = intent;
		this.context = context;
		alert = new AlertDialog.Builder(context);
		alert.setTitle("Confirm delete");
		alert.setMessage("Are you sure you want to delete?");
	}

	public void deleteTask(final int position) {
		alert.setPositiveButton("Yes", new PositiveDeletePrompt(position));
		alert.setNegativeButton("Cancel", new NegativeDeletePrompt());
		alert.show();
	}

	public void deleteTask(final UUID taskId, Intent intent) {
		alert.setPositiveButton("Yes", new PositiveDeletePrompt(taskId));
		alert.setNegativeButton("Cancel", new NegativeDeletePrompt());
		alert.show();
	}

	private class PositiveDeletePrompt implements OnClickListener {

		Integer position;
		UUID id;

		public PositiveDeletePrompt(int position) {
			this.position = position;
			id = null;
		}

		public PositiveDeletePrompt(UUID id) {
			this.id = id;
			position = null;
		}

		@Override
		public void onClick(DialogInterface dialog, int which) {

			if (position != null) { // If user deletes from the home view
				Log.i("User deletes task",
						"User deletes task located at position: " + position);
				WhileImOut.getTaskManager().deleteTask(position);
			} else { // If user deletes from details view
				Log.i("User deletes task", "User deletes task with id of: "
						+ id);
				TaskManager tm = WhileImOut.getTaskManager();
				tm.deleteTask(tm.findTaskById(id));
			}
			context.startActivity(intent);
			context.finish();
		}
	}

	private class NegativeDeletePrompt implements OnClickListener {

		public NegativeDeletePrompt() {
		}

		@Override
		public void onClick(DialogInterface dialog, int which) {
			Log.d("Prompt", "User clicked cancel");
		}

	}

}
