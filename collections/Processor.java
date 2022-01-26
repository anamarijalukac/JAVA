package hr.fer.oprpp1.custom.collections;

/**
 * This class represents a model of an object capable of performing some
 * operation on the passed object.
 * 
 * @author Anamarija
 *
 */
public interface Processor<T> {

	/**
	 * Method which will perform a certain operation defineded as a new class which
	 * inherits from the class Processor.
	 * 
	 * @param value of objects to be processed
	 */
	public void process(T value);
}
