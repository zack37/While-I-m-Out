package com.whileimout.models;

import java.util.UUID;

public class Task {

	private UUID id;
	private String name;
	private Location location;
	private boolean proximityMode;
	private String description;
	private boolean isActive;

	public Task() {
	}

	public Task(String name, Location location, boolean proximityMode,
			String description) {
		this.id = createId();
		this.name = name;
		this.location = location;
		this.proximityMode = proximityMode;
		this.description = description;
		this.isActive = true;
	}

	private UUID createId() {
		return UUID.randomUUID();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (isActive ? 1231 : 1237);
		result = prime * result
				+ ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (proximityMode ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Task other = (Task) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isActive != other.isActive)
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (proximityMode != other.proximityMode)
			return false;
		return true;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
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
		return proximityMode;
	}

	public void setProximityMode(boolean isProximityMode) {
		this.proximityMode = isProximityMode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	public void toggleIsActive() {
		isActive = !isActive;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Task [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", location=");
		builder.append(location);
		builder.append(", proximityMode=");
		builder.append(proximityMode);
		builder.append(", description=");
		builder.append(description);
		builder.append(", isActive=");
		builder.append(isActive);
		builder.append("]");
		return builder.toString();
	}

}