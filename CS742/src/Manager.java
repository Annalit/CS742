/*
 * This class represents the basic type MANAGER
 * Written by: Qian Xu
 * Date: April 20, 2015
 */
public class Manager {
	private String mID;
	public Manager(String mID){
		this.mID = mID;
	}
	public String getMid() {
		return mID;
	}

	public void setMid(String mID) {
		this.mID = mID;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mID == null) ? 0 : mID.hashCode());
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
		Manager other = (Manager) obj;
		if (mID == null) {
			if (other.mID != null)
				return false;
		} else if (!mID.equals(other.mID))
			return false;
		return true;
	}
	public int compareTo(Manager m){
		return mID.compareTo(m.mID);
	}
	public String toString(){
		return mID;
	}
	
}
