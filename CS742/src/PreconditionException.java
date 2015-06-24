/*
 * This class represents an exception when the precondition Exception of a 
 * class is violated.
 *
 * Written by: Qian Xu
 * Date: April 20, 2015
 */
	public class PreconditionException extends Exception {
	public String error;
    
    /** 
     * Customized custructor.
     * @param className The name of the class from which the exception is thrown.
     * @param method The name of the method from which the exception if thrown.
     * @param message The additional message to be displayed.
     */
    
    public PreconditionException (String className, String method, String message) {
    	
        System.out.println ("\n\n Class " + className + "::" + " Method " + method + ":" +
                            " Precondition for this method is violated\n " + message + "\n\n");
        error = "\n\n Class " + className + "::" + " Method " + method + ":"
				+ " Precondition for this method is violated\n " + message
				+ "\n\n";
    }
}
