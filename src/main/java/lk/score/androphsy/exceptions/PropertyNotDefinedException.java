package lk.score.androphsy.exceptions;

/**
 * This exception is thrown when a property cannot be read from the property
 * file
 */
public class PropertyNotDefinedException extends Exception {

	private static final String MESSAGE_PREFIX = "Property not defined!! : ";

	public PropertyNotDefinedException(String message) {
		super(MESSAGE_PREFIX + message);
	}

	public PropertyNotDefinedException() {
	}
}
