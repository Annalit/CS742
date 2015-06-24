/*
 * This class represents an exception when the state invariant of a 
 * class is violated.
 *
 * Written by: Qian Xu
 * Date: April 20, 2015
 */

public class InvariantException extends Exception {

	/**
	 * Customized custructor.
	 * 
	 * @param className
	 *            The name of the class from which the exception is thrown.
	 * @param method
	 *            The name of the method from which the exception if thrown.
	 * @param beforeOrAfter
	 *            A string that represents whether the state invariant is
	 *            violated before the operation is invoked or after the
	 *            operation is invoked.
	 */

	public InvariantException(String className, String method,
			String beforeOrAfter) {
		System.out.println("\n\n Class " + className + "::" + " Method "
				+ method + ":" + " State invariant is violated "
				+ beforeOrAfter + " this method\n\n");
	}
}
