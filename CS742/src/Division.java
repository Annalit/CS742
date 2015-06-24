import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * This program represents the prototype of the division specification. The
 * basic type EMPLOYEE,MANAGER,PROJECT DIVISION_NAME are implemented as classes
 * separately.See each class for their internal details. This program uses the built-in data type "HashSet" to implement
 * the set data type in the specification, and use HashMap to implement the
 * relation in the specification.
 * 
 * Author : Qian Xu Date : April 30, 2015
 */
public class Division {

	private DivisionName name;
	private HashSet<Employee> employees;
	private HashSet<Project> projects;
	private Map<Project, Integer> estimatedHours;
	private Map<Project, Integer> projectHours;
	private HashSet<EmployeeProjectPair> employeeHours;

	/**
	 * This is the constuctor method that implements 'Init' state.
	 */
	public Division(String name) {
		this.name = new DivisionName(name);
		employees = new HashSet<Employee>();
		projects = new HashSet<Project>();
		estimatedHours = new HashMap<Project, Integer>();
		projectHours = new HashMap<Project, Integer>();
		employeeHours = new HashSet<EmployeeProjectPair>();
		if (!stateInvariantCheck())
			System.out.print("\n Init state implemented by the "
					+ " constructor failed because the "
					+ " state invariant is violated \n");

	}

	private boolean stateInvariantCheck() {
		/*
		 * this is for consistency of dom estimatedHours = projects
		 */
		if (!(estimatedHours.keySet().containsAll(projects) && projects
				.containsAll(estimatedHours.keySet()))) {
			return false;
		}
		/*
		 * this is for consistency of dom projectHours = projects
		 */
		if (!(projectHours.keySet().containsAll(projects) && projects
				.containsAll(projectHours.keySet()))) {
			return false;
		}
		/*
		 * this is for consistency of for all employeeHours, its employee
		 * belongs to employees and its project belongs to projects
		 */
		Iterator<EmployeeProjectPair> itrEmployeeHours = employeeHours
				.iterator();
		while (itrEmployeeHours.hasNext()) {
			EmployeeProjectPair etemp = itrEmployeeHours.next();
			if (!(employees.contains(etemp.getEmployee()) && projects
					.contains(etemp.getProject()))) {
				return false;
			}
		}
		/*
		 * the method of equal and hashCode in the employeeProjectPair has kept
		 * consistency of for all employeeHours, they equals to each other if
		 * and only if their projects are the same and employees are the same
		 */
		return true;
	}

	public void addEmployee(Employee newEmployee) throws Exception {
		//
		// The state invariant must be checked before the operation.
		//
		if (!stateInvariantCheck())
			throw new InvariantException("Division", "addEmployee", "before");
		if (newEmployee.toString().equals("")) {
			throw new PreconditionException("Division", "addEmployee",
					"the employee can't be empty");
		}
		if (employees.contains(newEmployee)) {
			throw new PreconditionException("Division", "addEmployee",
					"the employee to be hired already exists");
		} else {
			employees.add(newEmployee);
		}
		//
		// The state invariant must be checked after the operation as well.
		//
		if (!stateInvariantCheck())
			throw new InvariantException("Division", "addEmployee", "after");
	}

	public void removeEmployee(Employee e) throws Exception {
		//
		// The state invariant must be checked before the operation.
		//
		if (!stateInvariantCheck())
			throw new InvariantException("Division", "removeEmployee", "before");
		if (e.toString().equals("")) {
			throw new PreconditionException("Division", "removeEmployee",
					"the employee can't be empty");
		}
		if (!employees.contains(e)) {
			throw new PreconditionException("Division", "removeEmployee",
					"the employee to be fired does not exist");
		} else {
			employees.remove(e);
			Iterator<EmployeeProjectPair> itr = employeeHours.iterator();
			while (itr.hasNext()) {
				EmployeeProjectPair temp = itr.next();
				if (temp.getEmployee().equals(e)) {
					itr.remove();
				}
			}

		}
		//
		// The state invariant must be checked after the operation as well.
		//
		if (!stateInvariantCheck())
			throw new InvariantException("Division", "removeEmployee", "after");
	}

