import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * This program represents the prototype of the company specification. The basic
 * type EMPLOYEE,MANAGER,PROJECT DIVISION_NAME are implemented as classes
 * separately. See each class for their internal details.This program uses the built-in data type "HashSet" to implement
 * the set data type in the specification, and use HashMap to implement the
 * relation in the specification.
 * 
 * Author : Qian Xu Date : April 30, 2015
 */
public class Company {
	public final int NumberOfDivisions = 4;
	HashSet<Division> divisions;
	HashMap<Division, Manager> managers;

	/*
	 * This is the constuctor method that implements 'Init' state.
	 */
	public Company(HashSet<Division> divisionSet) {
		// initial divisions in the company.
		divisions = divisionSet;
		// manager set is empty initially
		managers = new HashMap<Division, Manager>();
		if (!stateInvariantCheck())
			System.out.print("\n Init state implemented by the "
					+ " constructor failed because the "
					+ " state invariant is violated \n");
	}

	/*
	 * This method implements the state invariant check.
	 * 
	 * @return boolean result indicating whether or not state invariant is
	 * satisfied.
	 */
	private boolean stateInvariantCheck() {
		/*
		 * this is for consistency of dom managers = divisions
		 */

		if (!divisions.containsAll(managers.keySet())) {
			return false;
		}
		/*
		 * this is for consistency of two divisions can't have the intersection
		 * of employees
		 */
		Iterator<Division> itrDivisions = divisions.iterator();
		HashSet<Employee> checkDuplicate = new HashSet<Employee>();
		Boolean flag = true;
		while (itrDivisions.hasNext()) {
			Division div = itrDivisions.next();
			if (!div.getEmployeeHours().isEmpty()) {
				flag = checkDuplicate.addAll((div.getEmployees()));
				if (!flag) {
					return false;
				}
			}
		}
		/*
		 * this is for consistency of number of divisions
		 */
		if (divisions.size() != NumberOfDivisions) {
			return false;
		}

		/*
		 * the equal and hashCode method in division has kept consistency for
		 * for all divisions, they equals to each other if and only if their
		 * names are the same
		 */
		return true;
	}

	public int extractEstimatedHours(HashSet<Division> d, Project p) {
		int result = 0;
		Iterator<Division> itr = d.iterator();
		while (itr.hasNext()) {
			Map<Project, Integer> estimate = itr.next().getEstimatedHours();
			Iterator<Entry<Project, Integer>> ditr = estimate.entrySet()
					.iterator();// map in a division
			while (ditr.hasNext()) {
				Entry<Project, Integer> t = ditr.next();// a specific map
				if (t.getKey().equals(p)) {
					int temp = t.getValue();
					result += temp;
				}
			}
		}
		return result;
	}

	/**
	 * This method implements the function 'ExtractEstimatedHoursForProject'.
	 * 
	 * @param project
	 *            the project that divisions are working on
	 * @param divisions
	 *            a set of divisions
	 */
	public int extractSpentHours(HashSet<Division> d, Project p) {
		int result = 0;
		Iterator<Division> itr = d.iterator();
		while (itr.hasNext()) {
			Map<Project, Integer> real = itr.next().getProjectHours();
			Iterator<Entry<Project, Integer>> ditr = real.entrySet().iterator();
			while (ditr.hasNext()) {
				Entry<Project, Integer> t = ditr.next();// a specific map
				if (t.getKey().equals(p)) {
					int temp = t.getValue();
					result += temp;
				}
			}
		}
		return result;
	}

	/**
	 * This method implements the operation 'IsDivisionOperational'.
	 * 
	 * @param managers
	 *            the map from division to manager
	 * @param division
	 *            the division that needs to be checked
	 */
	public Boolean isDivisionOperational(Division division) throws Exception {
		Iterator<Entry<Division, Manager>> itr = managers.entrySet().iterator();
		while (itr.hasNext()) {
			Entry<Division, Manager> temp = itr.next();
			if (temp.getKey().equals(division)
					&& (!division.getEmployees().isEmpty())) {
				return true;
			}
		}
		throw new PreconditionException("Company", "isDivisionOperational",
				"the division is not operational");
	}

