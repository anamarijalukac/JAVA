package hr.fer.oprpp1.custom.collections;

/**
 * An adaptor class which represents a stack and provides to user the methods
 * which are natural for a stack and is based on ArrayIndexedCollection.
 * 
 * @author Anamarija
 *
 */
public class ObjectStack<T> {

	ArrayIndexedCollection<T> a = new ArrayIndexedCollection<>();

	/**
	 * Check if stack contains objects.
	 * 
	 * @return true if stack contains no objects and false otherwise.
	 */
	public boolean isEmpty() {
		return a.isEmpty();

	}

	/**
	 * Returns the number of elements in this stack.
	 * 
	 * @return number of elements in this stack
	 */
	public int size() {
		return a.size();

	}

	/**
	 * Pushes given value on the stack.
	 * 
	 * @param value that is pushed on top of the stack
	 */
	public void push(T value) {
		a.add(value);

	}

	/**
	 * Removes last value pushed on stack from stack and returns it.
	 * 
	 * @return last value from the stack
	 */
	public T pop() {
		if (a.size() == 0)
			throw new EmptyStackException("Stog je prazan.");
		T o = (T) a.get(a.size() - 1);
		a.remove(a.size() - 1);
		return o;

	}

	/**
	 * Returns last element placed on stack but does not delete it from stack.
	 * 
	 * @return last element from the stack
	 */
	public T peek() {
		if (a.size() == 0)
			throw new EmptyStackException("Stog je prazan.");
		return (T) a.get(a.size()-1);
	}

	/**
	 * Removes all elements from stack.
	 */
	public void clear() {
		a.clear();

	}

}
