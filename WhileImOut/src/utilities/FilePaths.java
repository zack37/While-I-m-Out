package utilities;

public enum FilePaths {
	TASKS("./tasks.txt"), ERROR("./error.log"), LOG("./activitylog.log");
	
	private String fileName;
	
	private FilePaths(String fileName) {
		this.fileName = fileName;
	}
	
	@Override
	public String toString() {
		return fileName;
	}
}