	public void hireManager(Manager m, DivisionName dID) throws Exception {
		//
		// The state invariant must be checked before the operation.
		//
		if (!stateInvariantCheck())
			throw new InvariantException("Campany", "hireManager", "before");
		if (m.toString().equals("")) {
			throw new PreconditionException("Company", "hireManager",
					"the manager can't be empty");
		}
		if (dID.toString().equals("")) {
			throw new PreconditionException("Company", "hireManager",
					"the division can't be empty");
		}
		if (!divisions.contains(getDivision(dID))) {
			throw new PreconditionException("Company", "hireManager",
					"the Division doesn't exist");
		}

		if (managers.containsKey(getDivision(dID))) {
			throw new PreconditionException("Company", "hireManager",
					"the division aleady has a manager");
		}
		if (managers.containsValue(m)) {
			throw new PreconditionException("Company", "hireManager",
					"the manager to hire is manager of other divisions");
		}
		managers.put(getDivision(dID), m);
		//
		// The state invariant must be checked after the operation as well.
		//
		if (!stateInvariantCheck())
			throw new InvariantException("Company", "hireManager", "after");
	}

	public void fireManager(DivisionName dID) throws Exception {
		//
		// The state invariant must be checked before the operation.
		//

		Boolean flag = false;
		if (!stateInvariantCheck()) {
			throw new InvariantException("Campany", "fireManager", "before");
		}
		if (dID.toString().equals("")) {
			throw new PreconditionException("Company", "fireManager",
					"the division can't be empty");
		}
		if (!divisions.contains(getDivision(dID))) {
			throw new PreconditionException("Company", "moveManager",
					"the Division doesn't exist");
		}
		Iterator<Entry<Division, Manager>> itr = managers.entrySet().iterator();
		while (itr.hasNext()) {
			Division div = itr.next().getKey();
			if (div.getName().equals(dID)) {
				managers.remove(div);
				flag = true;
				break;
			}

		}
		if (flag == false)

			throw new PreconditionException("Company", "fireManager",
					"the division to fire manager doesn't exist");
		//
		// The state invariant must be checked after the operation as well.
		//
		if (!stateInvariantCheck())
			throw new InvariantException("Company", "fireManager", "after");
	}

	public void moveManager(DivisionName from, DivisionName to)
			throws Exception {
		//
		// The state invariant must be checked before the operation.
		//
		if (!stateInvariantCheck())
			throw new InvariantException("Campany", "moveManager", "before");
		if (from.toString().equals("")) {
			throw new PreconditionException("Company", "moveManager",
					"the fromDivision can't be empty");
		}
		if (to.toString().equals("")) {
			throw new PreconditionException("Company", "moveManager",
					"the toDivision can't be empty");
		}
		if (!divisions.contains(getDivision(from))) {
			throw new PreconditionException("Company", "moveManager",
					"the fromDivision doesn't exist");
		}
		if (!divisions.contains(getDivision(to))) {
			throw new PreconditionException("Company", "moveManager",
					"the toDivision doesn't exist");
		}

		if (!managers.containsKey(getDivision(from))) {
			throw new PreconditionException("Company", "moveManager",
					"the fromDivision doen't have a manager");
		} else if (managers.containsKey(getDivision(to))) {
			throw new PreconditionException("Company", "moveManager",
					"the toDivision already has a manager");
		}
		Iterator<Entry<Division, Manager>> itr = managers.entrySet().iterator();
		while (itr.hasNext()) {
			Entry<Division, Manager> entry = itr.next();
			if (entry.getKey().equals(getDivision(from))) {
				Manager temp = entry.getValue();
				managers.remove(getDivision(from));
				managers.put(getDivision(to), temp);
				break;
			}
		}
		//
		// The state invariant must be checked after the operation as well.
		//
		if (!stateInvariantCheck())
			throw new InvariantException("Company", "moveManager", "after");
	}