	/*
	 * finished
	 */
	public void addProject(Project newProject, int estimated) throws Exception {
		//
		// The state invariant must be checked before the operation.
		//
		if (!stateInvariantCheck())
			throw new InvariantException("Division", "addProject", "before");
		if (newProject.toString().equals("")) {
			throw new PreconditionException("Division", "addProject",
					"the new project can't be empty");
		}
		if (estimated <= 0) {
			throw new PreconditionException("Division", "addProject",
					"invalid time input");
		} else if (projects.contains(newProject)) {
			throw new PreconditionException("Division", "addProject",
					"the project has already exist");
		} else if (estimatedHours.containsKey(newProject)) {
			throw new PreconditionException("Division", "addProject",
					"the newProject already exist in estimatedHours");
		} else if (projectHours.containsKey(newProject)) {
			throw new PreconditionException("Division", "addProject",
					"the newProject already exist in projectHours");
		} else {
			projects.add(newProject);
			estimatedHours.put(newProject, estimated);
			projectHours.put(newProject, 0);

		}
		//
		// The state invariant must be checked after the operation as well.
		//
		if (!stateInvariantCheck())
			throw new InvariantException("Division", "addProject", "after");
	}

	/*
	 * finished
	 */
	public void removeProject(Project project) throws Exception {
		//
		// The state invariant must be checked before the operation.
		//
		if (!stateInvariantCheck())
			throw new InvariantException("Division", "removeProject", "before");
		if (project.toString().equals("")) {
			throw new PreconditionException("Division", "removeProject",
					"the project can't be empty");
		}
		if (!projects.contains(project)) {
			throw new PreconditionException("Division", "removeProject",
					"the project to be removed does not exist");
		} else {
			projects.remove(project);
			estimatedHours.remove(project);
			projectHours.remove(project);

			Iterator<EmployeeProjectPair> itr = employeeHours.iterator();
			while (itr.hasNext()) {
				EmployeeProjectPair itemp = itr.next();
				if (itemp.getProject().equals(project)) {
					itr.remove();
				}
			}

		}
		//
		// The state invariant must be checked after the operation as well.
		//
		if (!stateInvariantCheck())
			throw new InvariantException("Division", "removeProject", "after");
	}

	/*
	 * finished
	 */
	public void assignProject(Employee employee, Project project)
			throws Exception {
		//
		// The state invariant must be checked before the operation.
		//
		if (!stateInvariantCheck())
			throw new InvariantException("Division", "assignProject", "before");
		if (project.toString().equals("")) {
			throw new PreconditionException("Division", "assignProject",
					"the project can't be empty");
		}
		if (employee.toString().equals("")) {
			throw new PreconditionException("Division", "assignProject",
					"the employee can't be empty");
		}
		if (!employees.contains(employee)) {
			throw new PreconditionException("Division", "assignProject",
					"the employee to be assigned does not exist");
		} else if (!projects.contains(project)) {
			throw new PreconditionException("Division", "assignProject",
					"the project to be assigned does not exist");
		}
		Iterator<EmployeeProjectPair> itr = employeeHours.iterator();
		while (itr.hasNext()) {
			EmployeeProjectPair itemp = itr.next();
			if (itemp.getProject().equals(project)
					&& itemp.getEmployee().equals(employee)) {
				throw new PreconditionException("Division", "assignProject",
						"the employee has already been assigned with the project");
			}
		}

		EmployeeProjectPair epp = new EmployeeProjectPair(employee, project, 0);
		employeeHours.add(epp);
		//
		// The state invariant must be checked after the operation as well.
		//
		if (!stateInvariantCheck())
			throw new InvariantException("Division", "assignProject", "after");

	}

