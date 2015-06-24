
/*
 * This class represents the the prototype of employeeProjectPair specification
 * Written by: Qian Xu
 * Date: April 20, 2015
 */
public class EmployeeProjectPair {
	private Employee employee;
	private Project project;
	private int hoursPent;

	public EmployeeProjectPair(Employee e, Project p, int h) {
		this.employee = e;
		this.project = p;
		this.hoursPent = h;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public int getHoursPent() {
		return hoursPent;
	}

	public void setHoursPent(int hoursPent) {
		this.hoursPent = hoursPent;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((employee == null) ? 0 : employee.hashCode());
		result = prime * result + ((project == null) ? 0 : project.hashCode());
		return result;
	}

	/*
	 * the method of equal has kept consistency of for all employeeHours, they
	 * equals to each other if and only if their projects are the same and
	 * employees are the same
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmployeeProjectPair other = (EmployeeProjectPair) obj;
		if (employee == null) {
			if (other.employee != null)
				return false;
		} else if (!employee.equals(other.employee))
			return false;
		if (project == null) {
			if (other.project != null)
				return false;
		} else if (!project.equals(other.project))
			return false;
		return true;
	}

}
