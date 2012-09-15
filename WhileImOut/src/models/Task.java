package models;

public class Task {

	private String name;
	private Location location;
	private boolean isProximityMode;
	private String description;

	public Task() {
	}

	public Task(String name, Location location, boolean proximityMode,
			String description) {
		this.name = name;
		this.location = location;
		isProximityMode = proximityMode;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public boolean isProximityMode() {
		return isProximityMode;
	}

	public void setProximityMode(boolean isProximityMode) {
		this.isProximityMode = isProximityMode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}