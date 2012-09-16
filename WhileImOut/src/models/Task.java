package models;

public class Task {

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result
				+ ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (proximityMode ? 1231 : 1237);
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Task))
			return false;
		Task other = (Task) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
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

	private String name;
	private Location location;
	private boolean proximityMode;
	private String description;

	public Task() {
	}

	public Task(String name, Location location, boolean proximityMode){
		this.name = name;
		this.location = location;
		this.proximityMode = proximityMode;
		this.description = "";
	}
	
	public Task(String name, Location location, boolean proximityMode,
			String description) {
		this.name = name;
		this.location = location;
		this.proximityMode = proximityMode;
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

}