
/*
 * This class represents the basic type EMPLOYEE
 * Written by: Qian Xu
 * Date: April 20, 2015
 */
	public class Employee {
	private String eID;

	public Employee(String eID) {
		this.eID = eID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((eID == null) ? 0 : eID.hashCode());
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
		Employee other = (Employee) obj;
		if (eID == null) {
			if (other.eID != null)
				return false;
		} else if (!eID.equals(other.eID))
			return false;
		return true;
	}

	public int compareTo(Employee emp) {
		return eID.compareTo(emp.eID);
	}

	public String toString() {
		return eID;
	}
	/*
	 * public String geteID() { return eID; }
	 * 
	 * public void seteID(String eID) { this.eID = eID; }
	 */
}
