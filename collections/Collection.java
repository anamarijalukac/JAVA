package hr.fer.oprpp1.custom.collections;

/**
 * This class represents a model of collection.
 * 
 * @author Anamarija
 *
 */
public interface Collection<T> {

	/**
	 * Protected default constructor.
	 */

	/**
	 * Check if collection contains objects.
	 */
	default boolean isEmpty() {
		return this.size() == 0;
	}

	/**
	 * Returns the number of currently stored objects in this collections.
	 */
	public int size();

	/**
	 * Adds the given object into this collection.
	 * 
	 * @param value of the object that is added in the collection
	 */
	public void add(T value);

	/**
	 * Checks if the collection contains a given value, as determined by equals
	 * method.
	 * 
	 * @param value of the object that is tested
	 */
	public boolean contains(Object value);

	/**
	 * Checks if the collection contains a given value and removes one occurrence of
	 * it.
	 * 
	 * @param value of the object that should be removed
	 */
	public boolean remove(Object value);

	/**
	 * Allocates new array with size equals to the size of this collections and
	 * fills it with collection content.
	 * 
	 * @throws UnsupportedOperationException
	 */
	public Object[] toArray();

	@SuppressWarnings("hiding")
	public <T> T[] toArray(T[] a);

	/**
	 * Method calls processor.process(.) for each element of this collection.
	 * 
	 * @param p is an instance of class Processor
	 */
	default void forEach(Processor<? super T> p) {
		ElementsGetter<T> getter = this.createElementsGetter();
		while (getter.hasNextElement()) {
			p.process(getter.getNextElement());
		}
	}

	/**
	 * Adds all elements from the given collection to this collection.
	 * 
	 * @param other is a collection containg elements that are added to this
	 *              collection
	 */
	default void addAll(Collection<? extends T> other) {

		class P implements Processor<T> {
			@SuppressWarnings("unchecked")
			@Override
			public void process(Object value) {
				Collection.this.add((T) value);
			}
		}
		P p = new P();

		other.forEach(p);
	}

	/**
	 * Removes all elements from this collection.
	 */
	public void clear();

	/**
	 * Abstract class of ElementsGetter for a collection.
	 *
	 * @return the elements getter created specifically for a given collection.
	 */
	public abstract ElementsGetter<T> createElementsGetter();

	/**
	 * Adds the all satisfying.
	 *
	 * @param col    is a collection from which the elements are copied into this
	 *               collection.
	 * @param tester is a reference to an interface Tester
	 */
	default void addAllSatisfying(Collection<? extends T> col, Tester<T> tester) {
		@SuppressWarnings("unchecked")
		ElementsGetter<T> getter = (ElementsGetter<T>) col.createElementsGetter();
		while (getter.hasNextElement()) {
			T o = (T) getter.getNextElement();
			if (tester.test(o)) {
				this.add(o);
			}
		}
	}
}