	public void hireEmployee(DivisionName dID, Employee newEmployee)
			throws Exception {
		//
		// The state invariant must be checked before the operation.
		//
		if (!stateInvariantCheck())
			throw new InvariantException("Company", "hireEmployee", "before");
		if (dID.toString().equals("")) {
			throw new PreconditionException("Company", "hireEmployee",
					"the Division can't be empty");
		}
		if (newEmployee.toString().equals("")) {
			throw new PreconditionException("Company", "hireEmployee",
					"the Employee can't be empty");
		}
		if (!divisions.contains(getDivision(dID))) {
			throw new PreconditionException("Company", "hireEmployee",
					"the division doesn't exist");
		}
		Iterator<Division> itr = divisions.iterator();
		while (itr.hasNext()) {
			Division temp = itr.next();
			if (temp.getEmployees().contains(newEmployee))
				throw new PreconditionException("Company", "hireEmployee",
						"the employee belongs to a certain division");
		}
		getDivision(dID).addEmployee(newEmployee);
		//
		// The state invariant must be checked after the operation as well.
		//
		if (!stateInvariantCheck())
			throw new InvariantException("Company", "hireEmployee", "after");
	}

	public void fireEmployee(DivisionName dID, Employee employee)
			throws Exception {
		//
		// The state invariant must be checked before the operation.
		//
		if (!stateInvariantCheck())
			throw new InvariantException("Campany", "fireEmployee", "before");
		if (dID.toString().equals("")) {
			throw new PreconditionException("Company", "fireEmployee",
					"the Division can't be empty");
		}
		if (employee.toString().equals("")) {
			throw new PreconditionException("Company", "fireEmployee",
					"the Employee can't be empty");
		}
		if (!divisions.contains(getDivision(dID))) {
			throw new PreconditionException("Company", "fireEmployee",
					"the division doesn't exist");
		}
		getDivision(dID).removeEmployee(employee);
		//
		// The state invariant must be checked after the operation as well.
		//
		if (!stateInvariantCheck())
			throw new InvariantException("Company", "fireEmployee", "after");
	}

	// HashSet<Division> divisions = new HashSet<Division>();
	public void moveEmployee(DivisionName from, DivisionName to,
			Employee employee) throws Exception {
		//
		// The state invariant must be checked before the operation.
		//
		if (!stateInvariantCheck())
			throw new InvariantException("Campany", "moveEmployee", "before");
		if (from.toString().equals("")) {
			throw new PreconditionException("Company", "moveEmployee",
					"the fromDivision can't be empty");
		}
		if (to.toString().equals("")) {
			throw new PreconditionException("Company", "moveEmployee",
					"the toDivision can't be empty");
		}
		if (employee.toString().equals("")) {
			throw new PreconditionException("Company", "moveEmployee",
					"the employee can't be empty");
		}
		if (!divisions.contains(getDivision(from))) {
			throw new PreconditionException("Company", "moveEmployee",
					"the fromDivision doesn't exist");
		}
		if (!divisions.contains(getDivision(to))) {
			throw new PreconditionException("Company", "moveEmployee",
					"the fromDivision doesn't exist");
		}

		if (!this.getDivision(from).getEmployees().contains(employee))
			throw new PreconditionException("Company", "moveEmployee",
					"the employee to be moved doesn't exist");
		if (this.getDivision(to).getEmployees().contains(employee))
			throw new PreconditionException("Company", "moveEmployee",
					"the employee already exist");
		fireEmployee(from, employee);
		hireEmployee(to, employee);
		//
		// The state invariant must be checked after the operation as well.
		//
		if (!stateInvariantCheck())
			throw new InvariantException("Company", "moveEmployee", "after");

	}

	public void addNewProject(DivisionName dID, Project newProject,
			int estimated) throws Exception {
		//
		// The state invariant must be checked before the operation.
		//
		if (!stateInvariantCheck())
			throw new InvariantException("Campany", "addNewProject", "before");
		if (dID.toString().equals("")) {
			throw new PreconditionException("Company", "addNewProject",
					"the division can't be empty");
		}
		if (newProject.toString().equals("")) {
			throw new PreconditionException("Company", "addNewProject",
					"the new project can't be empty");
		}
		if (!divisions.contains(getDivision(dID))) {
			throw new PreconditionException("Company", "addNewProject",
					"the Division doesn't exist");
		}
		if (isDivisionOperational(getDivision(dID)))
			getDivision(dID).addProject(newProject, estimated);
		//
		// The state invariant must be checked after the operation as well.
		//
		if (!stateInvariantCheck())
			throw new InvariantException("Company", "addNewProject", "after");
	}

