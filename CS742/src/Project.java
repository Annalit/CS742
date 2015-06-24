/*
 * This class represents the basic type PROJECT
 * Written by: Qian Xu
 * Date: April 20, 2015
 */
public class Project {
	private String pID;
	public Project(String pID){
		this.pID = pID; 
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pID == null) ? 0 : pID.hashCode());
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
		Project other = (Project) obj;
		if (pID == null) {
			if (other.pID != null)
				return false;
		} else if (!pID.equals(other.pID))
			return false;
		return true;
	}
	public String toString(){
		return pID;
	}


}