	public void deAssignProject(Employee employee, Project project)
			throws Exception {
		//
		// The state invariant must be checked before the operation.
		//
		if (!stateInvariantCheck())
			throw new InvariantException("Division", "deAssignProject",
					"before");
		if (project.toString().equals("")) {
			throw new PreconditionException("Division", "deAssignProject",
					"the project can't be empty");
		}
		if (employee.toString().equals("")) {
			throw new PreconditionException("Division", "deAssignProject",
					"the employee can't be empty");
		}
		Boolean flag = false;
		if (!employees.contains(employee)) {
			throw new PreconditionException("Division", "deAssignProject",
					"the employee to be deassigned does not exist");
		} else if (!projects.contains(project)) {
			throw new PreconditionException("Division", "deAssignProject",
					"the project to be deassigned does not exist");
		} else {
			Iterator<EmployeeProjectPair> itr = employeeHours.iterator();
			while (itr.hasNext()) {
				EmployeeProjectPair itemp = itr.next();
				if (itemp.getProject().equals(project)
						&& itemp.getEmployee().equals(employee)) {
					itr.remove();
					flag = true;
				}
			}
			if (!flag) {
				throw new PreconditionException("Division", "deAssignProject",
						"the employee has never been assigned with the project");
			}

		}
		//
		// The state invariant must be checked after the operation as well.
		//
		if (!stateInvariantCheck())
			throw new InvariantException("Division", "deAssignProject", "after");
	}

	public void employeeAddingHoursToProject(Employee employee,
			Project project, int hoursToAdd) throws Exception {
		//
		// The state invariant must be checked before the operation.
		//
		if (!stateInvariantCheck())
			throw new InvariantException("Division",
					"employeeAddingHoursToProject", "before");
		if (project.toString().equals("")) {
			throw new PreconditionException("Division",
					"employeeAddingHoursToProject",
					"the project can't be empty");
		}
		if (employee.toString().equals("")) {
			throw new PreconditionException("Division",
					"employeeAddingHoursToProject",
					"the employee can't be empty");
		}
		if (hoursToAdd < 0) {
			throw new PreconditionException("Division",
					"employeeAddingHoursToProject", "invalid time input");
		} else if (!projects.contains(project)) {
			throw new PreconditionException("Division",
					"employeeAddingHoursToProject", "the project doesn't exist");
		} else if (!estimatedHours.containsKey(project)) {
			throw new PreconditionException("Division",
					"employeeAddingHoursToProject",
					"the project doesn't exist in estimatedHours");
		} else if (!projectHours.containsKey(project)) {
			throw new PreconditionException("Division",
					"employeeAddingHoursToProject",
					"the Project doesn't exist in projectHours");
		} else if (!employees.contains(employee)) {
			throw new PreconditionException("Division",
					"employeeAddingHoursToProject",
					"the employee doesn't exist in the division");
		}
		Boolean flag = false;
		Iterator<EmployeeProjectPair> itr = employeeHours.iterator();
		while (itr.hasNext()) {
			EmployeeProjectPair itemp = itr.next();
			if (itemp.getProject().equals(project)
					&& itemp.getEmployee().equals(employee)) {
				int tempHour = itemp.getHoursPent();
				itr.remove();
				EmployeeProjectPair epp = new EmployeeProjectPair(employee,
						project, tempHour + hoursToAdd);
				employeeHours.add(epp);
				flag = true;
				break;
			}
		}
		if (flag) {
			int temp = projectHours.get(project);
			projectHours.remove(project);
			projectHours.put(project, temp + hoursToAdd);
		} else {
			throw new PreconditionException("Division",
					"employeeAddingHoursToProject",
					"the employee doesn't match with the project");
		}
		//
		// The state invariant must be checked after the operation as well.
		//
		if (!stateInvariantCheck())
			throw new InvariantException("Division",
					"employeeAddingHoursToProject", "after");
	}

	public DivisionName getName() {
		return name;
	}

	public void setName(DivisionName name) {
		this.name = name;
	}

	public HashSet<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(HashSet<Employee> employees) {
		this.employees = employees;
	}

	public HashSet<Project> getProjects() {
		return projects;
	}

	public void setProjects(HashSet<Project> projects) {
		this.projects = projects;
	}

	public Map<Project, Integer> getEstimatedHours() {
		return estimatedHours;
	}

	public void setEstimatedHours(Map<Project, Integer> estimatedHours) {
		this.estimatedHours = estimatedHours;
	}

	public Map<Project, Integer> getProjectHours() {
		return projectHours;
	}

	public void setProjectHours(Map<Project, Integer> projectHours) {
		this.projectHours = projectHours;
	}

	public HashSet<EmployeeProjectPair> getEmployeeHours() {
		return employeeHours;
	}

	public void setEmployeeHours(HashSet<EmployeeProjectPair> employeeHours) {
		this.employeeHours = employeeHours;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Division other = (Division) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