	public void removeProject(DivisionName dID, Project project)
			throws Exception {
		//
		// The state invariant must be checked before the operation.
		//
		if (!stateInvariantCheck())
			throw new InvariantException("Campany", "removeProject", "before");
		if (dID.toString().equals("")) {
			throw new PreconditionException("Company", "removeProject",
					"the division can't be empty");
		}
		if (project.toString().equals("")) {
			throw new PreconditionException("Company", "removeProject",
					"the project can't be empty");
		}
		if (!divisions.contains(getDivision(dID))) {
			throw new PreconditionException("Company", "removeProject",
					"the Division doesn't exist");
		}

		if (isDivisionOperational(getDivision(dID)))
			getDivision(dID).removeProject(project);
		//
		// The state invariant must be checked after the operation as well.
		//
		if (!stateInvariantCheck())
			throw new InvariantException("Company", "removeProject", "after");
	}

	public void assignProject(DivisionName dID, Project project, Employee e)
			throws Exception {
		//
		// The state invariant must be checked before the operation.
		//
		if (!stateInvariantCheck())
			throw new InvariantException("Campany", "assignProject", "before");
		if (dID.toString().equals("")) {
			throw new PreconditionException("Company", "assignProject",
					"the division can't be empty");
		}
		if (project.toString().equals("")) {
			throw new PreconditionException("Company", "assignProject",
					"the project can't be empty");
		}
		if (e.toString().equals("")) {
			throw new PreconditionException("Company", "assignProject",
					"the employee can't be empty");
		}
		if (!divisions.contains(getDivision(dID))) {
			throw new PreconditionException("Company", "assignProject",
					"the Division doesn't exist");
		}
		if (isDivisionOperational(getDivision(dID)))
			getDivision(dID).assignProject(e, project);
		//
		// The state invariant must be checked after the operation as well.
		//
		if (!stateInvariantCheck())
			throw new InvariantException("Company", "assignProject", "after");
	}

	public void deAssignProject(DivisionName dID, Project project, Employee e)
			throws Exception {
		//
		// The state invariant must be checked before the operation.
		//
		if (!stateInvariantCheck())
			throw new InvariantException("Campany", "deAssignProject", "before");
		if (dID.toString().equals("")) {
			throw new PreconditionException("Company", "deAssignProject",
					"the division can't be empty");
		}
		if (project.toString().equals("")) {
			throw new PreconditionException("Company", "deAssignProject",
					"the project can't be empty");
		}
		if (e.toString().equals("")) {
			throw new PreconditionException("Company", "deAssignProject",
					"the employee can't be empty");
		}
		if (!divisions.contains(getDivision(dID))) {
			throw new PreconditionException("Company", "deAssignProject",
					"the Division doesn't exist");
		}
		if (isDivisionOperational(getDivision(dID)))
			getDivision(dID).deAssignProject(e, project);
		//
		// The state invariant must be checked after the operation as well.
		//
		if (!stateInvariantCheck())
			throw new InvariantException("Company", "deAssignProject", "after");
	}

	/**
	 * This method implements the operation
	 * 'EmployeeAddingHoursToProjectInDivision'.
	 * 
	 * @param division
	 *            : the name of division.
	 * @param project
	 *            : the project to be added hours.
	 * @param employee
	 *            : the employee that add hours.
	 */
	public void employeeAddHoursToProject(DivisionName dID, Project project,
			Employee e, int hoursToAdd) throws Exception {
		//
		// The state invariant must be checked before the operation.
		//
		if (!stateInvariantCheck())
			throw new InvariantException("Campany",
					"employeeAddHoursToProject", "before");
		if (dID.toString().equals("")) {
			throw new PreconditionException("Company",
					"employeeAddingHoursToProject",
					"the division can't be empty");
		}
		if (project.toString().equals("")) {
			throw new PreconditionException("Company",
					"employeeAddingHoursToProject",
					"the project can't be empty");
		}
		if (e.toString().equals("")) {
			throw new PreconditionException("Company",
					"employeeAddingHoursToProject",
					"the employee can't be empty");
		}
		if (!divisions.contains(getDivision(dID))) {
			throw new PreconditionException("Company",
					"employeeAddingHoursToProject",
					"the Division doesn't exist");
		}
		if (isDivisionOperational(getDivision(dID))) {
			// if()
			getDivision(dID).employeeAddingHoursToProject(e, project,
					hoursToAdd);
			//
			// The state invariant must be checked after the operation as well.
			//
			if (!stateInvariantCheck())
				throw new InvariantException("Company",
						"employeeAddHoursToProject", "after");
		}
	}

