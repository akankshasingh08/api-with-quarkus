package dto;

public class AverageMarksDTO {
	private String name;
	private double averageMarks;
	private String passStatus;

	public AverageMarksDTO(String name, double averageMarks, String passStatus) {
		this.name = name;
		this.averageMarks = averageMarks;
		this.passStatus = passStatus;
	}

	public AverageMarksDTO() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getAverageMarks() {
		return averageMarks;
	}

	public void setAverageMarks(double averageMarks) {
		this.averageMarks = averageMarks;
	}

	public String isPassStatus() {
		return passStatus;
	}

	public void setPassStatus(String passStatus) {
		this.passStatus = passStatus;
	}
}
