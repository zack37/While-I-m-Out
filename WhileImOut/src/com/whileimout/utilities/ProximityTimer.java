package com.whileimout.utilities;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


import zsmith.capstone.whileimout.TaskDetailActivity;
import zsmith.capstone.whileimout.TaskDetailFragment;
import zsmith.capstone.whileimout.TaskListActivity;
import zsmith.capstone.whileimout.WhileImOut;

import com.whileimout.managers.LocationProximityManager;
import com.whileimout.managers.NotificationAlertManager;
import com.whileimout.models.Task;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;

public class ProximityTimer {

	private Timer checkProximityTimer;
	private TimerTask timerTask;
	private double distance;
	private Context context;
	private int delay;

	public ProximityTimer(Context context, int delayInSeconds, double distance) {
		this.checkProximityTimer = new Timer();
		this.timerTask = getTimerTask();
		this.delay = delayInSeconds;
		this.distance = distance;
		this.context = context;
	}

	public void startTimer() {
		checkProximityTimer.scheduleAtFixedRate(timerTask, delay, delay);
	}

	private TimerTask getTimerTask() {
		return new TimerTask() {
			public void run() {
				new Thread(new Runnable() {
					@Override
					public void run() {
						Looper.prepare();						
						LocationProximityManager locationSystem = new LocationProximityManager(
								context, distance);
						List<Task> tasksWithinRange = locationSystem
								.getTasksWithinRange();
						Looper.myLooper().quit();
						soundOffNotification(tasksWithinRange);
					}
				}).start();
			}
		};
	}

	private final int singleTaskSize = 1;
	private final int singleTaskLocation = 0;

	private void soundOffNotification(List<Task> tasksWithinRange) {
		if (!tasksWithinRange.isEmpty())
			if (tasksWithinRange.size() == singleTaskSize) {
				soundOffSingleTaskNotification(tasksWithinRange
						.get(singleTaskLocation));
			} else {
				soundOffMultiTaskNotification(tasksWithinRange.size());
			}
	}

	private void soundOffMultiTaskNotification(int size) {
		NotificationAlertManager.makeNotification("Tasks are within range",
				"There are " + size + " tasks within range of you right now",
				context, new Intent(context, TaskListActivity.class));
	}

	private void soundOffSingleTaskNotification(Task singleTask) {
		// Set intent to go to the details view when there is only 1
		// task within range
		Intent singleTaskIntent = new Intent(context, TaskDetailActivity.class);
		singleTaskIntent.putExtra(TaskDetailFragment.ARG_ITEM_ID, WhileImOut
				.getTaskManager().getTasks().indexOf(singleTask));
		NotificationAlertManager.makeNotification(singleTask.getName()
				+ " is within range", singleTask.getDescription(), context,
				singleTaskIntent);
	}

	public void changeInterval(int delay) {
		timerTask.cancel();
		timerTask = getTimerTask();
		this.delay = delay;
	}

	public void setDistance(double d) {
		distance = d;
	}

	public int getDelay() {
		return delay;
	}

	public double getDistance() {
		return distance;
	}

	public Timer getTimer() {
		return checkProximityTimer;
	}
}