	/**
	 * This method implements the operation 'CompleteProject'.
	 * 
	 * @param project
	 *            : the project which is completed.
	 * @return return totalEstimatedHours and totalHoursSpent in an array
	 */
	public int[] completeProject(Project project) throws Exception {
		//
		// The state invariant must be checked before the operation.
		//
		if (!stateInvariantCheck())
			throw new InvariantException("Campany", "completeProject", "before");
		if (project.toString().equals("")) {
			throw new PreconditionException("Company", "completeProject",
					"the project can't be empty");
		}
		Boolean contain = false;
		int[] result = new int[2];
		HashSet<Division> d = new HashSet<Division>();
		Iterator<Division> itr = divisions.iterator();
		while (itr.hasNext()) {
			Division div = itr.next();
			if (div.getProjects().contains(project)) {
				d.add(div);
				contain = true;
			}
		}
		if (!contain) {
			throw new PreconditionException("Company", "getDivision",
					"the project does not exist");
		}
		int totalEstimate = extractEstimatedHours(d, project);
		int totalSpent = extractSpentHours(d, project);
		result[0] = totalEstimate;
		result[1] = totalSpent;
		//
		// The state invariant must be checked after the operation as well.
		//
		if (!stateInvariantCheck())
			throw new InvariantException("Company", "completeProject", "after");
		return result;

	}

	/**
	 * This method implements the
	 * operation'ReportHoursSpentbyEmployeeOnProject'.
	 * 
	 * @param project
	 *            : the project which the employee will report
	 * @param employee
	 *            the employee who wants to report
	 * @return return the hours spent on this project
	 */
	public int reportHours(Employee e, Project p) throws Exception {
		// The state invariant must be checked before the operation.
		//
		int result = 0;
		if (!stateInvariantCheck())
			throw new InvariantException("Campany", "completeProject", "before");
		if (e.toString().equals("")) {
			throw new PreconditionException("Company", "report",
					"the employee can't be empty");
		}
		if (p.toString().equals("")) {
			throw new PreconditionException("Company", "report",
					"the project can't be empty");
		}

		Boolean flagmatch = false;
		Boolean flagproject = false;
		Boolean flagemp = false;
		Iterator<Division> itr = divisions.iterator();
		while (itr.hasNext()) {
			Division div = itr.next();
			HashSet<EmployeeProjectPair> eh = div.getEmployeeHours();
			Iterator<EmployeeProjectPair> eitr = eh.iterator();
			if (div.getProjects().contains(p)) {
				flagproject = true;
			}
			if (div.getEmployees().contains(e)) {
				flagemp = true;
			}
			while (eitr.hasNext()) {
				EmployeeProjectPair etemp = eitr.next();
				if (etemp.getEmployee().equals(e)
						&& etemp.getProject().equals(p)) {
					result = etemp.getHoursPent();
					flagmatch = true;
					break;
				}
			}
			if (flagmatch) {
				break;
			}
		}
		if (!flagproject) {
			throw new PreconditionException("Company", "reportHours",
					"the project doesn't exist");
		}
		if (!flagemp) {
			throw new PreconditionException("Company", "reportHours",
					"the employee doesn't exist");
		}
		if (!flagmatch) {
			throw new PreconditionException("Company", "reportHours",
					"the employee doesn't match with the project");
		}
		//
		// The state invariant must be checked after the operation as well.
		//
		if (!stateInvariantCheck())
			throw new InvariantException("Company", "completeProject", "after");
		return result;

	}

	/**
	 * This method is to get divison object by using divisionName
	 * 
	 * @param division the divison name of the division to be found
	 * @return return the divison object
	 * 
	 * */
	public Division getDivision(DivisionName dID) throws Exception {
		Iterator<Division> itr = divisions.iterator();
		while (itr.hasNext()) {
			Division div = itr.next();
			if (div.getName().equals(dID)) {
				return div;
			}
		}
		throw new PreconditionException("Company", "getDivision",
				"the Division with the dID desn't exist");
	}

}