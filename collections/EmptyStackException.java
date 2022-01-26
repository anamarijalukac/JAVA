package hr.fer.oprpp1.custom.collections;

/**
 * This class represents a custom exception.
 * 
 * @author Anamarija
 *
 */
public class EmptyStackException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * A custom exception thrown when the stack is empty.
	 * @param errorMessage that comes with the exception
	 */
	public EmptyStackException(String errorMessage) {
		super(errorMessage);
	}
}
